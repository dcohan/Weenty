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
    public class ProductController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: /Product/
        public async Task<ActionResult> Index()
        {
            var product = db.product.Include(p => p.category).Include(p => p.company);
            return View(await product.ToListAsync());
        }

        // GET: /Product/Details/5
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

        // GET: /Product/Create
        public ActionResult Create()
        {
            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name");
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name");
            return View();
        }

        // POST: /Product/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include="IdProduct,Title,Active,StartDatetime,ExpirationDatetime,ItemOrder,IdCompany,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,IdCategory,Description")] product product)
        {
            if (ModelState.IsValid)
            {
                db.product.Add(product);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", product.IdCompany);
            return View(product);
        }

        // GET: /Product/Edit/5
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
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", product.IdCompany);
            return View(product);
        }

        // POST: /Product/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include="IdProduct,Title,Active,StartDatetime,ExpirationDatetime,ItemOrder,IdCompany,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath,IdCategory,Description")] product product)
        {
            if (ModelState.IsValid)
            {
                db.Entry(product).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdCategory = new SelectList(db.category, "IdCategory", "Name", product.IdCategory);
            ViewBag.IdCompany = new SelectList(db.company, "IdCompany", "Name", product.IdCompany);
            return View(product);
        }

        // GET: /Product/Delete/5
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
            return View(product);
        }

        // POST: /Product/Delete/5
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
