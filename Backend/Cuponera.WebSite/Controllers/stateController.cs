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

namespace Cuponera.WebSite.Controllers
{
    public class stateController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();
        public IEnumerable<state> get(bool all = true, string name = null, int pageNumber = 1)
        {
            IEnumerable<state> states = db.state;

            if (!all)
            {
                states = states.Where(s => s.DeletionDatetime == null);
            }

            if (name != null)
            {
                states = states.Where(s => s.Name.ToLowerInvariant().Contains(name.ToLowerInvariant()));
            }

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)states.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return states.Skip(elemsToSkip).Take(pageSize);
        }


        public string GetAllBasicData()
        {
            var states = db.state.Where(s => s.DeletionDatetime == null);

            return Helpers.JSONHelper.SerializeJSON(states.ToList().Select(state => new { id = state.IdState, name = state.Name }));
        }
        
        // GET: /state/
        public async Task<ActionResult> Index(bool all = false, string name = null, int page = 1)
        {
            var states = get(all, name, page);
            return View(states);
        }

        // GET: /state/Details/5
        public async Task<ActionResult> Details(int? id)
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

            if (state.Latitude != null) { ViewBag.Latitude = state.Latitude.ToString().Replace(",", "."); }
            if (state.Longitude != null) { ViewBag.Longitude = state.Longitude.ToString().Replace(",", "."); }

            return View(state);
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
            state state = await db.state.FindAsync(id);
            if (state == null)
            {
                return HttpNotFound();
            }

            if (state.Latitude != null) { ViewBag.Latitude = state.Latitude.ToString().Replace(",", "."); }
            if (state.Longitude != null) { ViewBag.Longitude = state.Longitude.ToString().Replace(",", "."); }

            return View(state);
        }

        // POST: /state/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include="IdState,Name,Link")] state state, string Latitude, string Longitude)
        {
            if (ModelState.IsValid)
            {
                if (Latitude != null) { state.Latitude = Convert.ToDouble(Latitude.Replace(".", ",")); }
                if (Longitude != null) { state.Longitude = Convert.ToDouble(Longitude.Replace(".", ",")); }

                db.Entry(state).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(state);
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

        // POST: state/Activate/5
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
