﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Cuponera.Entities
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class CuponeraEntities : DbContext
    {
        public CuponeraEntities()
            : base("name=CuponeraEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<category> category { get; set; }
        public virtual DbSet<city> city { get; set; }
        public virtual DbSet<company> company { get; set; }
        public virtual DbSet<companySubscription> companySubscription { get; set; }
        public virtual DbSet<deviceos> deviceos { get; set; }
        public virtual DbSet<devicetypes> devicetypes { get; set; }
        public virtual DbSet<images> images { get; set; }
        public virtual DbSet<offer> offer { get; set; }
        public virtual DbSet<permissions> permissions { get; set; }
        public virtual DbSet<prehomeimages> prehomeimages { get; set; }
        public virtual DbSet<product> product { get; set; }
        public virtual DbSet<profile> profile { get; set; }
        public virtual DbSet<state> state { get; set; }
        public virtual DbSet<store> store { get; set; }
        public virtual DbSet<subscription> subscription { get; set; }
        public virtual DbSet<userCompany> userCompany { get; set; }
        public virtual DbSet<UserProfile> UserProfile { get; set; }
        public virtual DbSet<webpages_Membership> webpages_Membership { get; set; }
        public virtual DbSet<webpages_OAuthMembership> webpages_OAuthMembership { get; set; }
        public virtual DbSet<webpages_Roles> webpages_Roles { get; set; }
    }
}
