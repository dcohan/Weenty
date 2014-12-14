using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Web.Http;

namespace Cuponera.Backend
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            config.Formatters.JsonFormatter.SupportedMediaTypes.Add(new MediaTypeHeaderValue("text/html"));

            config.Routes.MapHttpRoute(
              name: "GetProductAndOffers",
              routeTemplate: "GetProductAndOffers",
              defaults: new { controller = "GetProductAndOffers", action = "GetProductAndOffers", }
              );

            config.Routes.MapHttpRoute(
              name: "GetNearestStores",
              routeTemplate: "GetNearestStores",
              defaults: new { controller = "GetProductAndOffers", action = "GetNearestStores", }
              );

            config.Routes.MapHttpRoute(
              name: "GetNearestStoresByName",
              routeTemplate: "GetNearestStoresByName",
              defaults: new { controller = "GetProductAndOffers", action = "GetNearestStoresByName", }
              );

            // Uncomment the following line of code to enable query support for actions with an IQueryable or IQueryable<T> return type.
            // To avoid processing unexpected or malicious queries, use the validation settings on QueryableAttribute to validate incoming queries.
            // For more information, visit http://go.microsoft.com/fwlink/?LinkId=279712.
            //config.EnableQuerySupport();
        }
    }
}