using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.WebSite.Helpers;
using Cuponera.Entities;

namespace Cuponera.WebSite.Controllers
{
    public class companyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        private IEnumerable<company> get(bool all, string name, int pageNumber)
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            IEnumerable<company> companies = cb.Getcompany(all, name);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)companies.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return companies.Skip(elemsToSkip).Take(pageSize);
        }

        // GET: company
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var companies = get(all, name, page);
            return View(companies);
        }


        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            IEnumerable<company> companies = cb.Getcompany(false);

            return Helpers.JSONHelper.SerializeJSON(companies.ToList().Select(company => new { id = company.IdCompany, name = company.Name }));
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
        public async Task<ActionResult> Create([Bind(Include = "IdCompany,Name,Contact,Telephone,Email,CreationDatetime,ModificationDatetime,DeletionDatetime")] company company)
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
        public async Task<ActionResult> Edit([Bind(Include = "IdCompany,Name,Contact,Telephone,Email,CreationDatetime,ModificationDatetime,DeletionDatetime")] company company)
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
