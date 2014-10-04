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
    public class campaignController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET api/campaign
        public IEnumerable<campaign> Getcampaigns()
        {
            return db.campaign.AsEnumerable();
        }

        // GET api/campaign/5
        public campaign Getcampaign(int id)
        {
            campaign campaign = db.campaign.Find(id);
            if (campaign == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return campaign;
        }

        // PUT api/campaign/5
        public HttpResponseMessage Putcampaign(int id, campaign campaign)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != campaign.CampaignId)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(campaign).State = EntityState.Modified;

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

        // POST api/campaign
        public HttpResponseMessage Postcampaign(campaign campaign)
        {
            if (ModelState.IsValid)
            {
                db.campaign.Add(campaign);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, campaign);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = campaign.CampaignId }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/campaign/5
        public HttpResponseMessage Deletecampaign(int id)
        {
            campaign campaign = db.campaign.Find(id);
            if (campaign == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.campaign.Remove(campaign);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, campaign);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}