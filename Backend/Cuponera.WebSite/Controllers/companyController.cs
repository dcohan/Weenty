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
    public class companyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        private IEnumerable<company> get(bool all)
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            return cb.Getcompany(all);
        }



        // GET: company
        public async Task<ActionResult> Index()
        {
            var companies = get(true);
            return View(companies);
        }


        public static string SerializeJSON(object unserializedData)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var json = jsonSerialiser.Serialize(unserializedData);
            return json;
        }

        [HttpGet]
        public string GetCompanies(bool all = false)
        {
            var companies = get(all);
            var dataToSerialize = companies.Select(c => new { IdCompany = c.IdCompany, Name = c.Name, DeletionDatetime = c.DeletionDatetime });

            return companyController.SerializeJSON(dataToSerialize);
        }
       

        // GET: company/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            company company = await db.company.FindAsync(id);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        // GET: company/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: company/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "IdCompany,Name,CreationDatetime,ModificationDatetime,DeletionDatetime")] company company)
        {
            if (ModelState.IsValid)
            {
                db.company.Add(company);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(company);
        }

        // GET: company/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            company company = await db.company.FindAsync(id);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        // POST: company/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdCompany,Name,CreationDatetime,ModificationDatetime,DeletionDatetime")] company company)
        {
            if (ModelState.IsValid)
            {
                db.Entry(company).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(company);
        }

        // GET: company/Delete/5
        public async Task<ActionResult> Delete(int id)
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            await cb.Delete(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // GET: company/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            await cb.Activate(id);

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: company/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            company company = await db.company.FindAsync(id);
            if (company == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }


            company.DeletionDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return new HttpStatusCodeResult(HttpStatusCode.OK);
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
