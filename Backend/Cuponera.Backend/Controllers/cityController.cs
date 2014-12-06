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
    builder.EntitySet<city>("city");
    builder.EntitySet<store>("store"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class cityController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/city
        [EnableQuery]
        public IQueryable<city> Getcity()
        {
            return db.city;
        }

        // GET: odata/city(5)
        [EnableQuery]
        public SingleResult<city> Getcity([FromODataUri] int key)
        {
            return SingleResult.Create(db.city.Where(city => city.IdCity == key));
        }

        // PUT: odata/city(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<city> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            city city = await db.city.FindAsync(key);
            if (city == null)
            {
                return NotFound();
            }

            patch.Put(city);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!cityExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(city);
        }

        // POST: odata/city
        public async Task<IHttpActionResult> Post(city city)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.city.Add(city);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (cityExists(city.IdCity))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return Created(city);
        }

        // PATCH: odata/city(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<city> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            city city = await db.city.FindAsync(key);
            if (city == null)
            {
                return NotFound();
            }

            patch.Patch(city);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!cityExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(city);
        }

        // DELETE: odata/city(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            city city = await db.city.FindAsync(key);
            if (city == null)
            {
                return NotFound();
            }

            db.city.Remove(city);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/city(5)/store
        [EnableQuery]
        public IQueryable<store> Getstore([FromODataUri] int key)
        {
            return db.city.Where(m => m.IdCity == key).SelectMany(m => m.store);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool cityExists(int key)
        {
            return db.city.Count(e => e.IdCity == key) > 0;
        }
    }
}
