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
    
    public partial class permissions
    {
        public int idPermission { get; set; }
        public int IdRole { get; set; }
        public int IdModule { get; set; }
        public Nullable<short> Read { get; set; }
        public Nullable<short> Create { get; set; }
        public Nullable<short> Update { get; set; }
        public Nullable<short> Delete { get; set; }
    }
}
