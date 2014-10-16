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
using Cuponera.Backend.Data;

namespace Cuponera.Backend.Controllers
{
    /*
    The WebApiConfig class may require additional changes to add a route for this controller. Merge these statements into the Register method of the WebApiConfig class as applicable. Note that OData URLs are case sensitive.

    using System.Web.Http.OData.Builder;
    using System.Web.Http.OData.Extensions;
    using Cuponera.Backend.Data;
    ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
    builder.EntitySet<store>("stores");
    builder.EntitySet<product>("product"); 
    builder.EntitySet<state>("state"); 
    builder.EntitySet<userStore>("userStore"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class storesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/stores
        [EnableQuery]
        public IQueryable<store> Getstores()
        {
            return db.store;
        }

        // GET: odata/stores(5)
        [EnableQuery]
        public SingleResult<store> Getstore([FromODataUri] int key)
        {
            return SingleResult.Create(db.store.Where(store => store.IdStore == key));
        }

        // PUT: odata/stores(5)
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

        // POST: odata/stores
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

        // PATCH: odata/stores(5)
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

        // DELETE: odata/stores(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            store store = await db.store.FindAsync(key);
            if (store == null)
            {
                return NotFound();
            }

            db.store.Remove(store);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/stores(5)/product
        [EnableQuery]
        public IQueryable<product> Getproduct([FromODataUri] int key)
        {
            return db.store.Where(m => m.IdStore == key).SelectMany(m => m.product);
        }

        // GET: odata/stores(5)/state
        [EnableQuery]
        public SingleResult<state> Getstate([FromODataUri] int key)
        {
            return SingleResult.Create(db.store.Where(m => m.IdStore == key).Select(m => m.state));
        }

        // GET: odata/stores(5)/userStore
        [EnableQuery]
        public IQueryable<userStore> GetuserStore([FromODataUri] int key)
        {
            return db.store.Where(m => m.IdStore == key).SelectMany(m => m.userStore);
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
