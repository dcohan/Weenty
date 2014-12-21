using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;
using System.Configuration;
using Cuponera.WebSite.Helpers;

namespace Cuponera.WebSite.Controllers
{
    [AuthorizeUserStoreAttribute(MustBeAdmin = true)]
    public class geoRequestController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        [HttpGet]
        public object checkIfUserCanAskForCoordinates()
        {
            return Helpers.JSONHelper.SerializeJSON(new { result = true });
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
