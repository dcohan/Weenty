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
using Cuponera.WebSite.Helpers;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
    public class subscriptionController : UploadImagesBaseController
    {
        private CuponeraEntities db = new CuponeraEntities();

        public subscriptionController()
            : base(UploadImagesEnum.subscription)
        {

        }

        public IEnumerable<subscription> get(bool all = true, string name = null, int pageNumber = 1)
        {
            IEnumerable<subscription> subscriptions = db.subscription;

            if (!all)
            {
                subscriptions = subscriptions.Where(s => s.DeletionDatetime == null);
            }

            if (name != null)
            {
                subscriptions = subscriptions.Where(s => s.Name.ToLowerInvariant().Contains(name.ToLowerInvariant()));
            }

            subscriptions = subscriptions.OrderBy(s => s.SortFactor);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)subscriptions.Count() / pageSize));
            return subscriptions.ToPagedList(pageNumber, pageSize);
        }


        public string GetAllBasicData()
        {
            var states = db.state.Where(s => s.DeletionDatetime == null);

            return Helpers.JSONHelper.SerializeJSON(states.ToList().Select(state => new { id = state.IdState, name = state.Name }));
        }
        
        // GET: /subscription/
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var subscriptions = get(all, name, page);

            return View(subscriptions);
        }

        // GET: /subscription/Details/5
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

            ViewBag.AllSubscriptions = db.subscription.Where(s => s.DeletionDatetime == null).OrderBy(s => s.SortFactor);

            return View(subscription);
        }

        // GET: /subscription/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /subscription/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Name,SortFactor,Duration")] subscription subscription, string Pricing, List<HttpPostedFileBase> fileUpload)
        {
            if (ModelState.IsValid)
            {
                if (!String.IsNullOrEmpty(Pricing)) { subscription.Pricing = Convert.ToDecimal(Pricing); }

                fileUpload = FilterFiles(fileUpload);
                if (fileUpload.Count() == 1)
                {
                    subscription.Icon = GeneratePhisicalFile(fileUpload.FirstOrDefault());
                }


                db.subscription.Add(subscription);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subscription);
        }

        // GET: /subscription/Edit/5
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
            ViewBag.AllSubscriptions = all_subscriptions.OrderBy(s => s.SortFactor);

            return View(subscription);
        }


        // POST: subscription/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdSubscription,Name,SortFactor,Duration")] subscription subscription, string Pricing, List<HttpPostedFileBase> fileUpload)
        {
            if (ModelState.IsValid)
            {
                if (!String.IsNullOrEmpty(Pricing)) { subscription.Pricing = Convert.ToDecimal(Pricing); }
                if (fileUpload.Count > 0) { subscription.Icon = GeneratePhisicalFile(fileUpload.FirstOrDefault()); }

                db.Entry(subscription).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subscription);
        }



        // GET: /subscription/Delete/5
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

        // POST: subscription/Activate/5
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
