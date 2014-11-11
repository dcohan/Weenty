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
            this.companyStore = new HashSet<companyStore>();
        }
    
        public int IdStore { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string ContactNumber { get; set; }
        public Nullable<double> Latitude { get; set; }
        public Nullable<double> Longitude { get; set; }
        public string ZipCode { get; set; }
        public string City { get; set; }
        public string IdState { get; set; }
        public string StoreHours { get; set; }
        public Nullable<System.DateTime> GeolocationProcessingDatetime { get; set; }
        public int GeolocationTries { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
    
        public virtual ICollection<companyStore> companyStore { get; set; }
        public virtual state state { get; set; }
    }
}
