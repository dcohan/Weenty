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
    builder.EntitySet<geoRequest>("geoRequest");
    builder.EntitySet<UserProfile>("UserProfile"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class geoRequestController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/geoRequest
        [EnableQuery]
        public IQueryable<geoRequest> GetgeoRequest()
        {
            return db.geoRequest;
        }

        // GET: odata/geoRequest(5)
        [EnableQuery]
        public SingleResult<geoRequest> GetgeoRequest([FromODataUri] int key)
        {
            return SingleResult.Create(db.geoRequest.Where(geoRequest => geoRequest.IdGeoRequest == key));
        }

        [Authorize]
        // PUT: odata/geoRequest(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<geoRequest> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            geoRequest geoRequest = await db.geoRequest.FindAsync(key);
            if (geoRequest == null)
            {
                return NotFound();
            }

            patch.Put(geoRequest);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!geoRequestExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(geoRequest);
        }

        [Authorize]
        // POST: odata/geoRequest
        public async Task<IHttpActionResult> Post(geoRequest geoRequest)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.geoRequest.Add(geoRequest);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (geoRequestExists(geoRequest.IdGeoRequest))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return Created(geoRequest);
        }

        [Authorize]
        // PATCH: odata/geoRequest(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<geoRequest> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            geoRequest geoRequest = await db.geoRequest.FindAsync(key);
            if (geoRequest == null)
            {
                return NotFound();
            }

            patch.Patch(geoRequest);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!geoRequestExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(geoRequest);
        }

        [Authorize]
        // DELETE: odata/geoRequest(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            geoRequest geoRequest = await db.geoRequest.FindAsync(key);
            if (geoRequest == null)
            {
                return NotFound();
            }

            db.geoRequest.Remove(geoRequest);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/geoRequest(5)/UserProfile
        [EnableQuery]
        public SingleResult<UserProfile> GetUserProfile([FromODataUri] int key)
        {
            return SingleResult.Create(db.geoRequest.Where(m => m.IdGeoRequest == key).Select(m => m.UserProfile));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool geoRequestExists(int key)
        {
            return db.geoRequest.Count(e => e.IdGeoRequest == key) > 0;
        }
    }
}
