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
    public class ProductController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();



        private IEnumerable<product> get(bool all, string title, int category, int company, int pageNumber)
        {
            Cuponera.Backend.Controllers.productController cb = new Backend.Controllers.productController();
            IEnumerable<product> companies = cb.Getproduct(all, title, category, company);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)companies.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return companies.Skip(elemsToSkip).Take(pageSize);
        }


        // GET: /Product/
        public async Task<ActionResult> Index(bool all = false, string title = null, int category = 0, int company = 0, int page = 1)
        {
            var categories = get(all, title, category, company, page);
            return View(categories);
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
        public async Task<ActionResult> Delete(int id)
        {
            Cuponera.Backend.Controllers.productController cb = new Backend.Controllers.productController();
            await cb.Delete(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }


        // GET: Product/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            Cuponera.Backend.Controllers.productController cb = new Backend.Controllers.productController();
            await cb.Activate(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
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
