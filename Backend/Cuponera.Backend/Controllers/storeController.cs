using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.ModelBinding;
using System.Web.Http.OData;
using System.Web.Http.OData.Routing;
using Cuponera.Entities;

namespace Cuponera.Backend.Controllers
{
    /*
    The WebApiConfig class may require additional changes to add a route for this controller. Merge these statements into the Register method of the WebApiConfig class as applicable. Note that OData URLs are case sensitive.

    using System.Web.Http.OData.Builder;
    using System.Web.Http.OData.Extensions;
    using Cuponera.Entities;
    ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
    builder.EntitySet<store>("store");
    builder.EntitySet<company>("company"); 
    builder.EntitySet<images>("images"); 
    builder.EntitySet<product>("product"); 
    builder.EntitySet<state>("state"); 
    builder.EntitySet<occasion>("occasion"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class storeController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/store
        [EnableQuery]
        public IQueryable<store> Getstore()
        {
            return db.store.Where(s => !s.DeletionDatetime.HasValue);
        }

        // GET: odata/store(5)
        [EnableQuery]
        public IQueryable<store> Getstore(bool all, int idCompany, string name, string zipCode, string city, int idState)
        {
            IQueryable<store> stores = db.store;
            if (!all)
            {
                stores = stores.Where(p => !p.DeletionDatetime.HasValue);
            }

            if (idCompany > 0)
            {
                stores = stores.Where(p => p.company.IdCompany == idCompany);
            }

            if (name != null)
            {
                stores = stores.Where(p => p.Name.Contains(name));
            }

            if (zipCode != null)
            {
                stores = stores.Where(p => p.ZipCode == zipCode);
            }

            if (city != null)
            {
                stores = stores.Where(p => p.state.Name.Contains(city));
            }
            return stores.OrderBy(c => c.Name);
        }

        // PUT: odata/store(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<store> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            store store = await db.store.FindAsync(key);
            if (store == null)
            {
                return NotFound();
            }

            patch.Put(store);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!storeExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(store);
        }

        // POST: odata/store
        public async Task<IHttpActionResult> Post(store store)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.store.Add(store);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (storeExists(store.IdStore))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return Created(store);
        }

        // PATCH: odata/store(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<store> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            store store = await db.store.FindAsync(key);
            if (store == null)
            {
                return NotFound();
            }

            patch.Patch(store);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!storeExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(store);
        }

        // DELETE: odata/store(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            store store = await db.store.FindAsync(key);
            if (store == null)
            {
                return NotFound();
            }

            store.ModificationDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/store(5)/company
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.store.Where(m => m.IdStore == key).Select(m => m.company));
        }

        // GET: odata/store(5)/images
        [EnableQuery]
        public SingleResult<images> Getimages([FromODataUri] int key)
        {
            return SingleResult.Create(db.store.Where(m => m.IdStore == key).Select(m => m.images));
        }

        // GET: odata/store(5)/product
        [EnableQuery]
        public IQueryable<product> Getproduct([FromODataUri] int key)
        {
            return db.store.Where(m => m.IdStore == key).SelectMany(m => m.product);
        }

        // GET: odata/store(5)/state
        [EnableQuery]
        public SingleResult<state> Getstate([FromODataUri] int key)
        {
            return SingleResult.Create(db.store.Where(m => m.IdStore == key).Select(m => m.state));
        }

        // GET: odata/store(5)/occasion
        [EnableQuery]
        public IQueryable<occasion> Getoccasion([FromODataUri] int key)
        {
            return db.store.Where(m => m.IdStore == key).SelectMany(m => m.occasion);
        }



        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool storeExists(int key)
        {
            return db.store.Count(e => e.IdStore == key) > 0;
        }
    }
}
