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
    builder.EntitySet<profile>("profile");
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class profileController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/profile
        [EnableQuery]
        public IQueryable<profile> Getprofile()
        {
            return db.profile;
        }

        // GET: odata/profile(5)
        [EnableQuery]
        public SingleResult<profile> Getprofile([FromODataUri] int key)
        {
            return SingleResult.Create(db.profile.Where(profile => profile.IdProfile == key));
        }

        // PUT: odata/profile(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<profile> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            profile profile = await db.profile.FindAsync(key);
            if (profile == null)
            {
                return NotFound();
            }

            patch.Put(profile);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!profileExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(profile);
        }

        // POST: odata/profile
        public async Task<IHttpActionResult> Post(profile profile)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.profile.Add(profile);
            await db.SaveChangesAsync();

            return Created(profile);
        }

        // PATCH: odata/profile(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<profile> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            profile profile = await db.profile.FindAsync(key);
            if (profile == null)
            {
                return NotFound();
            }

            patch.Patch(profile);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!profileExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(profile);
        }

        // DELETE: odata/profile(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            profile profile = await db.profile.FindAsync(key);
            if (profile == null)
            {
                return NotFound();
            }

            db.profile.Remove(profile);
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

        private bool profileExists(int key)
        {
            return db.profile.Count(e => e.IdProfile == key) > 0;
        }
    }
}
