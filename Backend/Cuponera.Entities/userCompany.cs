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
    
    public partial class userCompany
    {
        public int IdUserCompany { get; set; }
        public int IdUser { get; set; }
        public int IdCompany { get; set; }
        public bool IsAdmin { get; set; }
        public Nullable<int> IdStore { get; set; }
    
        public virtual company company { get; set; }
        public virtual store store { get; set; }
        public virtual UserProfile UserProfile { get; set; }
    }
}
