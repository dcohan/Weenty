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
    
    public partial class devicetypes
    {
        public devicetypes()
        {
            this.profile = new HashSet<profile>();
        }
    
        public int IdDeviceType { get; set; }
        public string Code { get; set; }
    
        public virtual ICollection<profile> profile { get; set; }
    }
}
