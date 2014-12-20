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
        public IEnumerable<subscription> get(bool all = true)
        {
            IEnumerable<subscription> subscriptions = db.subscription;

            if (!all)
            {
                subscriptions = subscriptions.Where(s => s.DeletionDatetime == null);
            }

            return subscriptions;
        }



        // GET: /companySubscription/
        public async Task<ActionResult> Index(bool all = false)
        {
            var subscriptions = get(all);
            ViewBag.Subscriptions = subscriptions;

            var companies = db.company.OrderBy(c => c.Name);
            var currentCompanySubscriptions = db.companySubscription;
            var companySubscriptions = new List<Cuponera.Entities.companySubscription>();
            

            foreach (var company in companies)
            {
                var cs = new companySubscription();
                cs.IdCompany = company.IdCompany;
                cs.company = company;
                var filteredCompanySubscription = currentCompanySubscriptions.Where(c => c.IdCompany == company.IdCompany);
                
                cs.IdSubscription = 0;
                cs.EndDate = DateTime.MinValue;
                if (filteredCompanySubscription != null && filteredCompanySubscription.Count() > 0) {
                    var firstCompanySubscription = filteredCompanySubscription.FirstOrDefault();
                    cs.IdSubscription = firstCompanySubscription.IdSubscription;
                    cs.EndDate = firstCompanySubscription.EndDate;
                }
                
                
                companySubscriptions.Add(cs);
            } 

            return View(companySubscriptions);
        }


        // POST: /companySubscription/
        [HttpPost]
        public async Task<ActionResult> Index(int subscriptions, int[] companies, DateTime? endDate)
        {
            int duration = 0;
            if (!endDate.HasValue)
            {
                var subscription = db.subscription.Where(s => s.IdSubscription == subscriptions);
                duration = subscription.FirstOrDefault().duration;
            }

            for (int a = 0; a < companies.Count(); a++)
            {
                IEnumerable<companySubscription> companySubscription = db.companySubscription;
                companySubscription = companySubscription.Where(cs => cs.IdCompany == companies[a]);

                if (companySubscription != null && companySubscription.Count() > 0)
                {
                    var cs = companySubscription.FirstOrDefault();
                    cs.IdSubscription = subscriptions;
                    if (endDate.HasValue)
                    {
                        cs.EndDate = endDate.Value;
                    }
                    cs.ModificationDatetime = DateTime.Now;
                }
                else
                {
                    var cs = new companySubscription();
                    cs.IdSubscription = subscriptions;
                    cs.IdCompany = companies.ElementAt(a);
                    cs.CreationDatetime = DateTime.Now;
                    cs.EndDate = endDate.HasValue ? endDate.Value : DateTime.Now.AddDays(duration);

                    db.companySubscription.Add(cs);
                }

                await db.SaveChangesAsync();
            }
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
