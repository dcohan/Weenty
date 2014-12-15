﻿using System;
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
    public class subscriptionController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();
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
        
        // GET: /state/
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var subscriptions = get(all, name, page);

            return View(subscriptions);
        }

        // GET: /state/Details/5
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

            var all_subscriptions = get(false);
            ViewBag.AllSubscriptions = all_subscriptions.OrderBy(s => s.SortFactor);

            return View(subscription);
        }

        // GET: /state/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /state/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include="Name,Link")] state state, string Latitude, string Longitude)
        {
            if (ModelState.IsValid)
            {
                if (Latitude != null) { state.Latitude = Convert.ToDouble(Latitude.Replace(".", ",")); }
                if (Longitude != null) { state.Longitude = Convert.ToDouble(Longitude.Replace(".", ",")); }

                db.state.Add(state);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(state);
        }

        // GET: /state/Edit/5
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

        // POST: /state/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "Name,SortFactor,duration")] subscription subscription, int SortFactor, int duration, string Price)
        {
            if (ModelState.IsValid)
            {
                if (!String.IsNullOrEmpty(Price)) { subscription.Pricing = Convert.ToDecimal(Price.Replace(",", ".")); }
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(subscription);
        }

        // GET: /state/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            state state = await db.state.FindAsync(id);
            if (state == null)
            {
                return HttpNotFound();
            }


            state.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();
            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        // POST: category/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            state state = await db.state.FindAsync(id);
            if (state == null)
            {
                return HttpNotFound();
            }

            state.DeletionDatetime = null;
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