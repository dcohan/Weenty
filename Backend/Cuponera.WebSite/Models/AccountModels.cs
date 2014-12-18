﻿using System;
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

            if (HttpContext.Current.Session != null && HttpContext.Current.Session["userId"] == null)
            {
                using (CuponeraEntities db = new CuponeraEntities())
                {
                    var _user = db.UserProfile.FirstOrDefault(u => u.UserName.ToLower() == Name.ToLower());
                    HttpContext.Current.Session["userId"] = _user.UserId;

                    var userCompany = db.userCompany.Where(uc => uc.IdUser.Equals(CuponeraIdentity.CurrentUserId) && uc.IsAdmin).FirstOrDefault();

                    //Is Admin
                    if (userCompany != null)
                    {
                        HttpContext.Current.Session["AdminCompany"] = true;
                    }
                    else
                    {
                        var stores = db.userCompany.Where(uc => uc.IdUser.Equals(CuponeraIdentity.CurrentUserId)).Select(s => s.IdStore).ToList();

                        HttpContext.Current.Session["AvaiableStores"] = stores;
                    }
                }
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

        public static int CurrentUserId
        {
            get
            {
                return (int)HttpContext.Current.Session["userId"];
            }
        }

        public static IEnumerable<object> CurrentAvaiableStores
        {
            get
            {
                return (IEnumerable<object>)HttpContext.Current.Session["AvaiableStores"];
            }
        }

        public static bool IsAdminCompany
        {
            get
            {
                return HttpContext.Current.Session["AdminCompany"] != null ? (bool)HttpContext.Current.Session["AdminCompany"] : false;
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

        public static bool CanAdminStores(List<int> IdStores)
        {
            //I'll try to get from SessionState
            List<int> currStore;
            if (IdStores == null  || IdStores.Count() == 0)
            {
                currStore = new List<int>();
                currStore.Add((int)HttpContext.Current.Session["currentStore"]);
            }
            else
            {
                currStore = IdStores;
            }

            return CuponeraIdentity.CurrentAvaiableStores.Join(currStore, s1 => s1, s2 => s2, (s1,s2) => new { store = s1 } ).Count() > 0;
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
