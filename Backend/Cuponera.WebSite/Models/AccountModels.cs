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

namespace Cuponera.WebSite.Models
{
    public class CuponeraIdentity : IIdentity
    {
        public CuponeraIdentity(IIdentity baseIdentity)
        {
            IsAuthenticated = baseIdentity.IsAuthenticated;
            AuthenticationType = baseIdentity.AuthenticationType;
            Name = baseIdentity.Name;

            using (CuponeraEntities db = new CuponeraEntities())
            {
                Cuponera.Entities.UserProfile user = db.UserProfile.FirstOrDefault(u => u.UserName.ToLower() == Name.ToLower());
                
            }
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
            throw new NotImplementedException();
        }

        public bool CanAdminStore(int storeId)
        {
            return true;
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
        [Display(Name = "User name")]
        public string UserName { get; set; }

        public string ExternalLoginData { get; set; }
    }

    public class LocalPasswordModel
    {
        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Current password")]
        public string OldPassword { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "New password")]
        public string NewPassword { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Confirm new password")]
        [Compare("NewPassword", ErrorMessage = "The new password and confirmation password do not match.")]
        public string ConfirmPassword { get; set; }
    }

    public class LoginModel
    {
        [Required]
        [Display(Name = "User name")]
        public string UserName { get; set; }

        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }

        [Display(Name = "Remember me?")]
        public bool RememberMe { get; set; }
    }

    public class RegisterModel
    {
        [Required]
        [Display(Name = "User name")]
        public string UserName { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "The {0} must be at least {2} characters long.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Confirm password")]
        [Compare("Password", ErrorMessage = "The password and confirmation password do not match.")]
        public string ConfirmPassword { get; set; }
    }

    public class ExternalLogin
    {
        public string Provider { get; set; }
        public string ProviderDisplayName { get; set; }
        public string ProviderUserId { get; set; }
    }
}
