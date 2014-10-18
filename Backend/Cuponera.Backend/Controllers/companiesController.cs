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
using Cuponera.Backend.Data;

namespace Cuponera.Backend.Controllers
{
    /*
    The WebApiConfig class may require additional changes to add a route for this controller. Merge these statements into the Register method of the WebApiConfig class as applicable. Note that OData URLs are case sensitive.

    using System.Web.Http.OData.Builder;
    using System.Web.Http.OData.Extensions;
    using Cuponera.Backend.Data;
    ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
    builder.EntitySet<company>("companies");
    builder.EntitySet<companySubscription>("companySubscription"); 
    builder.EntitySet<product>("product"); 
    builder.EntitySet<userCompany>("userCompany"); 
    builder.EntitySet<companyStore>("companyStore"); 
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class companiesController : ODataController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: odata/companies
        [EnableQuery]
        public IQueryable<company> Getcompanies()
        {
            return db.company;
        }

        // GET: odata/companies(5)
        [EnableQuery]
        public SingleResult<company> Getcompany([FromODataUri] int key)
        {
            return SingleResult.Create(db.company.Where(company => company.IdCompany == key));
        }

        // PUT: odata/companies(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<company> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            company company = await db.company.FindAsync(key);
            if (company == null)
            {
                return NotFound();
            }

            patch.Put(company);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companyExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(company);
        }

        // POST: odata/companies
        public async Task<IHttpActionResult> Post(company company)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.company.Add(company);
            await db.SaveChangesAsync();

            return Created(company);
        }

        // PATCH: odata/companies(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<company> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            company company = await db.company.FindAsync(key);
            if (company == null)
            {
                return NotFound();
            }

            patch.Patch(company);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!companyExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(company);
        }

        // DELETE: odata/companies(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            company company = await db.company.FindAsync(key);
            if (company == null)
            {
                return NotFound();
            }

            db.company.Remove(company);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        // GET: odata/companies(5)/companySubscription
        [EnableQuery]
        public IQueryable<companySubscription> GetcompanySubscription([FromODataUri] int key)
        {
            return db.company.Where(m => m.IdCompany == key).SelectMany(m => m.companySubscription);
        }

        // GET: odata/companies(5)/product
        [EnableQuery]
        public IQueryable<product> Getproduct([FromODataUri] int key)
        {
            return db.company.Where(m => m.IdCompany == key).SelectMany(m => m.product);
        }

        // GET: odata/companies(5)/userCompany
        [EnableQuery]
        public IQueryable<userCompany> GetuserCompany([FromODataUri] int key)
        {
            return db.company.Where(m => m.IdCompany == key).SelectMany(m => m.userCompany);
        }

        // GET: odata/companies(5)/companyStore
        [EnableQuery]
        public IQueryable<companyStore> GetcompanyStore([FromODataUri] int key)
        {
            return db.company.Where(m => m.IdCompany == key).SelectMany(m => m.companyStore);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool companyExists(int key)
        {
            return db.company.Count(e => e.IdCompany == key) > 0;
        }
    }
}
