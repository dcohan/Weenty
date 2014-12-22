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
using System.Configuration;
using Cuponera.WebSite.Models;
using PagedList;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute(MustBeCompanyAdmin=true)]
    public class userCompanyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        public void GetCompany(userCompany userCompany=null)
        {
            var companies = db.company.Where(c => !c.DeletionDatetime.HasValue);

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                if (CuponeraIdentity.AdminCompany > 0)
                {
                    companies = companies.Where(c => CuponeraIdentity.AdminCompany == c.IdCompany);
                }
                else
                {
                    companies = db.store.Where(s => CuponeraPrincipal.CanAdminStore(s.IdStore)).Select(s => s.company);
                }
            }

            if (userCompany != null)
            {
                ViewBag.IdCompany = new SelectList(companies, "IdCompany", "Name", userCompany.IdCompany);
            }
            else
            {
                ViewBag.IdCompany = new SelectList(companies, "IdCompany", "Name");
            }
        }

        public void GetStore(userCompany userCompany = null)
        {
            var stores = db.store.Where(s => !s.DeletionDatetime.HasValue);

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                if (CuponeraIdentity.AdminCompany > 0)
                {
                    stores = stores.Where(s => CuponeraIdentity.AdminCompany == s.IdCompany);
                }
                else
                {
                    stores = stores.Where(s => CuponeraPrincipal.CanAdminStore(s.IdStore));
                }
            }

            if (userCompany != null)
            {
                ViewBag.IdStore = new SelectList(stores, "IdStore", "Name", userCompany.IdCompany);
            }
            else
            {
                ViewBag.IdStore = new SelectList(stores, "IdStore", "Name");
            }
        }

        public IEnumerable<userCompany> get(int idCompany = 0, int page = 1)
        {
            IEnumerable<userCompany> ucs = db.userCompany;

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                ucs = ucs.Where(u => CuponeraIdentity.AdminCompany == u.IdCompany);

                ViewBag.CanSelectCompany = CuponeraIdentity.AdminCompany > 0;
            }
            else
            {
                ViewBag.CanSelectCompany = true;
            }

            if (idCompany > 0)
            {
                ucs = ucs.Where(u => u.IdCompany.Equals(idCompany));
            }

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)ucs.Count() / pageSize));

            ucs = ucs.OrderBy(u => u.company.Name);

            return ucs.ToPagedList(page, pageSize);
        }

        // GET: userCompany
        public async Task<ActionResult> Index(int company = 0, int page = 1)
        {
            return View(get(company, page));
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
            GetCompany(userCompany);
            return View(userCompany);
        }

        // GET: userCompany/Create
        public ActionResult Create()
        {
            GetCompany();
            GetStore();
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany");
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany");            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName");
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName");            return View();
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

            GetCompany(userCompany);
            GetStore(userCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);
            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);            return View(userCompany);
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

            GetCompany(userCompany);
            GetStore(userCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);            ViewBag.IdUser = new SelectList(db.UserProfile, "UserId", "UserName", userCompany.IdUser);
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
            GetCompany(userCompany);
            GetStore(userCompany);
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
