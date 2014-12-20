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
    public class offerController : UploadImagesBaseController
    {
        

        public offerController() : base(UploadImagesEnum.offer)
        {

        }

        // GET: product
        public async Task<ActionResult> Index(bool all = false, string title = null, int pageNumber = 1)
        {
            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            var offers = db.offer.Where(o => (title == null || o.Title.ToLower().Contains(title.ToLower())) && o.DeletionDatetime == null)
                                     .OrderBy(o => o.Title)
                                     .ToPagedList(pageNumber, pageSize);

            return View(offers);
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
        public async Task<ActionResult> Edit([Bind(Include = "IdOffer,Title,StartDatetime,ExpirationDatetime,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,Price")] offer offer)
        {
            if (ModelState.IsValid)
            {
                db.Entry(offer).State = EntityState.Modified;
                offer.ModificationDatetime = DateTime.Now;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
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
