﻿using System;
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
using Cuponera.WebSite.Models;

namespace Cuponera.WebSite.Controllers
{
    
    public class companyController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        private IEnumerable<company> get(bool all, string name, int pageNumber)
        {
            IQueryable<company> companies = null;

            if (!all)
            {
                //IQueryable<company> companies = db.company.Where(c => !c.DeletionDatetime.HasValue || companySubscription.Where(cs => cs.EndDate >= DateTime.Now).FirstOrDefault() != null);
                var companiesWithOutActiveSubcriptions = db.companySubscription.Where(cs => cs.EndDate < DateTime.Now).Select(cs => cs.IdCompany);
                companies = db.company.Where(c => !(c.DeletionDatetime.HasValue || companiesWithOutActiveSubcriptions.Contains(c.IdCompany) || c.companySubscription.Count() == 0));
            }
            else
            {
                companies = db.company;
            }

            if (name != null)
            {
                companies = companies.Where(c => c.Name.Contains(name));
            }

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                if (CuponeraIdentity.AdminCompany > 0)
                {
                    companies = companies.Where(c => CuponeraIdentity.AdminCompany == c.IdCompany);
                }
                else
                {
                    companies = db.store.Where(s => CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore)).Select(s => s.company).Where(c => c.DeletionDatetime == null);
                    if (!all)
                    {
                        companies = companies.Where(c => !c.DeletionDatetime.HasValue);
                    }

                    if (name != null)
                    {
                        companies = companies.Where(c => c.Name.Contains(name));
                    }
                }
            }


            /* BEGIN Replace its deletion datetime if the subscription is not present or is expired */
            company company;
            for (var i = 0; i < companies.Count(); i++)
            {
                company = companies.ToList()[i];

                if (company.companySubscription.Count() == 0 || company.companySubscription.FirstOrDefault().EndDate < DateTime.Now)
                {
                    company.DeletionDatetime = DateTime.Now;
                }
            }

            if (!all)
            {
                companies = companies.Where(c => !c.DeletionDatetime.HasValue);
            }
            /* END Replace its deletion datetime if the subscription is not present or is expired */



            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)companies.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            companies = companies.OrderBy(c => c.Name);
            return companies.Skip(elemsToSkip).Take(pageSize);
        }

        [AuthorizeUserStoreAttribute]
        // GET: company
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var companies = get(all, name, page);
            return View(companies);
        }

        [Authorize]
        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.companyController cb = new Backend.Controllers.companyController();
            IEnumerable<company> companies = cb.Getcompany(false);

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                if (CuponeraIdentity.AdminCompany > 0)
                {
                    companies = companies.Where(c => CuponeraIdentity.AdminCompany == c.IdCompany);
                }
                else
                {
                    companies = db.store.Where(s => CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore)).Select(s => s.company).Where(c => c.DeletionDatetime == null);
                }
            }

            return Helpers.JSONHelper.SerializeJSON(companies.ToList().Select(company => new { id = company.IdCompany, name = company.Name }));
        }

        [AuthorizeUserStoreAttribute]
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

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin=true)]
        // GET: company/Create
        public ActionResult Create()
        {
            return View();
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
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

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
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

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
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


        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: company/Delete/5
        public async Task<ActionResult> Delete(int id)
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

            company.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: company/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
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

            company.DeletionDatetime = null;
            await db.SaveChangesAsync();

            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
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
