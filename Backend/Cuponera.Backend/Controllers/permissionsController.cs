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
    builder.EntitySet<permissions>("permissions");
    builder.EntitySet<role>("role"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class permissionsController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/permissions
        [EnableQuery]
        public IQueryable<permissions> Getpermissions()
        {
            return db.permissions;
        }

        // GET: odata/permissions(5)
        [EnableQuery]
        public SingleResult<permissions> Getpermissions([FromODataUri] int key)
        {
            return SingleResult.Create(db.permissions.Where(permissions => permissions.idPermission == key));
        }

        // PUT: odata/permissions(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<permissions> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            permissions permissions = await db.permissions.FindAsync(key);
            if (permissions == null)
            {
                return NotFound();
            }

            patch.Put(permissions);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!permissionsExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(permissions);
        }

        // POST: odata/permissions
        public async Task<IHttpActionResult> Post(permissions permissions)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.permissions.Add(permissions);
            await db.SaveChangesAsync();

            return Created(permissions);
        }

        // PATCH: odata/permissions(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<permissions> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            permissions permissions = await db.permissions.FindAsync(key);
            if (permissions == null)
            {
                return NotFound();
            }

            patch.Patch(permissions);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!permissionsExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(permissions);
        }

        // DELETE: odata/permissions(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            permissions permissions = await db.permissions.FindAsync(key);
            if (permissions == null)
            {
                return NotFound();
            }

            db.permissions.Remove(permissions);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/permissions(5)/role
        [EnableQuery]
        public SingleResult<role> Getrole([FromODataUri] int key)
        {
            return SingleResult.Create(db.permissions.Where(m => m.idPermission == key).Select(m => m.role));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool permissionsExists(int key)
        {
            return db.permissions.Count(e => e.idPermission == key) > 0;
        }
    }
}
