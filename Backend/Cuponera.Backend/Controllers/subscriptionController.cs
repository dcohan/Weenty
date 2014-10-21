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
    builder.EntitySet<subscription>("subscription");
    builder.EntitySet<companySubscription>("companySubscription"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class subscriptionController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/subscription
        [EnableQuery]
        public IQueryable<subscription> Getsubscription()
        {
            return db.subscription.Where(s => !s.DeletionDatetime.HasValue);
        }

        // GET: odata/subscription(5)
        [EnableQuery]
        public SingleResult<subscription> Getsubscription([FromODataUri] int key)
        {
            return SingleResult.Create(db.subscription.Where(subscription => subscription.Idsubscription == key && !subscription.DeletionDatetime.HasValue));
        }

        // PUT: odata/subscription(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<subscription> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            subscription subscription = await db.subscription.FindAsync(key);
            if (subscription == null)
            {
                return NotFound();
            }

            patch.Put(subscription);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!subscriptionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(subscription);
        }

        // POST: odata/subscription
        public async Task<IHttpActionResult> Post(subscription subscription)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.subscription.Add(subscription);
            await db.SaveChangesAsync();

            return Created(subscription);
        }

        // PATCH: odata/subscription(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<subscription> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            subscription subscription = await db.subscription.FindAsync(key);
            if (subscription == null)
            {
                return NotFound();
            }

            patch.Patch(subscription);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!subscriptionExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(subscription);
        }

        // DELETE: odata/subscription(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            subscription subscription = await db.subscription.FindAsync(key);
            if (subscription == null)
            {
                return NotFound();
            }

            subscription.ModificationDatetime = DateTime.UtcNow;
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/subscription(5)/companySubscription
        [EnableQuery]
        public IQueryable<companySubscription> GetcompanySubscription([FromODataUri] int key)
        {
            return db.subscription.Where(m => m.Idsubscription == key && !m.DeletionDatetime.HasValue).SelectMany(m => m.companySubscription);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool subscriptionExists(int key)
        {
            return db.subscription.Count(e => e.Idsubscription == key) > 0;
        }
    }
}
