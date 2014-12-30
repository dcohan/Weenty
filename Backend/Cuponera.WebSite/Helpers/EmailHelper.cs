using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Web;
using Cuponera.Entities;
using Simplify.Mail;

namespace Cuponera.WebSite.Helpers
{
    public static class EmailHelper
    {
        private static string _serverPath {get;set;}
        private static string _subject {get;set;}
        static EmailHelper()
        {
            _serverPath = ConfigurationManager.AppSettings["ServerPath"];
            _subject = "Weenty - Notificación";
        }

        public static void SendNewUserNotificationToAdministrators(string Email)
        {
            using (CuponeraEntities db = new CuponeraEntities())
            {
                foreach (var user in db.webpages_Roles.Where(r => r.RoleName.Equals("admin")).Select(r => r.UserProfile))
                {
                    foreach(var u in user)
                    {
                        MailSender.Default.Send(MailSender.Default.Settings.SmtpUserName, u.Email, _subject,  PrepareBody(u.UserName,"Hay un nuevo usuario esperando la asignación a una compañia: "+Email));
                    }
                }
            }
        }

        public static void SendNewUserActivation(string Email)
        {
            MailSender.Default.Send(MailSender.Default.Settings.SmtpUserName, Email, _subject, PrepareBody(Email, "Usted ha sido asignado a una compañia, por favor seleccione el link para activar su cuenta" ));
        }

        private static string PrepareBody(string username, string content)
        {
            var formattedContent = @"<br/>" + content + @"<br/>";
            string mail = PrepareHeader(username)+formattedContent+PrepareFooter();
            return mail;
        }

        private static string PrepareHeader(string userName) 
        {
            return "<html><h1>Estimado " + userName + ", usted ha recibido una notificación de Weenty.</h1>";
        }

        private static string PrepareFooter() 
        {
            var footer = @"<br/><br/><a href='" + _serverPath + "'>Ir al Sitio</a>";
            footer = footer + @"<br/><br/><h1>Muchas gracias por utilizar Weenty!</h1></html>";
	        return footer;
        }
    }
}