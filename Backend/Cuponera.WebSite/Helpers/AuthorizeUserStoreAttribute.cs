using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;

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

            //get the storeID
            var idStore = 1;

            using (var usersContext = new CuponeraEntities())
            {
                return usersContext.store.Where(cs => cs.IdStore.Equals(idStore))
                            .Join(usersContext.company, cs => cs.IdCompany, c => c.IdCompany, (cs, c) => new { IdCompany = c.IdCompany })
                            .Join(usersContext.userCompany, c => c.IdCompany, uc => uc.IdCompany, (c, uc) => new { IdUser = uc.IdUser })
                            .Join(usersContext.UserProfile, uc => uc.IdUser, up => up.UserId, (uc, up) => new { UserName = up.UserName })
                            .Where(u => u.UserName.Equals(Thread.CurrentPrincipal.Identity.Name)).FirstOrDefault() != null;

            }
        }
    }
}