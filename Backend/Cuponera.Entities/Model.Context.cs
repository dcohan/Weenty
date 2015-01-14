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
    using System.Data.Entity.Core.Objects;
    using System.Linq;
    
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
        public virtual DbSet<company> company { get; set; }
        public virtual DbSet<companySubscription> companySubscription { get; set; }
        public virtual DbSet<ELMAH_Error> ELMAH_Error { get; set; }
        public virtual DbSet<geoRequest> geoRequest { get; set; }
        public virtual DbSet<images> images { get; set; }
        public virtual DbSet<occasion> occasion { get; set; }
        public virtual DbSet<offer> offer { get; set; }
        public virtual DbSet<preHomeImages> preHomeImages { get; set; }
        public virtual DbSet<product> product { get; set; }
        public virtual DbSet<profile> profile { get; set; }
        public virtual DbSet<state> state { get; set; }
        public virtual DbSet<store> store { get; set; }
        public virtual DbSet<storeCategory> storeCategory { get; set; }
        public virtual DbSet<subcategory> subcategory { get; set; }
        public virtual DbSet<subscription> subscription { get; set; }
        public virtual DbSet<userCompany> userCompany { get; set; }
        public virtual DbSet<UserProfile> UserProfile { get; set; }
        public virtual DbSet<webpages_Membership> webpages_Membership { get; set; }
        public virtual DbSet<webpages_OAuthMembership> webpages_OAuthMembership { get; set; }
        public virtual DbSet<webpages_Roles> webpages_Roles { get; set; }
    
        public virtual int CheckIfUserCanAskForCoordinates(Nullable<int> idUser)
        {
            var idUserParameter = idUser.HasValue ?
                new ObjectParameter("idUser", idUser) :
                new ObjectParameter("idUser", typeof(int));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("CheckIfUserCanAskForCoordinates", idUserParameter);
        }
    
        public virtual ObjectResult<string> ELMAH_GetErrorsXml(string application, Nullable<int> pageIndex, Nullable<int> pageSize, ObjectParameter totalCount)
        {
            var applicationParameter = application != null ?
                new ObjectParameter("Application", application) :
                new ObjectParameter("Application", typeof(string));
    
            var pageIndexParameter = pageIndex.HasValue ?
                new ObjectParameter("PageIndex", pageIndex) :
                new ObjectParameter("PageIndex", typeof(int));
    
            var pageSizeParameter = pageSize.HasValue ?
                new ObjectParameter("PageSize", pageSize) :
                new ObjectParameter("PageSize", typeof(int));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<string>("ELMAH_GetErrorsXml", applicationParameter, pageIndexParameter, pageSizeParameter, totalCount);
        }
    
        public virtual ObjectResult<string> ELMAH_GetErrorXml(string application, Nullable<System.Guid> errorId)
        {
            var applicationParameter = application != null ?
                new ObjectParameter("Application", application) :
                new ObjectParameter("Application", typeof(string));
    
            var errorIdParameter = errorId.HasValue ?
                new ObjectParameter("ErrorId", errorId) :
                new ObjectParameter("ErrorId", typeof(System.Guid));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<string>("ELMAH_GetErrorXml", applicationParameter, errorIdParameter);
        }
    
        public virtual int ELMAH_LogError(Nullable<System.Guid> errorId, string application, string host, string type, string source, string message, string user, string allXml, Nullable<int> statusCode, Nullable<System.DateTime> timeUtc)
        {
            var errorIdParameter = errorId.HasValue ?
                new ObjectParameter("ErrorId", errorId) :
                new ObjectParameter("ErrorId", typeof(System.Guid));
    
            var applicationParameter = application != null ?
                new ObjectParameter("Application", application) :
                new ObjectParameter("Application", typeof(string));
    
            var hostParameter = host != null ?
                new ObjectParameter("Host", host) :
                new ObjectParameter("Host", typeof(string));
    
            var typeParameter = type != null ?
                new ObjectParameter("Type", type) :
                new ObjectParameter("Type", typeof(string));
    
            var sourceParameter = source != null ?
                new ObjectParameter("Source", source) :
                new ObjectParameter("Source", typeof(string));
    
            var messageParameter = message != null ?
                new ObjectParameter("Message", message) :
                new ObjectParameter("Message", typeof(string));
    
            var userParameter = user != null ?
                new ObjectParameter("User", user) :
                new ObjectParameter("User", typeof(string));
    
            var allXmlParameter = allXml != null ?
                new ObjectParameter("AllXml", allXml) :
                new ObjectParameter("AllXml", typeof(string));
    
            var statusCodeParameter = statusCode.HasValue ?
                new ObjectParameter("StatusCode", statusCode) :
                new ObjectParameter("StatusCode", typeof(int));
    
            var timeUtcParameter = timeUtc.HasValue ?
                new ObjectParameter("TimeUtc", timeUtc) :
                new ObjectParameter("TimeUtc", typeof(System.DateTime));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("ELMAH_LogError", errorIdParameter, applicationParameter, hostParameter, typeParameter, sourceParameter, messageParameter, userParameter, allXmlParameter, statusCodeParameter, timeUtcParameter);
        }
    
        public virtual ObjectResult<GetNearestStores_Result> GetNearestStores(Nullable<int> idCategory, Nullable<double> latitude, Nullable<double> longitude)
        {
            var idCategoryParameter = idCategory.HasValue ?
                new ObjectParameter("IdCategory", idCategory) :
                new ObjectParameter("IdCategory", typeof(int));
    
            var latitudeParameter = latitude.HasValue ?
                new ObjectParameter("Latitude", latitude) :
                new ObjectParameter("Latitude", typeof(double));
    
            var longitudeParameter = longitude.HasValue ?
                new ObjectParameter("Longitude", longitude) :
                new ObjectParameter("Longitude", typeof(double));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<GetNearestStores_Result>("GetNearestStores", idCategoryParameter, latitudeParameter, longitudeParameter);
        }
    
        public virtual ObjectResult<GetNearestStoresByName_Result> GetNearestStoresByName(Nullable<int> idCategory, Nullable<double> latitude, Nullable<double> longitude, string name)
        {
            var idCategoryParameter = idCategory.HasValue ?
                new ObjectParameter("IdCategory", idCategory) :
                new ObjectParameter("IdCategory", typeof(int));
    
            var latitudeParameter = latitude.HasValue ?
                new ObjectParameter("Latitude", latitude) :
                new ObjectParameter("Latitude", typeof(double));
    
            var longitudeParameter = longitude.HasValue ?
                new ObjectParameter("Longitude", longitude) :
                new ObjectParameter("Longitude", typeof(double));
    
            var nameParameter = name != null ?
                new ObjectParameter("Name", name) :
                new ObjectParameter("Name", typeof(string));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<GetNearestStoresByName_Result>("GetNearestStoresByName", idCategoryParameter, latitudeParameter, longitudeParameter, nameParameter);
        }
    
        public virtual ObjectResult<GetNearestStoresWithOffers_Result> GetNearestStoresWithOffers(Nullable<double> latitude, Nullable<double> longitude)
        {
            var latitudeParameter = latitude.HasValue ?
                new ObjectParameter("Latitude", latitude) :
                new ObjectParameter("Latitude", typeof(double));
    
            var longitudeParameter = longitude.HasValue ?
                new ObjectParameter("Longitude", longitude) :
                new ObjectParameter("Longitude", typeof(double));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<GetNearestStoresWithOffers_Result>("GetNearestStoresWithOffers", latitudeParameter, longitudeParameter);
        }
    
        public virtual ObjectResult<GetProductAndOffers_Result> GetProductAndOffers(Nullable<int> idStore, Nullable<int> idCategoria)
        {
            var idStoreParameter = idStore.HasValue ?
                new ObjectParameter("IdStore", idStore) :
                new ObjectParameter("IdStore", typeof(int));
    
            var idCategoriaParameter = idCategoria.HasValue ?
                new ObjectParameter("IdCategoria", idCategoria) :
                new ObjectParameter("IdCategoria", typeof(int));
    
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<GetProductAndOffers_Result>("GetProductAndOffers", idStoreParameter, idCategoriaParameter);
        }
    
        public virtual ObjectResult<GetSubscriptionsForCompanies_Result> GetSubscriptionsForCompanies()
        {
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<GetSubscriptionsForCompanies_Result>("GetSubscriptionsForCompanies");
        }
    }
}
