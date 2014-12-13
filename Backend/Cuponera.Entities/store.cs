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
    using System.Collections.Generic;
    
    public partial class store
    {
        public store()
        {
            this.images = new HashSet<images>();
            this.occasion = new HashSet<occasion>();
            this.product = new HashSet<product>();
        }
    
        public int IdStore { get; set; }
        public int IdCompany { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string ContactNumber { get; set; }
        public Nullable<double> Latitude { get; set; }
        public Nullable<double> Longitude { get; set; }
        public string ZipCode { get; set; }
        public Nullable<int> IdState { get; set; }
        public string StoreHours { get; set; }
        public string Email { get; set; }
        public string FacebookUrl { get; set; }
        public string WhatsApp { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
        public string ImagePath { get; set; }
    
        public virtual company company { get; set; }
        public virtual ICollection<images> images { get; set; }
        public virtual ICollection<occasion> occasion { get; set; }
        public virtual ICollection<product> product { get; set; }
        public virtual state state { get; set; }
    }
}
