using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.OData.Builder;
using System.Web.Http.OData.Extensions;
using Cuponera.Backend.Data;
using Microsoft.Data.Edm;

namespace Cuponera.Backend
{
    public static class ODataConfig
    {
        public static IEdmModel GetModel()
        {
            ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
            builder.EntitySet<deviceos>("deviceos");
            builder.EntitySet<devicetypes>("devicetypes");
            builder.EntitySet<permissions>("permissions");
            builder.EntitySet<prehomeimages>("prehomeimages");
            builder.EntitySet<product>("products");
            builder.EntitySet<profile>("profiles");
            builder.EntitySet<role>("roles");
            builder.EntitySet<state>("states");
            builder.EntitySet<store>("stores");
            builder.EntitySet<subscription>("subscriptions");
            builder.EntitySet<userStore>("userStores");
            builder.EntitySet<userSubscription>("userSubscriptions");

            // New code:
            builder.Namespace = "usersService";
            builder.EntitySet<user>("users").EntityType.Collection
                .Action("Login")
                .Parameter<string>("UserName");

            return builder.GetEdmModel();
        }

        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapODataServiceRoute("odata", "data", GetModel());
            config.EnsureInitialized();
        }
    }
}
