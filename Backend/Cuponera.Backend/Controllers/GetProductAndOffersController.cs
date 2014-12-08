using System;
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
        public List<GetProductAndOffers> Get([FromUri] int idStore, [FromUri] int idCategory, [FromUri] double Latittud, [FromUri] double Longitude)
        {
            return db.GetProductAndOffers(idCategory, idCategory, Latittud, Longitude);
        }
    }
}
