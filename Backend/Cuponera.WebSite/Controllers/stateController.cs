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
                states = states.Where(s => s.Name.Contains(name));
            }

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)states.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return states.Skip(elemsToSkip).Take(pageSize);

            return states;
        }


        public string GetAllBasicData()
        {
            var states = db.state;

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
        public async Task<ActionResult> Create([Bind(Include="IdState,Name,Link,CreationDatetime,ModificationDatetime,DeletionDatetime,Longitude,Latitude")] state state)
        {
            if (ModelState.IsValid)
            {
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
            return View(state);
        }

        // POST: /state/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include="IdState,Name,Link,CreationDatetime,ModificationDatetime,DeletionDatetime,Longitude,Latitude")] state state)
        {
            if (ModelState.IsValid)
            {
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
            return View(state);
        }

        // POST: /state/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            state state = await db.state.FindAsync(id);
            db.state.Remove(state);
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
