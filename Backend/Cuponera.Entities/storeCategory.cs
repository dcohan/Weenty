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
    
    public partial class storeCategory
    {
        public int IdStoreCategory { get; set; }
        public int IdStore { get; set; }
        public Nullable<int> IdCategory { get; set; }
        public Nullable<int> IdSubCategory { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
    
        public virtual category category { get; set; }
        public virtual store store { get; set; }
        public virtual subcategory subcategory { get; set; }
    }
}
