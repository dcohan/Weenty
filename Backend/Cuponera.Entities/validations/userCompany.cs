using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(userCompanyMetadata))]
    public partial class userCompany
    {

    }
    
    public class userCompanyMetadata
    {
        [Display(Name = "Usuario")]
        public int IdUser { get; set; }

        [Display(Name = "Compañia")]    
        public int IdCompany { get; set; }

        [Display(Name = "Admin Compañia")]
        public bool IsAdmin { get; set; }

        [Display(Name = "Sucursal")]
        public Nullable<int> IdStore { get; set; }

    }
}
