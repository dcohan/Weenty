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
using Cuponera.WebSite.Helpers;

namespace Cuponera.WebSite.Controllers
{
    public class categoryController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();


        private IEnumerable<category> get(bool all, string name, int pageNumber)
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            IEnumerable<category> companies = cb.Getcategory(all, name);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)companies.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return companies.Skip(elemsToSkip).Take(pageSize);
        }

        [Authorize]
        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            IEnumerable<category> categories = cb.Getcategory(false).OrderBy(c => c.Name);

            return Helpers.JSONHelper.SerializeJSON(categories.ToList().Select(category => new { id = category.IdCategory, name = category.Name }));
        }
        

        // GET: category
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var categories = get(all, name, page);
            return View(categories);
        }

        // GET: category/Details/5
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

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // GET: category/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: category/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
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

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // GET: category/Edit/5
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

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // POST: category/Edit/5
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

        // GET: category/Delete/5
        public async Task<ActionResult> Delete(int id)
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

            category.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();
            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }


        // GET: category/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            await cb.Activate(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: category/Delete/5
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
