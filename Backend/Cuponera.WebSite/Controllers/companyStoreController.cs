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
    public class companyStoreController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: companyStore
        public async Task<ActionResult> Index()
        {
            var companyStore = db.companyStore.Include(c => c.company).Include(c => c.store);
            return View(await companyStore.ToListAsync());
        }

        // GET: companyStore/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            companyStore companyStore = await db.companyStore.FindAsync(id);
            if (companyStore == null)
            {
                return HttpNotFound();
            }
            return View(companyStore);
        }

        // GET: companyStore/Create
        public ActionResult Create()
        {
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name");
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name");
            return View();
        }

        // POST: companyStore/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "IdCompanyStore,IdCompany,IdStore,CreationDatetime,ModificationDatetime,DeletionDatetime")] companyStore companyStore)
        {
            if (ModelState.IsValid)
            {
                db.companyStore.Add(companyStore);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", companyStore.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", companyStore.IdStore);
            return View(companyStore);
        }

        // GET: companyStore/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            companyStore companyStore = await db.companyStore.FindAsync(id);
            if (companyStore == null)
            {
                return HttpNotFound();
            }
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", companyStore.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", companyStore.IdStore);
            return View(companyStore);
        }

        // POST: companyStore/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdCompanyStore,IdCompany,IdStore,CreationDatetime,ModificationDatetime,DeletionDatetime")] companyStore companyStore)
        {
            if (ModelState.IsValid)
            {
                db.Entry(companyStore).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", companyStore.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", companyStore.IdStore);
            return View(companyStore);
        }

        // GET: companyStore/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            companyStore companyStore = await db.companyStore.FindAsync(id);
            if (companyStore == null)
            {
                return HttpNotFound();
            }
            return View(companyStore);
        }

        // POST: companyStore/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            companyStore companyStore = await db.companyStore.FindAsync(id);
            db.companyStore.Remove(companyStore);
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
