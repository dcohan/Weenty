//------------------------------------------------------------------------------
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
    
    public partial class GetProductAndOffers_Result
    {
        public string PTitle { get; set; }
        public string PImagePath { get; set; }
        public string PDescription { get; set; }
        public int IdProduct { get; set; }
        public Nullable<double> PPrice { get; set; }
        public string OTitle { get; set; }
        public string OImagePath { get; set; }
        public Nullable<int> IdOffer { get; set; }
        public Nullable<double> OPrice { get; set; }
        public string ODescription { get; set; }
    }
}