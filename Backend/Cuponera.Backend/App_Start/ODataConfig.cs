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
            builder.EntitySet<companyStore>("companyStore");
            builder.EntitySet<companySubscription>("companySubscription");
            builder.EntitySet<deviceos>("deviceos");
            builder.EntitySet<devicetypes>("devicetypes");
            builder.EntitySet<images>("images");
            builder.EntitySet<offer>("offer");
            builder.EntitySet<permissions>("permissions");
            builder.EntitySet<prehomeimages>("prehomeimages");
            builder.EntitySet<product>("product");
            builder.EntitySet<profile>("profile");
            builder.EntitySet<role>("role");
            builder.EntitySet<state>("state");
            builder.EntitySet<store>("store");
            builder.EntitySet<subscription>("subscription");
            builder.EntitySet<user>("user");
            builder.EntitySet<userCompany>("userCompany");

            return builder.GetEdmModel();
        }

        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapODataServiceRoute("data", "", GetModel());
            config.EnsureInitialized();
        }
    }
}
