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
    
    public partial class state
    {
        public state()
        {
            this.store = new HashSet<store>();
            this.store1 = new HashSet<store>();
        }
    
        public int IdState { get; set; }
        public string Name { get; set; }
        public string Link { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
    
        public virtual ICollection<store> store { get; set; }
        public virtual ICollection<store> store1 { get; set; }
    }
}
