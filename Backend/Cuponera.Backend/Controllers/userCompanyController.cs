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
    builder.EntitySet<userCompany>("userCompany");
    builder.EntitySet<company>("company"); 
    builder.EntitySet<user>("user"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class userCompanyController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/userCompany
        [EnableQuery]
        public IQueryable<userCompany> GetuserCompany()
        {
            return db.userCompany;
        }

        // GET: odata/userCompany(5)
        [EnableQuery]
        public SingleResult<userCompany> GetuserCompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.userCompany.Where(userCompany => userCompany.IdUserCompany == key));
        }

        // PUT: odata/userCompany(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<userCompany> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            userCompany userCompany = await db.userCompany.FindAsync(key);
            if (userCompany == null)
            {
                return NotFound();
            }

            patch.Put(userCompany);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!userCompanyExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(userCompany);
        }

        // POST: odata/userCompany
        public async Task<IHttpActionResult> Post(userCompany userCompany)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.userCompany.Add(userCompany);
            await db.SaveChangesAsync();

            return Created(userCompany);
        }

        // PATCH: odata/userCompany(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<userCompany> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            userCompany userCompany = await db.userCompany.FindAsync(key);
            if (userCompany == null)
            {
                return NotFound();
            }

            patch.Patch(userCompany);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!userCompanyExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(userCompany);
        }

        // DELETE: odata/userCompany(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            userCompany userCompany = await db.userCompany.FindAsync(key);
            if (userCompany == null)
            {
                return NotFound();
            }

            db.userCompany.Remove(userCompany);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/userCompany(5)/company
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.userCompany.Where(m => m.IdUserCompany == key).Select(m => m.company));
        }

        // GET: odata/userCompany(5)/user
        [EnableQuery]
        public SingleResult<user> Getuser([FromODataUri] int key)
        {
            return SingleResult.Create(db.userCompany.Where(m => m.IdUserCompany == key).Select(m => m.user));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool userCompanyExists(int key)
        {
            return db.userCompany.Count(e => e.IdUserCompany == key) > 0;
        }
    }
}
