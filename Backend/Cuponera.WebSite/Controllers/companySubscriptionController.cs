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
    public class companySubscriptionController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();
        public IEnumerable<companySubscription> get(bool all = true)
        {
            IEnumerable<companySubscription> companySubscription = db.companySubscription;

            if (!all)
            {
                companySubscription = companySubscription.Where(s => s.DeletionDatetime == null);
            }

            return companySubscription;
        }



        // GET: /companySubscription/
        public async Task<ActionResult> Index(bool all = false)
        {
            var subscriptions = get(all);
            ViewBag.Subscriptions = subscriptions;

            var companies = db.company;
            var currentCompanySubscriptions = db.companySubscription;
            var companySubscriptions = new List<Cuponera.Entities.companySubscription>();
            

            foreach (var company in companies)
            {
                var cs = new companySubscription();
                cs.IdCompany = company.IdCompany;
                cs.company = company;
                var filteredCompanySubscription = currentCompanySubscriptions.Where(c => c.IdCompany == company.IdCompany);
                cs.IdSubscription = filteredCompanySubscription.Count() > 0 ? filteredCompanySubscription.FirstOrDefault().IdSubscription : 0;
                companySubscriptions.Add(cs);
            } 

            return View(companySubscriptions);
        }

        // GET: /companySubscription/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subscription subscription = await db.subscription.FindAsync(id);
            if (subscription == null)
            {
                return HttpNotFound();
            }


            var all_subscriptions = get(subscription.DeletionDatetime != null);

            return View(subscription);
        }

        // GET: /companySubscription/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /companySubscription/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Name,SortFactor,duration")] subscription subscription, string Pricing)
        {
            if (ModelState.IsValid)
            {
                if (!String.IsNullOrEmpty(Pricing)) { subscription.Pricing = Convert.ToDecimal(Pricing); }
                db.subscription.Add(subscription);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subscription);
        }

        // GET: /companySubscription/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subscription subscription = await db.subscription.FindAsync(id);
            if (subscription == null)
            {
                return HttpNotFound();
            }

            var all_subscriptions = get(false);

            return View(subscription);
        }


        // POST: companySubscription/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdSubscription,Name,SortFactor,duration")] subscription subscription, string Pricing)
        {
            if (ModelState.IsValid)
            {
                if (!String.IsNullOrEmpty(Pricing)) { subscription.Pricing = Convert.ToDecimal(Pricing); }

                db.Entry(subscription).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subscription);
        }



        // GET: /companySubscription/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subscription subscription = await db.subscription.FindAsync(id);
            if (subscription == null)
            {
                return HttpNotFound();
            }


            subscription.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();
            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: companySubscription/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            subscription subscription = await db.subscription.FindAsync(id);
            if (subscription == null)
            {
                return HttpNotFound();
            }

            subscription.DeletionDatetime = null;
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
