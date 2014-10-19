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
    builder.EntitySet<user>("user");
    builder.EntitySet<role>("role"); 
    builder.EntitySet<userCompany>("userCompany"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class userController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/user
        [EnableQuery]
        public IQueryable<user> Getuser()
        {
            return db.user;
        }

        // GET: odata/user(5)
        [EnableQuery]
        public SingleResult<user> Getuser([FromODataUri] int key)
        {
            return SingleResult.Create(db.user.Where(user => user.IdUser == key));
        }

        // PUT: odata/user(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<user> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            user user = await db.user.FindAsync(key);
            if (user == null)
            {
                return NotFound();
            }

            patch.Put(user);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!userExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(user);
        }

        // POST: odata/user
        public async Task<IHttpActionResult> Post(user user)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.user.Add(user);
            await db.SaveChangesAsync();

            return Created(user);
        }

        // PATCH: odata/user(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<user> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            user user = await db.user.FindAsync(key);
            if (user == null)
            {
                return NotFound();
            }

            patch.Patch(user);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!userExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(user);
        }

        // DELETE: odata/user(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            user user = await db.user.FindAsync(key);
            if (user == null)
            {
                return NotFound();
            }

            db.user.Remove(user);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/user(5)/role
        [EnableQuery]
        public SingleResult<role> Getrole([FromODataUri] int key)
        {
            return SingleResult.Create(db.user.Where(m => m.IdUser == key).Select(m => m.role));
        }

        // GET: odata/user(5)/userCompany
        [EnableQuery]
        public IQueryable<userCompany> GetuserCompany([FromODataUri] int key)
        {
            return db.user.Where(m => m.IdUser == key).SelectMany(m => m.userCompany);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool userExists(int key)
        {
            return db.user.Count(e => e.IdUser == key) > 0;
        }
    }
}
