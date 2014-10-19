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
    builder.EntitySet<role>("role");
    builder.EntitySet<permissions>("permissions"); 
    builder.EntitySet<user>("user"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class roleController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/role
        [EnableQuery]
        public IQueryable<role> Getrole()
        {
            return db.role;
        }

        // GET: odata/role(5)
        [EnableQuery]
        public SingleResult<role> Getrole([FromODataUri] int key)
        {
            return SingleResult.Create(db.role.Where(role => role.idRole == key));
        }

        // PUT: odata/role(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<role> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            role role = await db.role.FindAsync(key);
            if (role == null)
            {
                return NotFound();
            }

            patch.Put(role);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!roleExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(role);
        }

        // POST: odata/role
        public async Task<IHttpActionResult> Post(role role)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.role.Add(role);
            await db.SaveChangesAsync();

            return Created(role);
        }

        // PATCH: odata/role(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<role> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            role role = await db.role.FindAsync(key);
            if (role == null)
            {
                return NotFound();
            }

            patch.Patch(role);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!roleExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(role);
        }

        // DELETE: odata/role(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            role role = await db.role.FindAsync(key);
            if (role == null)
            {
                return NotFound();
            }

            db.role.Remove(role);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/role(5)/permissions
        [EnableQuery]
        public IQueryable<permissions> Getpermissions([FromODataUri] int key)
        {
            return db.role.Where(m => m.idRole == key).SelectMany(m => m.permissions);
        }

        // GET: odata/role(5)/user
        [EnableQuery]
        public IQueryable<user> Getuser([FromODataUri] int key)
        {
            return db.role.Where(m => m.idRole == key).SelectMany(m => m.user);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool roleExists(int key)
        {
            return db.role.Count(e => e.idRole == key) > 0;
        }
    }
}
