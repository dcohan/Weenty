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
    using System.Collections.Generic;
    
    public partial class company
    {
        public company()
        {
            this.companySubscription = new HashSet<companySubscription>();
            this.store = new HashSet<store>();
            this.userCompany = new HashSet<userCompany>();
        }
    
        public int IdCompany { get; set; }
        public string Name { get; set; }
        public string Contact { get; set; }
        public string Telephone { get; set; }
        public string Email { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
    
        public virtual ICollection<companySubscription> companySubscription { get; set; }
        public virtual ICollection<store> store { get; set; }
        public virtual ICollection<userCompany> userCompany { get; set; }
    }
}
