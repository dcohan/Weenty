//------------------------------------------------------------------------------
// <auto-generated>
//     Este código se generó a partir de una plantilla.
//
//     Los cambios manuales en este archivo pueden causar un comportamiento inesperado de la aplicación.
//     Los cambios manuales en este archivo se sobrescribirán si se regenera el código.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Cuponera.Entities
{
    using System;
    
    public partial class GetNearestStoresWithOffers_Result
    {
        public int IdStore { get; set; }
        public int IdCompany { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string Address { get; set; }
        public Nullable<double> Latitude { get; set; }
        public Nullable<double> Longitude { get; set; }
        public string ZipCode { get; set; }
        public string ContactNumber { get; set; }
        public Nullable<int> IdCategory { get; set; }
        public Nullable<int> IdState { get; set; }
        public string StoreHours { get; set; }
        public string ImagePath { get; set; }
        public string Email { get; set; }
        public string WebPage { get; set; }
        public string FacebookUrl { get; set; }
        public string WhatsApp { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
        public Nullable<double> Distance { get; set; }
        public int HasOffers { get; set; }
    }
}
