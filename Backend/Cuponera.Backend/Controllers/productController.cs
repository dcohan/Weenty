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
    builder.EntitySet<product>("product");
    builder.EntitySet<category>("category"); 
    builder.EntitySet<company>("company"); 
    builder.EntitySet<images>("images"); 
    builder.EntitySet<offer>("offer"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class productController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/product
        [EnableQuery]
        public IQueryable<product> Getproduct(bool all = false, string title = null, int category = 0, int company = 0)
        {
            IQueryable<product> products = db.product;
            if (!all)
            {
                products = products.Where(p => !p.DeletionDatetime.HasValue);
            }

            if (title != null)
            {
                products = products.Where(p => p.Title.Contains(title));
            }

            if (category > 0)
            {
                products = products.Where(p => p.category.IdCategory == category);
            }

            if (company > 0)
            {
                products = products.Where(p => p.company.IdCompany == company);
            }
            return products.OrderBy(c => c.Title);

        }

        // GET: odata/product(5)
        [EnableQuery]
        public SingleResult<product> Getproduct([FromODataUri] int key)
        {
            return SingleResult.Create(db.product.Where(product => product.IdProduct == key));
        }

        // PUT: odata/product(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<product> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            product product = await db.product.FindAsync(key);
            if (product == null)
            {
                return NotFound();
            }

            patch.Put(product);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!productExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(product);
        }

        // POST: odata/product
        public async Task<IHttpActionResult> Post(product product)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.product.Add(product);
            await db.SaveChangesAsync();

            return Created(product);
        }

        // PATCH: odata/product(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<product> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            product product = await db.product.FindAsync(key);
            if (product == null)
            {
                return NotFound();
            }

            patch.Patch(product);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!productExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(product);
        }

        // DELETE: odata/product(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            product product = await db.product.FindAsync(key);
            if (product == null)
            {
                return NotFound();
            }

            product.DeletionDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: odata/product(5)/Activate
        [HttpPost]
        public async Task<IHttpActionResult> Activate([FromODataUri] int key)
        {
            product product = await db.product.FindAsync(key);
            if (product == null)
            {
                return NotFound();
            }

            product.DeletionDatetime = null;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }


        // GET: odata/product(5)/category
        [EnableQuery]
        public SingleResult<category> Getcategory([FromODataUri] int key)
        {
            return SingleResult.Create(db.product.Where(m => m.IdProduct == key).Select(m => m.category));
        }

        // GET: odata/product(5)/company
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.product.Where(m => m.IdProduct == key && !m.DeletionDatetime.HasValue).Select(m => m.company));
        }

        // GET: odata/product(5)/images
        [EnableQuery]
        public IQueryable<images> Getimages([FromODataUri] int key)
        {
            return db.product.Where(m => m.IdProduct == key && !m.DeletionDatetime.HasValue).SelectMany(m => m.images);
        }

        // GET: odata/product(5)/offer
        [EnableQuery]
        public IQueryable<offer> Getoffer([FromODataUri] int key)
        {
            return db.product.Where(m => m.IdProduct == key && !m.DeletionDatetime.HasValue).SelectMany(m => m.offer);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool productExists(int key)
        {
            return db.product.Count(e => e.IdProduct == key) > 0;
        }
    }
}
