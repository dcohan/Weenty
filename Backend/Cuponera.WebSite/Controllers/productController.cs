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
            var stores = db.store.Where(s => s.DeletionDatetime == null);

             if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
             {
                stores = stores.Where(s => CuponeraIdentity.AdminCompany == s.IdCompany ||
                                           CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore));
             }

             var permitedStores = stores.OrderBy(s => s.Name);

            if (product == null)
            {
                ViewBag.IdStore = new SelectList(permitedStores, "IdStore", "Name");
            }
            else
            {
                ViewBag.IdStore = new SelectList(permitedStores, "IdStore", "Name", product.IdStore);
            }
        }

        private void GetCategories(product product=null)
        {
            if (product == null)
            {
                ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name");
            }
            else
            {
                ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            }
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
                                     .Where(p => all || p.DeletionDatetime == null);

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
        public ActionResult Create([Bind(Include="IdProduct,Title,StartDatetime,ExpirationDatetime,ImagePath,IdCategory,Description,IdStore, Price")] product product, List<HttpPostedFileBase> fileUpload)
        {
            if (!Validate(product))
            {
                return View(product);
            }

            if (ModelState.IsValid)
            {
                if (fileUpload.Count > 0) product.ImagePath = GeneratePhisicalFile(fileUpload[0]);
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
        public async Task<ActionResult> Edit([Bind(Include = "IdProduct,Title,Active,StartDatetime,ExpirationDatetime,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,IdCategory,Description,IdStore, Price, Files")] product product, List<HttpPostedFileBase> fileUpload)
        {
            if (!Validate(product))
            {
                return View(product);
            }

            if (ModelState.IsValid)
            {
                db.Entry(product).State = EntityState.Modified;
                product.ModificationDatetime = DateTime.Now;
                await db.SaveChangesAsync();
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
            await db.SaveChangesAsync();

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
