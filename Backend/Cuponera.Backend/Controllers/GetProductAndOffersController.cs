﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Cuponera.Entities;

namespace Cuponera.Backend.Controllers
{
    public class GetProductAndOffersController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: GetProductAndOffers
        [HttpGet]
        public object GetProductAndOffers([FromUri] int idStore, [FromUri] int idCategory)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var response = new { value = db.GetProductAndOffers(idStore, idCategory) };

            return response;
        }


        // GET: GetNearestStores
        [HttpGet]
        public object GetNearestStores([FromUri] int idCategory, [FromUri] double Latittud, [FromUri] double Longitude)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var response = new { value = db.GetNearestStores(idCategory, Latittud, Longitude) };

            return response;
        }

    }
}
