using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Threading;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using Cuponera.Entities;
using Cuponera.WebSite.Models;

namespace Cuponera.WebSite
{
    // Note: For instructions on enabling IIS6 or IIS7 classic mode, 
    // visit http://go.microsoft.com/?LinkId=9394801

    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();

            WebApiConfig.Register(GlobalConfiguration.Configuration);
            //FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
            AuthConfig.RegisterAuth();
            
        }

        protected void Session_Start(Object sender, EventArgs e)
        {
            if (User != null && User.Identity != null && User.Identity.IsAuthenticated && Thread.CurrentPrincipal.GetType() != typeof(CuponeraPrincipal))
            {
                IPrincipal principal = new CuponeraPrincipal(new CuponeraIdentity(User.Identity));
                Thread.CurrentPrincipal = principal;
                HttpContext.Current.User = principal;
            }
        }

        protected void Application_Error(Object sender, EventArgs e)
        {
            var exception = Server.GetLastError();
            if (exception is HttpUnhandledException)
            {
                //Server.Transfer("~/Error.html");
            }
            if (exception is HttpException && exception.Message.Contains("was not found on controller"))
            {
                //Server.Transfer("~/Error/NotFound");
            }

            if (exception != null)
            {
                
                //Server.Transfer("~/Error");
            }
            else
            {
                //Server.Transfer("~/Error/NotFound");
            }
        }   
    }
}