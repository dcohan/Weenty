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

        private bool Validate(offer offer)
        {
            if (offer.ExpirationDatetime.HasValue && offer.ExpirationDatetime <= offer.StartDatetime)
            {
                ModelState.AddModelError("Date", "Las fechas de expiración debe ser inferior a la de inicio");
                ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);   
                return false;
            }

            if (db.offer.Where(o => o.Active && o.IdOffer != offer.IdOffer && o.ExpirationDatetime < DateTime.Now
                                && o.ExpirationDatetime < offer.StartDatetime).Count() > 0)
            {
                ModelState.AddModelError("ServerValidations", "Existe otra oferta en curso, no puede haber mas de una oferta vigente para un producto");
                ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
                return false;
            }

            return true;
        }

        // GET: product
        public async Task<ActionResult> Index(bool all = false, string title = null, int pageNumber = 1)
        {
            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            var offers = db.offer.Where(o => (title == null || o.Title.ToLower().Contains(title.ToLower())));

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                offers = offers.Where(o => CuponeraIdentity.AdminCompany == o.product.store.IdCompany)
                               .Where(o => CuponeraIdentity.CurrentAvaiableStores.Contains(o.product.IdStore));
            }

            var permitedOffers = offers.OrderBy(o => o.Title);

            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)permitedOffers.Count() / pageSize));

            return View(permitedOffers.ToPagedList(pageNumber, pageSize));
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
            return View(offer);
        }

        // GET: /offer/Create
        public ActionResult Create()
        {
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title");
            return View();
        }

        // POST: /offer/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "IdOffer,Title,StartDatetime,ExpirationDatetime,IdProduct,ImagePath, Price")] offer offer, List<HttpPostedFileBase> fileUpload)
        {
			 if (!Validate(offer))
            {
                return View(offer);
            }
            if (ModelState.IsValid)
            {
                if (fileUpload.Count > 0) offer.ImagePath = GeneratePhisicalFile(fileUpload[0]);
                offer.CreationDatetime = DateTime.Now;
                offer.ModificationDatetime = DateTime.Now;
                db.offer.Add(offer);
                db.SaveChanges();

                //Save aditional images
                UploadImages(fileUpload, offer.IdOffer);

                return RedirectToAction("Index");
            }

            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
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
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // POST: /offer/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdOffer,Title,Description,StartDatetime,ExpirationDatetime,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,Price,ImagePath")] offer offer, List<HttpPostedFileBase> fileUpload)
        {

			if (!Validate(offer))
            {
                return View(offer);
            }

            try
            {

                if (ModelState.IsValid)
                {
                    db.Entry(offer).State = EntityState.Modified;
                    offer.ModificationDatetime = DateTime.Now;
                    await db.SaveChangesAsync();
                    //Save aditional images
                    UploadImages(fileUpload, offer.IdOffer);
                    return RedirectToAction("Index");
                }
                ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
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
