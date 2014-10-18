//------------------------------------------------------------------------------
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
    using System.Collections.Generic;
    
    public partial class product
    {
        public product()
        {
            this.images = new HashSet<images>();
            this.offer = new HashSet<offer>();
        }
    
        public int IdProduct { get; set; }
        public string Title { get; set; }
        public string PromoCode { get; set; }
        public string TargetURL { get; set; }
        public Nullable<int> Active { get; set; }
        public Nullable<System.DateTime> StartDatetime { get; set; }
        public Nullable<System.DateTime> ExpirationDatetime { get; set; }
        public Nullable<int> ItemOrder { get; set; }
        public int IdCompany { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
        public string ImagePath { get; set; }
    
        public virtual company company { get; set; }
        public virtual ICollection<images> images { get; set; }
        public virtual ICollection<offer> offer { get; set; }
    }
}
