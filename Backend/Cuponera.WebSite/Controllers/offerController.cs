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
using System.Data.Entity.Validation;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute]
    public class offerController : UploadImagesBaseController
    {
        

        public offerController() : base(UploadImagesEnum.offer)
        {

        }

        public void GetProducts(offer offer=null)
        {
            IQueryable<product> products = db.product;
            if (offer.product.DeletionDatetime == null) { 
                products = products.Where(p => p.DeletionDatetime == null);
            }

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                products = products.Where(p => CuponeraIdentity.AdminCompany == p.store.IdCompany ||
                                           CuponeraIdentity.CurrentAvaiableStores.Contains(p.IdStore));
            }

            ViewBag.Products = products;
            ViewBag.IdProduct = new SelectList(products, "IdProduct", "Title", offer != null ? offer.IdProduct : 0);
        }

        private bool Validate(offer offer)
        {
            if (offer.ExpirationDatetime.HasValue && offer.ExpirationDatetime <= offer.StartDatetime)
            {
                ModelState.AddModelError("Date", "Las fechas de expiración debe ser inferior a la de inicio.");
                GetProducts(offer);
                return false;
            }

            if (db.offer.Where(o => !o.DeletionDatetime.HasValue && o.IdOffer != offer.IdOffer && o.ExpirationDatetime < DateTime.Now
                                && o.ExpirationDatetime < offer.StartDatetime && o.IdProduct == offer.IdProduct).Count() > 0)
            {
                ModelState.AddModelError("ServerValidations", "Existe otra oferta en curso, no puede haber mas de una oferta vigente para un producto.");
                GetProducts(offer);
                return false;
            }

            return true;
        }

        // GET: product
        public async Task<ActionResult> Index(bool all = false, string title = null, int page = 1)
        {
            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            var offers = db.offer.Where(o => (title == null || o.Title.ToLower().Contains(title.ToLower())))
                                 .Where(o => (all || o.DeletionDatetime == null && o.product.DeletionDatetime == null));

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                offers = offers.Where(o => CuponeraIdentity.AdminCompany == o.product.store.IdCompany ||
                                           CuponeraIdentity.CurrentAvaiableStores.Contains(o.product.IdStore));
            }

            foreach (var offer in offers)
            {
                if (offer.product.DeletionDatetime != null)
                {
                    offer.DeletionDatetime = offer.product.DeletionDatetime;
                }
            }

            var permitedOffers = offers.OrderBy(o => o.Title);

            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)permitedOffers.Count() / pageSize));

            return View(permitedOffers.ToPagedList(page, pageSize));
        }

        // GET: /offer/Details/5
        
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = await db.offer.FindAsync(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            GetProducts(offer);
            return View(offer);
        }

        // GET: /offer/Create
        public ActionResult Create(int IdProduct = 0)
        {
            offer offer = null;
            if (IdProduct > 0)
            {
                offer = new offer() { IdProduct = IdProduct };
            }

            GetProducts(offer);
            return View();
        }

        // POST: /offer/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "IdOffer,Title,StartDatetime,ExpirationDatetime,Description,IdProduct,Price")] offer offer, List<HttpPostedFileBase> fileUpload)
        {
			if (!Validate(offer))
            {
                return View(offer);
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
                offer.ImagePath = imagePath;

                offer.CreationDatetime = DateTime.Now;
                offer.ModificationDatetime = DateTime.Now;
                db.offer.Add(offer);
                db.SaveChanges();

                //Save aditional images
                UploadImages(fileUpload, offer.IdOffer);
                return RedirectToAction("Index");
            }

            GetProducts(offer);
            return View(offer);
        }

        // GET: /offer/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = await db.offer.FindAsync(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            GetProducts(offer); 
            return View(offer);
        }

        // POST: /offer/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdOffer,Title,Description,StartDatetime,ExpirationDatetime,IdProduct,Price")] offer offer, List<HttpPostedFileBase> fileUpload, string imagesToRemove, string ImagePath)
        {

			if (!Validate(offer))
            {
                return View(offer);
            }

            try
            {

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

                    string previousImagePath = db.offer.Where(o => o.IdOffer == offer.IdOffer).Select(p => p.ImagePath).FirstOrDefault();

                    string[] images_to_remove = imagesToRemove.Split(new Char[] { ',' });
                    RemoveImages(images_to_remove);

                    string remainingImagePath = GetRemainImageName(offer.IdOffer);
                    if (!string.IsNullOrEmpty(remainingImagePath))
                    {
                        ImagePath = remainingImagePath;
                    }

                    offer.ImagePath = ChangeCoverImage(previousImagePath, ImagePath, images_to_remove.Contains("main"), offer.IdOffer);




                    db.Entry(offer).State = EntityState.Modified;
                    offer.ModificationDatetime = DateTime.Now;
                    db.SaveChanges();
                    //Save aditional images
                    UploadImages(fileUpload, offer.IdOffer);
                    return RedirectToAction("Index");
                }
                GetProducts(offer);
                return View(offer);
            }
            catch (DbEntityValidationException ex)
            {
                return View(offer);
            }
        }

        // GET: /offer/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }

            offer offer = await db.offer.FindAsync(id);
            offer.DeletionDatetime = DateTime.Now;
            db.Entry(offer).State = EntityState.Modified;
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
