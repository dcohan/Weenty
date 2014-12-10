﻿using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;

namespace Cuponera.WebSite.Controllers
{
    public class offerController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: /offer/
        public async Task<ActionResult> Index()
        {
            var offer = db.offer.Include(o => o.product);
            return View(await offer.ToListAsync());
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
        public async Task<ActionResult> Create([Bind(Include="IdOffer,Title,Active,StartDatetime,ExpirationDatetime,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath")] offer offer)
        {
            if (ModelState.IsValid)
            {
                db.offer.Add(offer);
                await db.SaveChangesAsync();
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
        public async Task<ActionResult> Edit([Bind(Include="IdOffer,Title,Active,StartDatetime,ExpirationDatetime,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath")] offer offer)
        {
            if (ModelState.IsValid)
            {
                db.Entry(offer).State = EntityState.Modified;
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
            if (offer == null)
            {
                return HttpNotFound();
            }
            return View(offer);
        }

        // POST: /offer/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            offer offer = await db.offer.FindAsync(id);
            db.offer.Remove(offer);
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
