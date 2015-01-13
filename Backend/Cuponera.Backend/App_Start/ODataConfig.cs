using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.OData.Builder;
using System.Web.Http.OData.Extensions;
using Cuponera.Entities;
using Microsoft.Data.Edm;

namespace Cuponera.Backend
{
    public static class ODataConfig
    {
        public static IEdmModel GetModel()
        {
            ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
            builder.EntitySet<category>("category");
            builder.EntitySet<company>("company");
            builder.EntitySet<companySubscription>("companySubscription");
            builder.EntitySet<images>("images");
            builder.EntitySet<offer>("offer");
            builder.EntitySet<preHomeImages>("prehomeimages");
            builder.EntitySet<product>("product");
            builder.EntitySet<occasion>("occasion");
            builder.EntitySet<profile>("profile");
            builder.EntitySet<state>("state");
            builder.EntitySet<store>("store");
            builder.EntitySet<storeCategory>("storeCategory");
            builder.EntitySet<subcategory>("subcategory");
            builder.EntitySet<subscription>("subscription");
            builder.EntitySet<geoRequest>("georequest");
            builder.EntitySet<UserProfile>("userprofile");
            builder.EntitySet<userCompany>("userCompany");
            builder.EntitySet<webpages_Roles>("webpages_Roles");
            builder.EntitySet<webpages_OAuthMembership>("webpages_OAuthMembership");
            builder.EntitySet<webpages_Membership>("webpages_Membership");
            builder.EntitySet<GetNearestStores>("GetNearestStores");

            /* Custom rules */
            builder.Entity<category>().Action("Activate");
            builder.Entity<company>().Action("Activate");
            return builder.GetEdmModel();
        }

        public static void Register(HttpConfiguration config)
        {


            config.Routes.MapODataServiceRoute("data", "", GetModel());
            config.EnsureInitialized();
        }
    }
}
