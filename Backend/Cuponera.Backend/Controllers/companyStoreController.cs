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
    builder.EntitySet<companyStore>("companyStore");
    builder.EntitySet<company>("company"); 
    builder.EntitySet<store>("store"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class companyStoreController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/companyStore
        [EnableQuery]
        public IQueryable<companyStore> GetcompanyStore()
        {
            return db.companyStore;
        }

        // GET: odata/companyStore(5)
        [EnableQuery]
        public SingleResult<companyStore> GetcompanyStore([FromODataUri] int key)
        {
            return SingleResult.Create(db.companyStore.Where(companyStore => companyStore.IdCompanyStore == key));
        }

        // PUT: odata/companyStore(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<companyStore> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            companyStore companyStore = await db.companyStore.FindAsync(key);
            if (companyStore == null)
            {
                return NotFound();
            }

            patch.Put(companyStore);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companyStoreExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(companyStore);
        }

        // POST: odata/companyStore
        public async Task<IHttpActionResult> Post(companyStore companyStore)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.companyStore.Add(companyStore);
            await db.SaveChangesAsync();

            return Created(companyStore);
        }

        // PATCH: odata/companyStore(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<companyStore> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            companyStore companyStore = await db.companyStore.FindAsync(key);
            if (companyStore == null)
            {
                return NotFound();
            }

            patch.Patch(companyStore);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companyStoreExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(companyStore);
        }

        // DELETE: odata/companyStore(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            companyStore companyStore = await db.companyStore.FindAsync(key);
            if (companyStore == null)
            {
                return NotFound();
            }

            db.companyStore.Remove(companyStore);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/companyStore(5)/company
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.companyStore.Where(m => m.IdCompanyStore == key).Select(m => m.company));
        }

        // GET: odata/companyStore(5)/store
        [EnableQuery]
        public SingleResult<store> Getstore([FromODataUri] int key)
        {
            return SingleResult.Create(db.companyStore.Where(m => m.IdCompanyStore == key).Select(m => m.store));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool companyStoreExists(int key)
        {
            return db.companyStore.Count(e => e.IdCompanyStore == key) > 0;
        }
    }
}
