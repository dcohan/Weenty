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
    builder.EntitySet<state>("state");
    builder.EntitySet<store>("store"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class stateController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/state
        [EnableQuery]
        public IQueryable<state> Getstate()
        {
            return db.state.Where(s => s.DeletionDatetime == null).OrderBy(s => s.Name);
        }

        // GET: odata/state(5)
        [EnableQuery]
        public SingleResult<state> Getstate([FromODataUri] int key)
        {

            return SingleResult.Create(db.state.Where(state => state.IdState == key && state.DeletionDatetime == null));
        }

        [Authorize]
        // PUT: odata/state(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<state> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            state state = await db.state.FindAsync(key);
            if (state == null)
            {
                return NotFound();
            }

            patch.Put(state);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!stateExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(state);
        }

        [Authorize]
        // POST: odata/state
        public async Task<IHttpActionResult> Post(state state)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.state.Add(state);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (stateExists(state.IdState))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return Created(state);
        }

        [Authorize]
        // PATCH: odata/state(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<state> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            state state = await db.state.FindAsync(key);
            if (state == null)
            {
                return NotFound();
            }

            patch.Patch(state);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!stateExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(state);
        }

        [Authorize]
        // DELETE: odata/state(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            state state = await db.state.FindAsync(key);
            if (state == null)
            {
                return NotFound();
            }

            db.state.Remove(state);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/state(5)/store
        [EnableQuery]
        public IQueryable<store> Getstore([FromODataUri] int key)
        {
            return db.state.Where(m => m.IdState == key).SelectMany(m => m.store);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool stateExists(int key)
        {
            return db.state.Count(e => e.IdState == key) > 0;
        }
    }
}
