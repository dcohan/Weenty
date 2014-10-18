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
    builder.EntitySet<devicetypes>("devicetypes");
    builder.EntitySet<profile>("profile"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class devicetypesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/devicetypes
        [EnableQuery]
        public IQueryable<devicetypes> Getdevicetypes()
        {
            return db.devicetypes;
        }

        // GET: odata/devicetypes(5)
        [EnableQuery]
        public SingleResult<devicetypes> Getdevicetypes([FromODataUri] int key)
        {
            return SingleResult.Create(db.devicetypes.Where(devicetypes => devicetypes.IdDeviceType == key));
        }

        // PUT: odata/devicetypes(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<devicetypes> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            devicetypes devicetypes = await db.devicetypes.FindAsync(key);
            if (devicetypes == null)
            {
                return NotFound();
            }

            patch.Put(devicetypes);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!devicetypesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(devicetypes);
        }

        // POST: odata/devicetypes
        public async Task<IHttpActionResult> Post(devicetypes devicetypes)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.devicetypes.Add(devicetypes);
            await db.SaveChangesAsync();

            return Created(devicetypes);
        }

        // PATCH: odata/devicetypes(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<devicetypes> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            devicetypes devicetypes = await db.devicetypes.FindAsync(key);
            if (devicetypes == null)
            {
                return NotFound();
            }

            patch.Patch(devicetypes);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!devicetypesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(devicetypes);
        }

        // DELETE: odata/devicetypes(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            devicetypes devicetypes = await db.devicetypes.FindAsync(key);
            if (devicetypes == null)
            {
                return NotFound();
            }

            db.devicetypes.Remove(devicetypes);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/devicetypes(5)/profile
        [EnableQuery]
        public IQueryable<profile> Getprofile([FromODataUri] int key)
        {
            return db.devicetypes.Where(m => m.IdDeviceType == key).SelectMany(m => m.profile);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool devicetypesExists(int key)
        {
            return db.devicetypes.Count(e => e.IdDeviceType == key) > 0;
        }
    }
}
