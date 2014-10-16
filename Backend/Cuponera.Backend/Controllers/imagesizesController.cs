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
    builder.EntitySet<imagesizes>("imagesizes");
    builder.EntitySet<deviceos>("deviceos"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class imagesizesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/imagesizes
        [EnableQuery]
        public IQueryable<imagesizes> Getimagesizes()
        {
            return db.imagesizes;
        }

        // GET: odata/imagesizes(5)
        [EnableQuery]
        public SingleResult<imagesizes> Getimagesizes([FromODataUri] int key)
        {
            return SingleResult.Create(db.imagesizes.Where(imagesizes => imagesizes.IdImageSize == key));
        }

        // PUT: odata/imagesizes(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<imagesizes> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            imagesizes imagesizes = await db.imagesizes.FindAsync(key);
            if (imagesizes == null)
            {
                return NotFound();
            }

            patch.Put(imagesizes);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!imagesizesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(imagesizes);
        }

        // POST: odata/imagesizes
        public async Task<IHttpActionResult> Post(imagesizes imagesizes)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.imagesizes.Add(imagesizes);
            await db.SaveChangesAsync();

            return Created(imagesizes);
        }

        // PATCH: odata/imagesizes(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<imagesizes> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            imagesizes imagesizes = await db.imagesizes.FindAsync(key);
            if (imagesizes == null)
            {
                return NotFound();
            }

            patch.Patch(imagesizes);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!imagesizesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(imagesizes);
        }

        // DELETE: odata/imagesizes(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            imagesizes imagesizes = await db.imagesizes.FindAsync(key);
            if (imagesizes == null)
            {
                return NotFound();
            }

            db.imagesizes.Remove(imagesizes);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/imagesizes(5)/deviceos
        [EnableQuery]
        public SingleResult<deviceos> Getdeviceos([FromODataUri] int key)
        {
            return SingleResult.Create(db.imagesizes.Where(m => m.IdImageSize == key).Select(m => m.deviceos));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool imagesizesExists(int key)
        {
            return db.imagesizes.Count(e => e.IdImageSize == key) > 0;
        }
    }
}
