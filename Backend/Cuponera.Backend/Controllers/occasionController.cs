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
    builder.EntitySet<occasion>("occasion");
    builder.EntitySet<store>("store"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class occasionController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/occasion
        [EnableQuery]
        public IQueryable<occasion> Getoccasion()
        {
            return db.occasion.Where(o => !o.DeletionDateTime.HasValue);
        }

        // GET: odata/occasion(5)
        [EnableQuery]
        public SingleResult<occasion> Getoccasion([FromODataUri] int key)
        {
            return SingleResult.Create(db.occasion.Where(occasion => occasion.IdEvent == key && !occasion.DeletionDateTime.HasValue));
        }

        // PUT: odata/occasion(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<occasion> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            occasion occasion = await db.occasion.FindAsync(key);
            if (occasion == null)
            {
                return NotFound();
            }

            patch.Put(occasion);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!occasionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(occasion);
        }

        // POST: odata/occasion
        public async Task<IHttpActionResult> Post(occasion occasion)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.occasion.Add(occasion);
            await db.SaveChangesAsync();

            return Created(occasion);
        }

        // PATCH: odata/occasion(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<occasion> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            occasion occasion = await db.occasion.FindAsync(key);
            if (occasion == null)
            {
                return NotFound();
            }

            patch.Patch(occasion);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!occasionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(occasion);
        }

        // DELETE: odata/occasion(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            occasion occasion = await db.occasion.FindAsync(key);
            if (occasion == null)
            {
                return NotFound();
            }

            occasion.DeletionDateTime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/occasion(5)/store
        [EnableQuery]
        public SingleResult<store> Getstore([FromODataUri] int key)
        {
            return SingleResult.Create(db.occasion.Where(m => m.IdEvent == key).Select(m => m.store));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool occasionExists(int key)
        {
            return db.occasion.Count(e => e.IdEvent == key) > 0;
        }
    }
}
