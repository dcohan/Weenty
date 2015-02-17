using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;
using System.Configuration;
using System.IO;
using System.Threading;
using Cuponera.WebSite.Models;
using Cuponera.WebSite.Helpers;
using PagedList;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute]
    public class productController : UploadImagesBaseController
    {
        

        public productController() : base(UploadImagesEnum.product)
        {

        }

        private void GetStores(product product=null)
        {
            var stores = db.store.Where(s => s.DeletionDatetime == null && s.company.DeletionDatetime == null);

             if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
             {
                stores = stores.Where(s => CuponeraIdentity.AdminCompany == s.IdCompany ||
                                           CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore));
             }

             var permitedStores = stores.OrderBy(s => s.Name);
             ViewBag.IdStore = new SelectList(permitedStores, "IdStore", "Name", product != null ? product.IdStore : 0);
        }

        private void GetCategories(product product=null)
        {
            var categories = db.category.Where(c => c.DeletionDatetime == null).OrderBy(c => c.Name);


            ViewBag.IdCategory = new SelectList(categories, "IdCategory", "Name", product != null ? product.IdCategory : 0);
        }

        private bool Validate(product product)
        {
            if (product.ExpirationDatetime.HasValue && product.ExpirationDatetime <= product.StartDatetime)
            {
                ModelState.AddModelError("Date", "Las fechas de expiración debe ser inferior a la de inicio");
                GetStores(product);
                GetCategories(product);
                
                return false;
            }

            return true;
        }

        // GET: product
        public async Task<ActionResult> Index(bool all = false, string title = null, int? category = null, int page = 1)
        {
            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            var products = db.product.Where(p => (title == null || p.Title.ToLower().Contains(title.ToLower())))
                                     .Where(p => (category == null || category == 0 || p.IdCategory == category))
                                     .Where(p => all || p.DeletionDatetime == null && p.store.DeletionDatetime == null && p.store.company.DeletionDatetime == null);
            
            if (all) {
                foreach (var product in products)
                {
                    if (product.store.DeletionDatetime != null || product.store.company.DeletionDatetime != null || product.store.company.companySubscription.Where(cs => DateTime.Now < cs.EndDate).Count() == 0)
                    {
                        product.DeletionDatetime = DateTime.Now;
                    }

                }
            }

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                products = products.Where(p => CuponeraIdentity.AdminCompany == p.store.IdCompany ||  
                                            CuponeraIdentity.CurrentAvaiableStores.Contains(p.IdStore));
            }

            var permitedProducts = products.OrderBy(o => o.Title);

            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)permitedProducts.Count() / pageSize));

            return View(permitedProducts.ToPagedList(page, pageSize));
        }

        // GET: /product/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            return View(product);
        }

        // GET: /product/Create
        public ActionResult Create()
        {
            GetCategories();
            GetStores();
            return View();
        }

        // POST: /product/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include="IdProduct,Title,StartDatetime,ExpirationDatetime,IdCategory,Description,IdStore, Price")] product product, List<HttpPostedFileBase> fileUpload)
        {
            if (!Validate(product))
            {
                return View(product);
            }

            if (ModelState.IsValid)
            {
                fileUpload = FilterFiles(fileUpload);
                string imagePath = null;
                HttpPostedFileBase fileImagePath;
                if (fileUpload.Count() == 1)
                {
                    fileImagePath = fileUpload.FirstOrDefault();
                    fileUpload.RemoveAt(0);
                    imagePath = GeneratePhisicalFile(fileImagePath);
                }

                product.ImagePath = imagePath;

                product.CreationDatetime = DateTime.Now;
                product.ModificationDatetime = DateTime.Now;
                db.product.Add(product);
                db.SaveChanges();
    			//Save aditional images
                UploadImages(fileUpload, product.IdProduct);
                return RedirectToAction("Index");
            }

            GetCategories(product);
            GetStores(product);
            return View(product);
        }

        // GET: /product/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            GetCategories(product);
            GetStores(product);
            return View(product);
        }

        // POST: /product/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdProduct,Title,Active,StartDatetime,ExpirationDatetime,IdCategory,Description,IdStore, Price")] product product, List<HttpPostedFileBase> fileUpload, string imagesToRemove, string ImagePath)
        {
            if (!Validate(product))
            {
                return View(product);
            }

            if (ModelState.IsValid)
            {
                fileUpload = FilterFiles(fileUpload);

                HttpPostedFileBase fileImagePath;
                if (String.IsNullOrEmpty(ImagePath) && fileUpload.Count() == 1)
                {
                    fileImagePath = fileUpload.FirstOrDefault();
                    fileUpload.RemoveAt(0);
                    ImagePath = GeneratePhisicalFile(fileImagePath);
                }

                string previousImagePath = db.product.Where(p => p.IdProduct == product.IdProduct).Select(p => p.ImagePath).FirstOrDefault();

                string[] images_to_remove = imagesToRemove.Split(new Char[] { ',' });
                RemoveImages(images_to_remove);

                string remainingImagePath = GetRemainImageName(product.IdProduct);
                if (!string.IsNullOrEmpty(remainingImagePath)) 
                {
                    ImagePath = remainingImagePath;
                }

                product.ImagePath = ChangeCoverImage(previousImagePath, ImagePath, images_to_remove.Contains("main"), product.IdProduct);

                db.Entry(product).State = EntityState.Modified;
                product.ModificationDatetime = DateTime.Now;

                db.SaveChanges();
               
                //Save aditional images
                UploadImages(fileUpload, product.IdProduct);
                return RedirectToAction("Index");
            }

            return View(product);
        }

        // GET: /product/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }

            product.DeletionDatetime = DateTime.Now;


            db.Configuration.ValidateOnSaveEnabled = false;
            await db.SaveChangesAsync();
            db.Configuration.ValidateOnSaveEnabled = true;

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // GET: Product/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }

            product.DeletionDatetime = null;


            db.Configuration.ValidateOnSaveEnabled = false;
            await db.SaveChangesAsync();
            db.Configuration.ValidateOnSaveEnabled = true;


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: /product/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            product product = await db.product.FindAsync(id);
            db.product.Remove(product);
            await db.SaveChangesAsync();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
