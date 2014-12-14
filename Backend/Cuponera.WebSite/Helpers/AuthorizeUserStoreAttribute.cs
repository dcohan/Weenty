using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;
using Cuponera.WebSite.Models;

namespace Cuponera.WebSite.Helpers
{
    public class AuthorizeUserStoreAttribute : AuthorizeAttribute
    {
        protected override bool AuthorizeCore(HttpContextBase httpContext)
        {
            var isAuthorized = base.AuthorizeCore(httpContext);
            if (!isAuthorized)
            {
                return false;
            }

            //Override Principal for IsInRole Validation 
            //TODO: figure out why, when a session already exist, this fails because simplemembership is not initialized
            //if (httpContext.User.IsInRole("admin")) return true;
            if (new CuponeraPrincipal(new CuponeraIdentity(httpContext.User.Identity)).IsInRole("admin")) return true;

            //Define Entity and Id of entity
            string entity = httpContext.Request.Url.Segments[1].Replace("/", string.Empty);
            int idEntity = Convert.ToInt32(httpContext.Request.Url.Segments[3].Replace("/",string.Empty));
            List<int> stores = new List<int>();

            using (CuponeraEntities db = new CuponeraEntities())
            {
                switch (entity)
                {
                    case "offer":
                        stores.Add(db.offer.Where(o => o.IdOffer.Equals(idEntity)).FirstOrDefault().product.store.IdStore);
                        break;
                    case "product":
                        stores.Add(db.product.Where(p => p.IdProduct.Equals(idEntity)).FirstOrDefault().store.IdStore);
                        break;
                    case "company":
                        stores.AddRange(db.company.Where(c => c.IdCompany.Equals(idEntity)).FirstOrDefault().store.Select(s => s.IdStore));
                        break;
                    case "store":
                        stores.Add(idEntity);
                        break;
                }
            }


            //Otherwise, I need to check if he can admin over the selected company/store
            return CuponeraPrincipal.CanAdminStores( stores );           
        }
    }
}