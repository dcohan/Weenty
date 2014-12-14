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
using Cuponera.WebSite.Models;
using System.Threading;

namespace Cuponera.WebSite.Controllers
{
    public class storeController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();




        private IEnumerable<store> get(bool all, int idCompany, string name, string zipCode, int idState, int idUser, int pageNumber)
        {
            IQueryable<store> stores = db.store;
            if (!all)
            {
                stores = stores.Where(s => !s.DeletionDatetime.HasValue);
            }

            if (idCompany > 0)
            {
                stores = stores.Where(s => s.company.IdCompany == idCompany);
            }

            if (name != null)
            {
                stores = stores.Where(s => s.Name.Contains(name));
            }

            if (zipCode != null)
            {
                stores = stores.Where(s => s.ZipCode == zipCode);
            }

            if (idState > 0)
            {
                stores = stores.Where(s => s.state.IdState == idState);
            }
            stores = stores.OrderBy(s => s.Name);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)stores.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return stores.Skip(elemsToSkip).Take(pageSize);
        }

        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            IEnumerable<category> categories = cb.Getcategory(false);

            return Helpers.JSONHelper.SerializeJSON(categories.ToList().Select(category => new { id = category.IdCategory, name = category.Name }));
        }
        

        // GET: store
        public async Task<ActionResult> Index(bool all = false, int company = 0, string name = null, string zipCode = null, int state = 0, int page = 1)
        {
            var idUser = 1;
            var stores = get(all, company, name, zipCode, state, idUser, page);

            ViewBag.CanSelectCompany = true;
            return View(stores);
        }

        // GET: store/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            var state = db.state.Where(s => s.IdState == store.IdState);
            if (state != null && state.ToList().Count() > 0) { ViewBag.StateName = state.FirstOrDefault().Name; }

            var company = db.company.Where(s => s.IdCompany == store.IdCompany);
            if (company != null && company.ToList().Count() > 0) { ViewBag.CompanyName = company.FirstOrDefault().Name; }

            if (store.Latitude != null) { ViewBag.Latitude = store.Latitude.ToString().Replace(",", "."); }
            if (store.Longitude != null) { ViewBag.Longitude = store.Longitude.ToString().Replace(",", "."); }
            
            return View(store);
        }

        // GET: store/Create
        public ActionResult Create()
        {
            IEnumerable<state> states = (new stateController()).get(false);
            ViewBag.States = states.Select(s =>
                                new SelectListItem()
                                {
                                    Value = s.IdState.ToString(),
                                    Text = s.Name
                                });
            return View();
        }

        // POST: store/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Name,Address,ContactNumber,ZipCode,IdState,StoreHours,Email,FacebookUrl,WhatsApp")] store store, string Latitude, string Longitude)
        {
            if (ModelState.IsValid)
            {
                if (Latitude != null) { store.Latitude = Convert.ToDouble(Latitude.Replace(".", ",")); }
                if (Longitude != null) { store.Longitude = Convert.ToDouble(Longitude.Replace(".", ",")); }
                //var userId = ((CuponeraIdentity)Thread.CurrentPrincipal.Identity).UserId;
                int idUser = 1;

                var idCompany = db.userCompany.Where(uc => uc.IdUser == idUser);
                if (idCompany.ToList().Count() > 0)
                {
                    store.IdCompany = Convert.ToInt32(idCompany.FirstOrDefault().IdCompany);
                }

                db.store.Add(store);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(store);
        }

        // GET: store/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }


            IEnumerable<state> states = (new stateController()).get(false);
            ViewBag.States = states.Select(s =>
                                new SelectListItem()
                                {
                                    Value = s.IdState.ToString(),
                                    Text = s.Name
                                });

            if (store.Latitude != null) { ViewBag.Latitude = store.Latitude.ToString().Replace(",", "."); }
            if (store.Longitude != null) { ViewBag.Longitude = store.Longitude.ToString().Replace(",", "."); }

            return View(store);
        }

        // POST: store/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdCategory,Name,CreationDatetime,ModificationDatetime,DeletionDatetime")] category category)
        {
            if (ModelState.IsValid)
            {
                db.Entry(category).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(category);
        }

        // GET: store/Delete/5
        public async Task<ActionResult> Delete(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            store.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }


        // GET: store/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            store.DeletionDatetime = null;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: store/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            category category = await db.category.FindAsync(id);
            db.category.Remove(category);
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
