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

namespace Cuponera.WebSite.Controllers
{
    public class storeController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        private IEnumerable<store> get(bool all, int idCompany, string name, string zipCode, string city, int idState, int pageNumber)
        {
            Cuponera.Backend.Controllers.storeController sc = new Backend.Controllers.storeController();
            IEnumerable<store> stores = sc.Getstore(all, idCompany, name, zipCode, city, idState);

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
        public async Task<ActionResult> Index(bool all = false, int idCompany = 0, string name = null, string zipCode = null, string city = null, int idState = 0, int page = 1)
        {
            var stores = get(all, idCompany, name, zipCode, city, idState, page);
            return View(stores);
        }

        // GET: store/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            category category = await db.category.FindAsync(id);
            if (category == null)
            {
                return HttpNotFound();
            }
            return View(category);
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
        public async Task<ActionResult> Create([Bind(Include = "IdCategory,Name,CreationDatetime,ModificationDatetime,DeletionDatetime")] category category)
        {
            if (ModelState.IsValid)
            {
                db.category.Add(category);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(category);
        }

        // GET: store/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            category category = await db.category.FindAsync(id);
            if (category == null)
            {
                return HttpNotFound();
            }
            return View(category);
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
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            await cb.Delete(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }


        // GET: store/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            await cb.Activate(id);

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
