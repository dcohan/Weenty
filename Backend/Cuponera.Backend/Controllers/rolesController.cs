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
    public class rolesController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET api/roles
        public IEnumerable<roles> Getroles()
        {
            return db.roles.AsEnumerable();
        }

        // GET api/roles/5
        public roles Getroles(int id)
        {
            roles roles = db.roles.Find(id);
            if (roles == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return roles;
        }

        // PUT api/roles/5
        public HttpResponseMessage Putroles(int id, roles roles)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != roles.idRole)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(roles).State = EntityState.Modified;

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

        // POST api/roles
        public HttpResponseMessage Postroles(roles roles)
        {
            if (ModelState.IsValid)
            {
                db.roles.Add(roles);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, roles);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = roles.idRole }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/roles/5
        public HttpResponseMessage Deleteroles(int id)
        {
            roles roles = db.roles.Find(id);
            if (roles == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.roles.Remove(roles);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, roles);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}