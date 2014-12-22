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
using Cuponera.WebSite.Helpers;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute]
    public class userCompanyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: userCompany
        public async Task<ActionResult> Index()
        {
            var userCompany = db.userCompany.Include(u => u.company).Include(u => u.store).Include(u => u.userCompany1).Include(u => u.userCompany2).Include(u => u.UserProfile);
            return View(await userCompany.ToListAsync());
        }

        
        // GET: userCompany/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            userCompany userCompany = await db.userCompany.FindAsync(id);
            if (userCompany == null)
            {
                return HttpNotFound();
            }
            return View(userCompany);
        }

        // GET: userCompany/Create
        public ActionResult Create()
        {
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name");
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name");
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany");
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany");
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName");
            return View();
        }

        // POST: userCompany/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "IdUserCompany,IdUser,IdCompany,IsAdmin,IdStore")] userCompany userCompany)
        {
            if (ModelState.IsValid)
            {
                db.userCompany.Add(userCompany);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", userCompany.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", userCompany.IdStore);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);
            return View(userCompany);
        }

        // GET: userCompany/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            userCompany userCompany = await db.userCompany.FindAsync(id);
            if (userCompany == null)
            {
                return HttpNotFound();
            }
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", userCompany.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", userCompany.IdStore);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);
            return View(userCompany);
        }

        // POST: userCompany/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdUserCompany,IdUser,IdCompany,IsAdmin,IdStore")] userCompany userCompany)
        {
            if (ModelState.IsValid)
            {
                db.Entry(userCompany).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", userCompany.IdCompany);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", userCompany.IdStore);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);
            return View(userCompany);
        }

        // GET: userCompany/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            userCompany userCompany = await db.userCompany.FindAsync(id);
            if (userCompany == null)
            {
                return HttpNotFound();
            }
            return View(userCompany);
        }

        // POST: userCompany/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            userCompany userCompany = await db.userCompany.FindAsync(id);
            db.userCompany.Remove(userCompany);
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
