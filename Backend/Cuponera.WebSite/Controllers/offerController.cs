using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;

namespace Cuponera.WebSite.Controllers
{
    public class offerController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();


        // GET: offer
        public ActionResult Index()
        {
            var offer = db.offer.Include(o => o.product);
            return View(offer.ToList());
        }

        // GET: offer/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = db.offer.Find(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            return View(offer);
        }

        // GET: offer/Create
        public ActionResult Create(int? IdProduct)
        {
            if (IdProduct != null)
            {
                offer o = new offer();
                o.IdProduct = IdProduct != null ? (int)IdProduct : 0;
                o.StartDatetime = DateTime.Now;
                o.ExpirationDatetime = DateTime.Now.AddDays(30);

                var product = db.product.First(p => p.IdProduct == (int)IdProduct);
                o.Title = product.Title;
                o.ImagePath = product.ImagePath;

                ViewBag.IdProduct = new SelectList(db.product.Where(p => p.IdProduct.Equals((int)IdProduct)).ToList(), "IdProduct", "Title", "Description");  
                return View(o);
            }
            else
            {
                ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", "Description");
                return View();
            }

        }

        // POST: offer/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "IdOffer,Title,PromoCode,TargetURL,Active,StartDatetime,ExpirationDatetime,ItemOrder,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath")] offer offer)
        {
            if (ModelState.IsValid)
            {
                offer.CreationDatetime = DateTime.Now;
                db.offer.Add(offer);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // GET: offer/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = db.offer.Find(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // POST: offer/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "IdOffer,Title,PromoCode,TargetURL,Active,StartDatetime,ExpirationDatetime,ItemOrder,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath")] offer offer)
        {
            if (ModelState.IsValid)
            {
                db.Entry(offer).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // GET: offer/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = db.offer.Find(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            return View(offer);
        }

        // POST: offer/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            offer offer = db.offer.Find(id);
            offer.DeletionDatetime = DateTime.Now;
            db.SaveChanges();
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
