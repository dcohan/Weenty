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
    builder.EntitySet<prehomeimages>("prehomeimages");
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class prehomeimagesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/prehomeimages
        [EnableQuery]
        public IQueryable<prehomeimages> Getprehomeimages()
        {
            return db.prehomeimages;
        }

        // GET: odata/prehomeimages(5)
        [EnableQuery]
        public SingleResult<prehomeimages> Getprehomeimages([FromODataUri] int key)
        {
            return SingleResult.Create(db.prehomeimages.Where(prehomeimages => prehomeimages.IdPreHomeImage == key));
        }

        // PUT: odata/prehomeimages(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<prehomeimages> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            prehomeimages prehomeimages = await db.prehomeimages.FindAsync(key);
            if (prehomeimages == null)
            {
                return NotFound();
            }

            patch.Put(prehomeimages);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!prehomeimagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(prehomeimages);
        }

        // POST: odata/prehomeimages
        public async Task<IHttpActionResult> Post(prehomeimages prehomeimages)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.prehomeimages.Add(prehomeimages);
            await db.SaveChangesAsync();

            return Created(prehomeimages);
        }

        // PATCH: odata/prehomeimages(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<prehomeimages> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            prehomeimages prehomeimages = await db.prehomeimages.FindAsync(key);
            if (prehomeimages == null)
            {
                return NotFound();
            }

            patch.Patch(prehomeimages);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!prehomeimagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(prehomeimages);
        }

        // DELETE: odata/prehomeimages(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            prehomeimages prehomeimages = await db.prehomeimages.FindAsync(key);
            if (prehomeimages == null)
            {
                return NotFound();
            }

            db.prehomeimages.Remove(prehomeimages);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool prehomeimagesExists(int key)
        {
            return db.prehomeimages.Count(e => e.IdPreHomeImage == key) > 0;
        }
    }
}
