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
    builder.EntitySet<preHomeImages>("preHomeImages");
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class preHomeImagesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/preHomeImages
        [EnableQuery]
        public IQueryable<preHomeImages> GetpreHomeImages()
        {
            return db.preHomeImages;
        }

        // GET: odata/preHomeImages(5)
        [EnableQuery]
        public SingleResult<preHomeImages> GetpreHomeImages([FromODataUri] int key)
        {
            return SingleResult.Create(db.preHomeImages.Where(preHomeImages => preHomeImages.IdPreHomeImage == key));
        }

        // PUT: odata/preHomeImages(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<preHomeImages> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            preHomeImages preHomeImages = await db.preHomeImages.FindAsync(key);
            if (preHomeImages == null)
            {
                return NotFound();
            }

            patch.Put(preHomeImages);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!preHomeImagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(preHomeImages);
        }

        // POST: odata/preHomeImages
        public async Task<IHttpActionResult> Post(preHomeImages preHomeImages)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.preHomeImages.Add(preHomeImages);
            await db.SaveChangesAsync();

            return Created(preHomeImages);
        }

        // PATCH: odata/preHomeImages(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<preHomeImages> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            preHomeImages preHomeImages = await db.preHomeImages.FindAsync(key);
            if (preHomeImages == null)
            {
                return NotFound();
            }

            patch.Patch(preHomeImages);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!preHomeImagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(preHomeImages);
        }

        // DELETE: odata/preHomeImages(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            preHomeImages preHomeImages = await db.preHomeImages.FindAsync(key);
            if (preHomeImages == null)
            {
                return NotFound();
            }

            db.preHomeImages.Remove(preHomeImages);
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

        private bool preHomeImagesExists(int key)
        {
            return db.preHomeImages.Count(e => e.IdPreHomeImage == key) > 0;
        }
    }
}
