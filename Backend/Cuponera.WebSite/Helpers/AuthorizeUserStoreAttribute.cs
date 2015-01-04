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
        public bool MustBeAdmin { get; set; }
        public bool MustBeCompanyAdmin { get; set; }

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
            if (new CuponeraPrincipal(new CuponeraIdentity(httpContext.User.Identity)).IsInRole("admin"))
            {
                return true;
            }
            else
            {
                if (MustBeAdmin) return false;
            }

            //Define Entity and Id of entity
            string entity = httpContext.Request.Url.Segments[1].Replace("/", string.Empty);

            //Only applies to operation on specific resources, not for Index
            if (httpContext.Request.Url.Segments.Count() > 3)
            {
                int idEntity = Convert.ToInt32(httpContext.Request.Url.Segments[3].Replace("/", string.Empty));
                List<int> stores = new List<int>();

                using (CuponeraEntities db = new CuponeraEntities())
                {
                    var userId = db.UserProfile.Where(u => u.UserName.Equals(httpContext.User.Identity.Name)).Select(u => u.UserId).FirstOrDefault();
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

                            if (MustBeCompanyAdmin && db.userCompany.Where(uc => uc.IdUser.Equals(userId) && uc.IsAdmin).Count() <= 0)
                            {
                                return false;
                            }

                            break;
                        case "store":
                            stores.Add(idEntity);
                            break;

                        case "userCompany":
                            if (MustBeCompanyAdmin && db.userCompany.Where(uc => uc.IdUser.Equals(userId) && uc.IsAdmin).Count() <= 0)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                            break;
                    }
                }

                //Otherwise, I need to check if he can admin over the selected company/store
                return CuponeraPrincipal.CanAdminStores(stores);
            }
            else
            {
                using (CuponeraEntities db = new CuponeraEntities())
                {
                    var userId = db.UserProfile.Where(u => u.UserName.Equals(httpContext.User.Identity.Name)).Select(u => u.UserId).FirstOrDefault();
                    switch (entity)
                    {
                        case "store":
                        case "company":
                            if (MustBeCompanyAdmin)
                            {
                                if (CuponeraIdentity.AdminCompany > 0)
                                {
                                    return true;
                                }
                                else
                                    return false;
                            }

                            break;

                    }
                }

                return true;
            }
        }
    }
}