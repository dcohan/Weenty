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
    
    public partial class images
    {
        public int IdImage { get; set; }
        public string ImagePath { get; set; }
        public int IdProduct { get; set; }
    
        public virtual product product { get; set; }
    }
}
