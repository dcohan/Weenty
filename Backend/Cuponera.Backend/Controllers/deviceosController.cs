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
    builder.EntitySet<deviceos>("deviceos");
    builder.EntitySet<enabledsections>("enabledsections"); 
    builder.EntitySet<imagesizes>("imagesizes"); 
    builder.EntitySet<profiles>("profiles"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class deviceosController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/deviceos
        [EnableQuery(PageSize = 10)]
        public IQueryable<deviceos> Getdeviceos()
        {
            return db.deviceos;
        }

        // GET: odata/deviceos(5)
        [EnableQuery]
        public SingleResult<deviceos> Getdeviceos([FromODataUri] int key)
        {
            return SingleResult.Create(db.deviceos.Where(deviceos => deviceos.IdDeviceOs == key));
        }

        // PUT: odata/deviceos(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<deviceos> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            deviceos deviceos = await db.deviceos.FindAsync(key);
            if (deviceos == null)
            {
                return NotFound();
            }

            patch.Put(deviceos);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!deviceosExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(deviceos);
        }

        // POST: odata/deviceos
        public async Task<IHttpActionResult> Post(deviceos deviceos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.deviceos.Add(deviceos);
            await db.SaveChangesAsync();

            return Created(deviceos);
        }

        // PATCH: odata/deviceos(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<deviceos> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            deviceos deviceos = await db.deviceos.FindAsync(key);
            if (deviceos == null)
            {
                return NotFound();
            }

            patch.Patch(deviceos);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!deviceosExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(deviceos);
        }

        // DELETE: odata/deviceos(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            deviceos deviceos = await db.deviceos.FindAsync(key);
            if (deviceos == null)
            {
                return NotFound();
            }

            db.deviceos.Remove(deviceos);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/deviceos(5)/enabledsections
        [EnableQuery]
        public IQueryable<enabledsections> Getenabledsections([FromODataUri] int key)
        {
            return db.deviceos.Where(m => m.IdDeviceOs == key).SelectMany(m => m.enabledsections);
        }

        // GET: odata/deviceos(5)/imagesizes
        [EnableQuery]
        public IQueryable<imagesizes> Getimagesizes([FromODataUri] int key)
        {
            return db.deviceos.Where(m => m.IdDeviceOs == key).SelectMany(m => m.imagesizes);
        }

        // GET: odata/deviceos(5)/profiles
        [EnableQuery]
        public IQueryable<profiles> Getprofiles([FromODataUri] int key)
        {
            return db.deviceos.Where(m => m.IdDeviceOs == key).SelectMany(m => m.profiles);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool deviceosExists(int key)
        {
            return db.deviceos.Count(e => e.IdDeviceOs == key) > 0;
        }
    }
}
