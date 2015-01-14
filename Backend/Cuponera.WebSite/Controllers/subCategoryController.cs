using Cuponera.Entities;
using Cuponera.WebSite.Helpers;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace Cuponera.WebSite.Controllers
{
    public class subcategoryController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        public subcategoryController()
        {
        }


        private void GetCategories(subcategory subcategory = null)
        {
            var categories = db.category.Where(c => c.DeletionDatetime == null).OrderBy(c => c.Name);

            ViewBag.IdCategory = new SelectList(categories, "IdCategory", "Name", subcategory != null ? subcategory.IdCategory : 0);
        }

        [Authorize]
        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            IEnumerable<category> categories = cb.Getcategory(false).OrderBy(c => c.Name);

            return Helpers.JSONHelper.SerializeJSON(categories.ToList().Select(category => new { id = category.IdCategory, name = category.Name }));
        }


        // GET: subcategory
        public async Task<ActionResult> Index(bool all = false, string name = null, int category = 0, int page = 1)
        {
            IQueryable<subcategory> subcategories = db.subcategory;
            if (!all)
            {
                subcategories = subcategories.Where(s => s.DeletionDatetime == null);
            }

            if (category > 0)
            {
                subcategories = subcategories.Where(s => s.IdCategory == category);
            }

            if (!string.IsNullOrEmpty(name))
            {
                subcategories = subcategories.Where(s => s.Name.ToLower().Contains(name.ToLower()));
            }

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)subcategories.Count() / pageSize));

            int elemsToSkip = pageSize * (page - 1);
            subcategories = subcategories.OrderBy(s=> s.Name).Skip(elemsToSkip).Take(pageSize);


            return View(subcategories);
        }

        // GET: subcategory/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subcategory subcategory = await db.subcategory.FindAsync(id);
            if (subcategory == null)
            {
                return HttpNotFound();
            }
            return View(subcategory);
        }

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // GET: subcategory/Create
        public ActionResult Create()
        {
            GetCategories();
            return View();
        }

        // POST: subcategory/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        public async Task<ActionResult> Create([Bind(Include = "Name,IdCategory")] subcategory subcategory)
        {
            if (ModelState.IsValid)
            {
                db.subcategory.Add(subcategory);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(subcategory);
        }

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // GET: subcategory/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subcategory subcategory = await db.subcategory.FindAsync(id);
            if (subcategory == null)
            {
                return HttpNotFound();
            }

            GetCategories(subcategory);
            return View(subcategory);
        }

        [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
        // POST: subcategory/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdSubCategory,Name,IdCategory")] subcategory subcategory)
        {
            if (ModelState.IsValid)
            {
                db.Entry(subcategory).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subcategory);
        }

        // GET: subcategory/Delete/5
        public async Task<ActionResult> Delete(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subcategory subcategory = await db.subcategory.FindAsync(id);
            if (subcategory == null)
            {
                return HttpNotFound();
            }

            subcategory.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();
            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }


        // GET: subcategory/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subcategory subcategory = await db.subcategory.FindAsync(id);
            if (subcategory == null)
            {
                return HttpNotFound();
            }

            subcategory.DeletionDatetime = null;
            await db.SaveChangesAsync();
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
