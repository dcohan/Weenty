using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Transactions;
using System.Web;
using WebMatrix.WebData;
using Cuponera.WebSite.Filters;
using Cuponera.WebSite.Models;
using System.Security.Principal;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Globalization;
using System.Web.Security;
using Cuponera.Entities;
using System.Threading;

namespace Cuponera.WebSite.Models
{
    
    public class CuponeraIdentity : IIdentity
    {
        
        public CuponeraIdentity(IIdentity baseIdentity)
        {
            IsAuthenticated = baseIdentity.IsAuthenticated;
            AuthenticationType = baseIdentity.AuthenticationType;
            Name = baseIdentity.Name;

            if (HttpContext.Current.Session != null)
            {
                using (CuponeraEntities db = new CuponeraEntities())
                {
                    var _user = db.UserProfile.FirstOrDefault(u => u.UserName.ToLower() == Name.ToLower());
                    if (_user == null)
                    {
                        LogoutMethod();
                        return;
                    }

                    HttpContext.Current.Session["userId"] = _user.UserId;
                    HttpContext.Current.Session["userActive"] = _user.Active;

                    if (_user.Active!=null && (bool)_user.Active)
                    {

                        var userCompany = db.userCompany.Where(uc => uc.IdUser.Equals(CuponeraIdentity.CurrentUserId) && uc.IsAdmin).FirstOrDefault();

                        //Is Admin
                        if (userCompany != null)
                        {
                            if (userCompany.IsAdmin)
                            {
                                HttpContext.Current.Session["AdminCompany"] = userCompany.IdCompany;
                            }
                            var stores = db.userCompany.Where(uc => uc.IdUser.Equals(CuponeraIdentity.CurrentUserId)).Select(s => s.IdStore).ToList();

                            HttpContext.Current.Session["AvailableStores"] = stores;
                        }
                        else
                        {
                            var stores = db.userCompany.Where(uc => uc.IdUser.Equals(CuponeraIdentity.CurrentUserId)).Select(s => s.IdStore).ToList();

                            HttpContext.Current.Session["AvailableStores"] = stores;
                        }
                    }
                }
            }
        }

        private void LogoutMethod()
        {
            InitializeSimpleMembershipAttribute i = new InitializeSimpleMembershipAttribute();
            i.OnActionExecuting(null);

            WebSecurity.Logout();
        }

        public string AuthenticationType
        {
            get;
            set;
        }

        public bool IsAuthenticated
        {
            get;
            set;
        }

        public string Name
        {
            get;
            set;
        }

        public static int CurrentUserId
        {
            get
            {
                return (int)HttpContext.Current.Session["userId"];
            }
        }

        public static bool CurrentUserIsActive
        {
            get
            {
                return HttpContext.Current.Session["userActive"] != null && (bool)HttpContext.Current.Session["userActive"];
            }
        }

        public static List<Nullable<Int32>> CurrentAvaiableStores
        {
            get
            {
                return (List<Nullable<Int32>>)HttpContext.Current.Session["AvailableStores"];
            }
        }

        public static int AdminCompany
        {
            get
            {
                return HttpContext.Current.Session["AdminCompany"] != null ? (int)HttpContext.Current.Session["AdminCompany"] : 0;
            }
        }

        public List<store> Stores { get; set; }
    }

    public class CuponeraPrincipal : IPrincipal
    {
        public CuponeraPrincipal(CuponeraIdentity identity)
        {
            Identity = identity;
        }

        public IIdentity Identity
        {
            get;
            set;
        }

        public bool IsInRole(string role)
        {
            using (CuponeraEntities db = new CuponeraEntities())
            {
                var _user = db.UserProfile.FirstOrDefault(u => u.UserName.ToLower() == this.Identity.Name);

                return _user.webpages_Roles.Where(r => r.RoleName.Equals(role)).Count() > 0;
            }   
        }

        public static bool CanAdminStore(int IdStore)
        {
            return CuponeraIdentity.CurrentAvaiableStores.Where(s => s == IdStore).Count() > 0;
        }

        public static bool CanAdminStores(List<int> IdStores)
        {
            return CuponeraIdentity.CurrentAvaiableStores.Join(IdStores, s1 => s1, s2 => s2, (s1, s2) => new { store = s1 }).Count() > 0;
        }
    }
    public class UsersContext : DbContext
    {
        public UsersContext()
            : base("DefaultConnection")
        {
        }

        public DbSet<UserProfile> UserProfiles { get; set; }
    }

    [Table("UserProfile")]
    public class UserProfile
    {
        [Key]
        [DatabaseGeneratedAttribute(DatabaseGeneratedOption.Identity)]
        public int UserId { get; set; }
        public string UserName { get; set; }
    }

    public class RegisterExternalLoginModel
    {
        [Required]
        [Display(Name = "Usuario")]
        public string UserName { get; set; }

        public string ExternalLoginData { get; set; }
    }

    public class ChangePasswordModel
    {
        [Required]
        [DataType(DataType.Password)]
        [StringLength(100, ErrorMessage = "La {0} debe ser al menos de {2} caracteres de largo.", MinimumLength = 6)]
        [Display(Name = "Password")]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Confirmar nueva password")]
        [Compare("Password", ErrorMessage = "La nueva password no coincide.")]
        public string ConfirmPassword { get; set; }

        public string Token { get; set; }
        public string UserName { get; set; }
    }

    public class LocalPasswordModel
    {
        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password Actual")]
        public string OldPassword { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "La {0} debe ser al menos {2} caracteres de largo.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Nueva Password")]
        public string NewPassword { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Confirmar nueva password")]
        [Compare("NewPassword", ErrorMessage = "La nueva password no coincide.")]
        public string ConfirmPassword { get; set; }
    }

    public class LoginModel
    {
        [Required]
        [Display(Name = "Usuario")]
        public string UserName { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }

        [Display(Name = "Recordarme?")]
        public bool RememberMe { get; set; }
    }

    public class ForgotPasswordModel
    {
        [Required]
        [Display(Name = "Email")]
        [RegularExpression(@"^([\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+\.)*[\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\.)+[a-zA-Z]{2,6})|(\d{1,3}\.){3}\d{1,3}(\:\d{1,5})?)$", ErrorMessage = "El formato de e-mail no es válido.")]
        public string Email { get; set; }
    }

    public class RegisterModel
    {
        [Required]
        [Display(Name = "Usuario")]
        public string UserName { get; set; }

        [Required]
        [Display(Name = "Email")]
        [RegularExpression(@"^([\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+\.)*[\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\.)+[a-zA-Z]{2,6})|(\d{1,3}\.){3}\d{1,3}(\:\d{1,5})?)$", ErrorMessage = "El formato de e-mail no es válido.")]
        public string Email { get; set; }

        public bool Active { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "La {0} debe ser al menos de {2} caracteres de largo.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Confirmar password")]
        [Compare("Password", ErrorMessage = "El password y su confirmación no coinciden.")]
        public string ConfirmPassword { get; set; }

        [Display(Name = "Empresa")]
        public string Company { get; set; }
    }

    public class ExternalLogin
    {
        public string Provider { get; set; }
        public string ProviderDisplayName { get; set; }
        public string ProviderUserId { get; set; }
    }
}
