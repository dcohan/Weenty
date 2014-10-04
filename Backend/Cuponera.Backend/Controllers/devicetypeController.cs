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
    public class devicetypeController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET api/devicetype
        public IEnumerable<devicetypes> Getdevicetypes()
        {
            return db.devicetypes.AsEnumerable();
        }

        // GET api/devicetype/5
        public devicetypes Getdevicetypes(int id)
        {
            devicetypes devicetypes = db.devicetypes.Find(id);
            if (devicetypes == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return devicetypes;
        }

        // PUT api/devicetype/5
        public HttpResponseMessage Putdevicetypes(int id, devicetypes devicetypes)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != devicetypes.IdDeviceType)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(devicetypes).State = EntityState.Modified;

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

        // POST api/devicetype
        public HttpResponseMessage Postdevicetypes(devicetypes devicetypes)
        {
            if (ModelState.IsValid)
            {
                db.devicetypes.Add(devicetypes);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, devicetypes);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = devicetypes.IdDeviceType }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/devicetype/5
        public HttpResponseMessage Deletedevicetypes(int id)
        {
            devicetypes devicetypes = db.devicetypes.Find(id);
            if (devicetypes == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.devicetypes.Remove(devicetypes);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, devicetypes);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}