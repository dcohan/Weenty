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
    
    public partial class offer
    {
        public offer()
        {
            this.images = new HashSet<images>();
        }
    
        public int IdOffer { get; set; }
        public string Title { get; set; }
        public Nullable<System.DateTime> StartDatetime { get; set; }
        public Nullable<System.DateTime> ExpirationDatetime { get; set; }
        public int IdProduct { get; set; }
        public Nullable<System.DateTime> CreationDatetime { get; set; }
        public Nullable<System.DateTime> ModificationDatetime { get; set; }
        public Nullable<System.DateTime> DeletionDatetime { get; set; }
        public string ImagePath { get; set; }
        public Nullable<double> Price { get; set; }
        public string Description { get; set; }
    
        public virtual ICollection<images> images { get; set; }
        public virtual product product { get; set; }
    }
}
