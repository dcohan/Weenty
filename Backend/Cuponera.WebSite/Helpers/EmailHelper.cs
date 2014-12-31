using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Web;
using Cuponera.Entities;
using Cuponera.WebSite.Models;
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

        public static void SendNewUserNotificationToAdministrators(RegisterModel model)
        {
            Dictionary<string, string> emailList = new Dictionary<string, string>();
            using (CuponeraEntities db = new CuponeraEntities())
            {
                bool emailSentToCompanyAdmins = false;
                var companies = db.company.Where(c => c.Name.ToLower().Contains(model.Company.ToLower()));
                if (companies.Count() > 0)
                {
                    foreach (var company in companies)
                    {
                        foreach(var usercompany in company.userCompany)
                        {
                            if (usercompany.IsAdmin)
                            {
                                emailSentToCompanyAdmins = true;
                                if (!emailList.ContainsKey(usercompany.UserProfile.UserName))
                                {
                                    emailList.Add(usercompany.UserProfile.UserName, usercompany.UserProfile.Email);
                                }
                            }
                        }
                    }
                }
                
                if (!emailSentToCompanyAdmins)//Send the email to BO admins
                {
                    foreach (var user in db.webpages_Roles.Where(r => r.RoleName.Equals("admin")).Select(r => r.UserProfile))
                    {
                        foreach (var u in user)
                        {
                            if (!emailList.ContainsKey(u.UserName))
                            {
                                emailList.Add(u.UserName, u.Email);
                            }
                        }
                    }
                }

                foreach (var email in emailList)
                {
                    MailSender.Default.Send(MailSender.Default.Settings.SmtpUserName, email.Value, _subject, PrepareBody(email.Key, "Hay un nuevo usuario esperando la asignación a una compañia: " + email.Value));
                }
            }
        }

        public static void SendPasswordRecovery(string Email, string token)
        {
            MailSender.Default.Send(MailSender.Default.Settings.SmtpUserName, Email, _subject, PrepareBody(Email, "Usted ha solicitado un cambio de contraseña, por favor seleccione abra el link para cambiar su contraseña<br /><br /><a href='" + _serverPath + "/Account/ChangePassword?Id=" + token + "'>" + _serverPath + "/Account/ChangePassword?Id=" + token + "</a> "));
        }


        public static void SendNewUserActivation(string Email, string token)
        {
            MailSender.Default.Send(MailSender.Default.Settings.SmtpUserName, Email, _subject, PrepareBody(Email, "Usted ha sido asignado a una compañia, por favor seleccione abra el link para activar su cuenta<br /><br /><a href='" + _serverPath + "/Account/RegisterConfirmation?Id=" + token + "'>" + _serverPath + "/Account/RegisterConfirmation?Id=" + token + "</a> "));
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