﻿//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Cuponera.Backend.Data
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
    
        public DbSet<deviceos> deviceos { get; set; }
        public DbSet<devicetypes> devicetypes { get; set; }
        public DbSet<permissions> permissions { get; set; }
        public DbSet<prehomeimages> prehomeimages { get; set; }
        public DbSet<product> product { get; set; }
        public DbSet<profile> profile { get; set; }
        public DbSet<role> role { get; set; }
        public DbSet<state> state { get; set; }
        public DbSet<store> store { get; set; }
        public DbSet<subscription> subscription { get; set; }
        public DbSet<sysdiagrams> sysdiagrams { get; set; }
        public DbSet<user> user { get; set; }
        public DbSet<userStore> userStore { get; set; }
        public DbSet<userSubscription> userSubscription { get; set; }
    }
}
