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
using WebMatrix.WebData;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute(MustBeCompanyAdmin=true)]
    public class userCompanyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        private string GetToken(int UserId)
        {
            return db.webpages_Membership.Where(m => m.UserId.Equals(UserId)).Select(m => m.ConfirmationToken).FirstOrDefault();
        }

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
            var stores = db.store.Where(s => !s.DeletionDatetime.HasValue && s.company.DeletionDatetime == null);

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

        public IEnumerable<userCompany> get(bool all = false, int idCompany = 0, int page = 1)
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

            if (!all)
            {
                ucs = ucs.Where(u => (u.store != null && u.store.DeletionDatetime == null) && (u.company != null && u.company.DeletionDatetime == null));
            }

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)ucs.Count() / pageSize));

            ucs = ucs.OrderBy(u => u.company.Name);

            return ucs.ToPagedList(page, pageSize);
        }

        private void GetUsers(userCompany userCompany=null)
        {
            var users = db.UserProfile.Where(u => u.UserId != CuponeraIdentity.CurrentUserId);
            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                users = users.Where(u => u.webpages_Roles.Count() == 0 );
            }

            if (userCompany != null)
            {
                ViewBag.IdUser = new SelectList(users, "UserId", "UserName", userCompany.IdUser);
            }
            else
            {
                ViewBag.IdUser = new SelectList(users, "UserId", "UserName");
            }
        }

        // GET: userCompany
        public async Task<ActionResult> Index(bool all = false, int company = 0, int page = 1)
        {
            var users = get(all, company, page);
            return View(users);
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
            GetUsers();
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany");            
            ViewBag.isAdminBO = 0;
            return View();
        }

        // POST: userCompany/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "IdUserCompany,IdUser,IdCompany,IsAdmin,IdStore")] userCompany userCompany, bool? isAdminBO)
        {
            if (ModelState.IsValid)
            {
                var user = db.UserProfile.Where(up => up.UserId.Equals(userCompany.IdUser)).FirstOrDefault();
                if (new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
                {
                    if ((bool)isAdminBO)
                    {
                        AddUserToCompany(user);
                    }
                    else
                    {
                        RemoveUserToCompany(user);
                    }
                }
                 
                db.userCompany.Add(userCompany);
                await db.SaveChangesAsync();
                EmailHelper.SendNewUserActivation(user.Email, GetToken(user.UserId));
                
                return RedirectToAction("Index");
            }

            GetCompany(userCompany);
            GetStore(userCompany);
            GetUsers(userCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
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
            GetUsers(userCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            return View(userCompany);
        }

        // POST: userCompany/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdUserCompany,IdUser,IdCompany,IsAdmin,IdStore")] userCompany userCompany, bool? isAdminBO)
        {
            if (ModelState.IsValid)
            {
                var user = db.UserProfile.Where(up => up.UserId.Equals(userCompany.IdUser)).FirstOrDefault();
                if (new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
                {
                    if ((bool)isAdminBO)
                    {
                        AddUserToCompany(user);
                    }
                    else
                    {
                        RemoveUserToCompany(user);
                    }
                }

                db.Entry(userCompany).State = EntityState.Modified;
                await db.SaveChangesAsync();

                if (user.Active == null || !(bool)user.Active)
                {
                    EmailHelper.SendNewUserActivation(userCompany.UserProfile.Email, GetToken(user.UserId));
                }

                return RedirectToAction("Index");
            }
            GetCompany(userCompany);
            GetStore(userCompany);
            GetUsers(userCompany);
            ViewBag.IdUserCompany = new SelectList(db.userCompany, "IdUserCompany", "IdUserCompany", userCompany.IdUserCompany);
            return View(userCompany);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
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

            userCompany.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: userCompany/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int? id)
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

            userCompany.DeletionDatetime = null;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
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

        private void AddUserToCompany(Cuponera.Entities.UserProfile user)
        {
            var adminRole = db.webpages_Roles.Where(r => r.RoleName.Equals("admin")).FirstOrDefault();
            if (user.webpages_Roles.Where(ur => ur.RoleId.Equals(adminRole.RoleId)).FirstOrDefault() == null)
            {
                user.webpages_Roles.Add(adminRole);
                db.SaveChanges();
            }
        }

        private void RemoveUserToCompany(Cuponera.Entities.UserProfile user)
        {
            var adminRole = db.webpages_Roles.Where(r => r.RoleName.Equals("admin")).FirstOrDefault();
            if (user.webpages_Roles.Where(ur => ur.RoleId.Equals(adminRole.RoleId)).FirstOrDefault() != null)
            {
                user.webpages_Roles.Remove(adminRole);
                db.SaveChanges();
            }
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
