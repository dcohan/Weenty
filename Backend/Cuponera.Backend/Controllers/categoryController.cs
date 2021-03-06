﻿using System;
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
    builder.EntitySet<category>("category");
    builder.EntitySet<product>("product"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class categoryController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/category
        [EnableQuery]
        public IQueryable<category> Getcategory(bool all = false, string name = null)
        {
            IQueryable<category> categories;
            if (all)
            {
                categories = db.category;
            }
            else
            {
                categories = db.category.Where(c => !c.DeletionDatetime.HasValue);
            }


            if (name != null)
            {
                categories = categories.Where(c => c.Name.Contains(name));
            }
            return categories.OrderBy(c => c.Name);
        }

        // GET: odata/category(5)
        [EnableQuery]
        public SingleResult<category> Getcategory([FromODataUri] int key)
        {
            return SingleResult.Create(db.category.Where(category => category.IdCategory == key && !category.DeletionDatetime.HasValue));
        }

        [Authorize]
        // PUT: odata/category(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<category> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            category category = await db.category.FindAsync(key);
            if (category == null)
            {
                return NotFound();
            }

            patch.Put(category);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!categoryExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(category);
        }

        [Authorize]
        // POST: odata/category
        public async Task<IHttpActionResult> Post(category category)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.category.Add(category);
            await db.SaveChangesAsync();

            return Created(category);
        }

        [Authorize]
        // PATCH: odata/category(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<category> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            category category = await db.category.FindAsync(key);
            if (category == null)
            {
                return NotFound();
            }

            patch.Patch(category);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!categoryExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(category);
        }

        [Authorize]
        // DELETE: odata/category(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            category category = await db.category.FindAsync(key);
            if (category == null)
            {
                return NotFound();
            }

            category.DeletionDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        [Authorize]
        [HttpPost]
        public async Task<IHttpActionResult> Activate([FromODataUri] int key)
        {
            category category = await db.category.FindAsync(key);
            if (category == null)
            {
                return NotFound();
            }

            category.DeletionDatetime = null;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        [Authorize]
        // GET: odata/category(5)/product
        [EnableQuery]
        public IQueryable<product> Getproduct([FromODataUri] int key)
        {
            return db.category.Where(m => m.IdCategory == key && !m.DeletionDatetime.HasValue).SelectMany(m => m.product);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool categoryExists(int key)
        {
            return db.category.Count(e => e.IdCategory == key) > 0;
        }
    }
}
