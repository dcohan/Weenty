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
    public class profileController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET api/profile
        public IEnumerable<profiles> Getprofiles()
        {
            var profiles = db.profiles.Include(p => p.deviceos).Include(p => p.devicetypes).Include(p => p.states);
            return profiles.AsEnumerable();
        }

        // GET api/profile/5
        public profiles Getprofiles(int id)
        {
            profiles profiles = db.profiles.Find(id);
            if (profiles == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return profiles;
        }

        // PUT api/profile/5
        public HttpResponseMessage Putprofiles(int id, profiles profiles)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != profiles.IdProfile)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(profiles).State = EntityState.Modified;

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

        // POST api/profile
        public HttpResponseMessage Postprofiles(profiles profiles)
        {
            if (ModelState.IsValid)
            {
                db.profiles.Add(profiles);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, profiles);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = profiles.IdProfile }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/profile/5
        public HttpResponseMessage Deleteprofiles(int id)
        {
            profiles profiles = db.profiles.Find(id);
            if (profiles == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.profiles.Remove(profiles);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, profiles);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}