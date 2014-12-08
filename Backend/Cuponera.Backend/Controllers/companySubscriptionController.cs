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
    builder.EntitySet<companySubscription>("companySubscription");
    builder.EntitySet<company>("company"); 
    builder.EntitySet<subscription>("subscription"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class companySubscriptionController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/companySubscription
        [EnableQuery]
        public IQueryable<companySubscription> GetcompanySubscription()
        {
            return db.companySubscription.Where(c => !c.DeletionDatetime.HasValue);
        }

        // GET: odata/companySubscription(5)
        [EnableQuery]
        public SingleResult<companySubscription> GetcompanySubscription([FromODataUri] int key)
        {
            return SingleResult.Create(db.companySubscription.Where(companySubscription => companySubscription.IdCompanySubscription == key && !companySubscription.DeletionDatetime.HasValue));
        }

        // PUT: odata/companySubscription(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<companySubscription> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            companySubscription companySubscription = await db.companySubscription.FindAsync(key);
            if (companySubscription == null)
            {
                return NotFound();
            }

            patch.Put(companySubscription);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companySubscriptionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(companySubscription);
        }

        // POST: odata/companySubscription
        public async Task<IHttpActionResult> Post(companySubscription companySubscription)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.companySubscription.Add(companySubscription);
            await db.SaveChangesAsync();

            return Created(companySubscription);
        }

        // PATCH: odata/companySubscription(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<companySubscription> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            companySubscription companySubscription = await db.companySubscription.FindAsync(key);
            if (companySubscription == null)
            {
                return NotFound();
            }

            patch.Patch(companySubscription);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companySubscriptionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(companySubscription);
        }

        // DELETE: odata/companySubscription(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            companySubscription companySubscription = await db.companySubscription.FindAsync(key);
            if (companySubscription == null)
            {
                return NotFound();
            }

            companySubscription.ModificationDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/companySubscription(5)/company
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.companySubscription.Where(m => m.IdCompanySubscription == key && !m.DeletionDatetime.HasValue).Select(m => m.company));
        }

        // GET: odata/companySubscription(5)/subscription
        [EnableQuery]
        public SingleResult<subscription> Getsubscription([FromODataUri] int key)
        {
            return SingleResult.Create(db.companySubscription.Where(m => m.IdCompanySubscription == key && !m.DeletionDatetime.HasValue).Select(m => m.subscription));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool companySubscriptionExists(int key)
        {
            return db.companySubscription.Count(e => e.IdCompanySubscription == key) > 0;
        }
    }
}
