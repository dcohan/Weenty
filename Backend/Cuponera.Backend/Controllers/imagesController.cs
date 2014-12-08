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
    builder.EntitySet<images>("images");
    builder.EntitySet<offer>("offer"); 
    builder.EntitySet<product>("product"); 
    builder.EntitySet<store>("store"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class imagesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/images
        [EnableQuery]
        public IQueryable<images> Getimages()
        {
            return db.images;
        }

        // GET: odata/images(5)
        [EnableQuery]
        public SingleResult<images> Getimages([FromODataUri] int key)
        {
            return SingleResult.Create(db.images.Where(images => images.IdImage == key));
        }

        // PUT: odata/images(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<images> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            images images = await db.images.FindAsync(key);
            if (images == null)
            {
                return NotFound();
            }

            patch.Put(images);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!imagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(images);
        }

        // POST: odata/images
        public async Task<IHttpActionResult> Post(images images)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.images.Add(images);
            await db.SaveChangesAsync();

            return Created(images);
        }

        // PATCH: odata/images(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<images> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            images images = await db.images.FindAsync(key);
            if (images == null)
            {
                return NotFound();
            }

            patch.Patch(images);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!imagesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(images);
        }

        // DELETE: odata/images(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            images images = await db.images.FindAsync(key);
            if (images == null)
            {
                return NotFound();
            }

            db.images.Remove(images);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/images(5)/offer
        [EnableQuery]
        public SingleResult<offer> Getoffer([FromODataUri] int key)
        {
            return SingleResult.Create(db.images.Where(m => m.IdImage == key).Select(m => m.offer));
        }

        // GET: odata/images(5)/product
        [EnableQuery]
        public SingleResult<product> Getproduct([FromODataUri] int key)
        {
            return SingleResult.Create(db.images.Where(m => m.IdImage == key).Select(m => m.product));
        }

        // GET: odata/images(5)/store
        [EnableQuery]
        public SingleResult<store> Getstore([FromODataUri] int key)
        {
            return SingleResult.Create(db.images.Where(m => m.IdImage == key).Select(m => m.store));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool imagesExists(int key)
        {
            return db.images.Count(e => e.IdImage == key) > 0;
        }
    }
}
