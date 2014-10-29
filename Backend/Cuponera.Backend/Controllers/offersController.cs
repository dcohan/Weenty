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
    builder.EntitySet<offer>("offers");
    builder.EntitySet<product>("product"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class offersController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/offers
        [EnableQuery]
        public IQueryable<offer> Getoffers()
        {
            return db.offer;
        }

        // GET: odata/offers(5)
        [EnableQuery]
        public SingleResult<offer> Getoffer([FromODataUri] int key)
        {
            return SingleResult.Create(db.offer.Where(offer => offer.IdOffer == key));
        }

        // PUT: odata/offers(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<offer> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            offer offer = await db.offer.FindAsync(key);
            if (offer == null)
            {
                return NotFound();
            }

            patch.Put(offer);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!offerExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(offer);
        }

        // POST: odata/offers
        public async Task<IHttpActionResult> Post(offer offer)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.offer.Add(offer);
            await db.SaveChangesAsync();

            return Created(offer);
        }

        // PATCH: odata/offers(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<offer> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            offer offer = await db.offer.FindAsync(key);
            if (offer == null)
            {
                return NotFound();
            }

            patch.Patch(offer);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!offerExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(offer);
        }

        // DELETE: odata/offers(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            offer offer = await db.offer.FindAsync(key);
            if (offer == null)
            {
                return NotFound();
            }

            db.offer.Remove(offer);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/offers(5)/product
        [EnableQuery]
        public SingleResult<product> Getproduct([FromODataUri] int key)
        {
            return SingleResult.Create(db.offer.Where(m => m.IdOffer == key).Select(m => m.product));
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool offerExists(int key)
        {
            return db.offer.Count(e => e.IdOffer == key) > 0;
        }
    }
}