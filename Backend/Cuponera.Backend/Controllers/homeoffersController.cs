using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Cuponera.Backend.Data;

namespace Cuponera.Backend.Controllers
{
    public class homeoffersController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET api/homeoffers
        public IEnumerable<homeoffers> Gethomeoffers()
        {
            return db.homeoffers.AsEnumerable();
        }

        // GET api/homeoffers/5
        public homeoffers Gethomeoffers(int id)
        {
            homeoffers homeoffers = db.homeoffers.Find(id);
            if (homeoffers == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return homeoffers;
        }

        // PUT api/homeoffers/5
        public HttpResponseMessage Puthomeoffers(int id, homeoffers homeoffers)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != homeoffers.IdHomeOffer)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(homeoffers).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/homeoffers
        public HttpResponseMessage Posthomeoffers(homeoffers homeoffers)
        {
            if (ModelState.IsValid)
            {
                db.homeoffers.Add(homeoffers);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, homeoffers);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = homeoffers.IdHomeOffer }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/homeoffers/5
        public HttpResponseMessage Deletehomeoffers(int id)
        {
            homeoffers homeoffers = db.homeoffers.Find(id);
            if (homeoffers == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.homeoffers.Remove(homeoffers);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, homeoffers);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}