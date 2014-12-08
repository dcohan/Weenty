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

namespace Cuponera.WebSite.Controllers
{
    public class preHomeController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: /preHome/
        public async Task<ActionResult> Index()
        {
            return View(await db.preHomeImages.ToListAsync());
        }

        // GET: /preHome/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            preHomeImages prehomeimages = await db.preHomeImages.FindAsync(id);
            if (prehomeimages == null)
            {
                return HttpNotFound();
            }
            return View(prehomeimages);
        }

        // GET: /preHome/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /preHome/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include="IdPreHomeImage,Active,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,Latitude,Longitude")] preHomeImages prehomeimages)
        {
            if (ModelState.IsValid)
            {
                db.preHomeImages.Add(prehomeimages);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(prehomeimages);
        }

        // GET: /preHome/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            preHomeImages prehomeimages = await db.preHomeImages.FindAsync(id);
            if (prehomeimages == null)
            {
                return HttpNotFound();
            }
            return View(prehomeimages);
        }

        // POST: /preHome/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include="IdPreHomeImage,Active,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,Latitude,Longitude")] preHomeImages prehomeimages)
        {
            if (ModelState.IsValid)
            {
                db.Entry(prehomeimages).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(prehomeimages);
        }

        // GET: /preHome/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            preHomeImages prehomeimages = await db.preHomeImages.FindAsync(id);
            if (prehomeimages == null)
            {
                return HttpNotFound();
            }
            return View(prehomeimages);
        }

        // POST: /preHome/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            preHomeImages prehomeimages = await db.preHomeImages.FindAsync(id);
            db.preHomeImages.Remove(prehomeimages);
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
