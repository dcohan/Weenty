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
    builder.EntitySet<webpages_Roles>("webpages_Roles");
    builder.EntitySet<permissions>("permissions"); 
    builder.EntitySet<UserProfile>("UserProfile"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class webpages_RolesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/webpages_Roles
        [EnableQuery]
        public IQueryable<webpages_Roles> Getwebpages_Roles()
        {
            return db.webpages_Roles;
        }

        // GET: odata/webpages_Roles(5)
        [EnableQuery]
        public SingleResult<webpages_Roles> Getwebpages_Roles([FromODataUri] int key)
        {
            return SingleResult.Create(db.webpages_Roles.Where(webpages_Roles => webpages_Roles.RoleId == key));
        }

        // PUT: odata/webpages_Roles(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<webpages_Roles> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            webpages_Roles webpages_Roles = await db.webpages_Roles.FindAsync(key);
            if (webpages_Roles == null)
            {
                return NotFound();
            }

            patch.Put(webpages_Roles);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!webpages_RolesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(webpages_Roles);
        }

        // POST: odata/webpages_Roles
        public async Task<IHttpActionResult> Post(webpages_Roles webpages_Roles)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.webpages_Roles.Add(webpages_Roles);
            await db.SaveChangesAsync();

            return Created(webpages_Roles);
        }

        // PATCH: odata/webpages_Roles(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<webpages_Roles> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            webpages_Roles webpages_Roles = await db.webpages_Roles.FindAsync(key);
            if (webpages_Roles == null)
            {
                return NotFound();
            }

            patch.Patch(webpages_Roles);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!webpages_RolesExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(webpages_Roles);
        }

        // DELETE: odata/webpages_Roles(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            webpages_Roles webpages_Roles = await db.webpages_Roles.FindAsync(key);
            if (webpages_Roles == null)
            {
                return NotFound();
            }

            db.webpages_Roles.Remove(webpages_Roles);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/webpages_Roles(5)/permissions
        [EnableQuery]
        public IQueryable<permissions> Getpermissions([FromODataUri] int key)
        {
            return db.webpages_Roles.Where(m => m.RoleId == key).SelectMany(m => m.permissions);
        }

        // GET: odata/webpages_Roles(5)/UserProfile
        [EnableQuery]
        public IQueryable<UserProfile> GetUserProfile([FromODataUri] int key)
        {
            return db.webpages_Roles.Where(m => m.RoleId == key).SelectMany(m => m.UserProfile);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool webpages_RolesExists(int key)
        {
            return db.webpages_Roles.Count(e => e.RoleId == key) > 0;
        }
    }
}
