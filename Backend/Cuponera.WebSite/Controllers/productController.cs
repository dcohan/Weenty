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
using PagedList;

namespace Cuponera.WebSite.Controllers
{
    public class productController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: product
        public async Task<ActionResult> Index(bool all = false, string title = null, int pageNumber = 1)
        {
            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            var products = db.product.Where(p => title == null || p.Title.ToLower().Contains(title.ToLower()))
                                     .OrderBy(p => p.Title);

            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)products.Count() / pageSize));

            products.ToPagedList(pageNumber, pageSize);

            return View(products);
        }

        // GET: /product/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            return View(product);
        }

        // GET: /product/Create
        public ActionResult Create()
        {
            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name");
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name");
            return View();
        }

        // POST: /product/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include="IdProduct,Title,Active,StartDatetime,ExpirationDatetime,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,IdCategory,Description,IdStore, Price")] product product)
        {
            if (ModelState.IsValid)
            {
                db.product.Add(product);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", product.IdStore);
            return View(product);
        }

        // GET: /product/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", product.IdStore);
            return View(product);
        }

        // POST: /product/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdProduct,Title,Active,StartDatetime,ExpirationDatetime,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,IdCategory,Description,IdStore, Price")] product product)
        {
            if (ModelState.IsValid)
            {
                db.Entry(product).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            ViewBag.IdStore = new SelectList(db.store, "IdStore", "Name", product.IdStore);
            return View(product);
        }

        // GET: /product/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }

            product.DeletionDatetime = DateTime.Now;
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
            product product = await db.product.FindAsync(id);
            if (product == null)
            {
                return HttpNotFound();
            }

            product.DeletionDatetime = null;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: /product/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            product product = await db.product.FindAsync(id);
            db.product.Remove(product);
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
