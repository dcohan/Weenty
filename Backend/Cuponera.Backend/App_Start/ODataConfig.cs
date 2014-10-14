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
            builder.EntitySet<enabledsections>("enabledsections");
            builder.EntitySet<imagesizes>("imagesizes");
            builder.EntitySet<profiles>("profiles");
            builder.EntitySet<devicetypes>("devicetypes");
            builder.EntitySet<states>("states");
            builder.EntitySet<stores>("stores");


            return builder.GetEdmModel();
        }

        public static void Register(HttpConfiguration config)
        {
            config.Routes.MapODataServiceRoute("odata", "api", GetModel());
            config.EnsureInitialized();
        }
    }
}
