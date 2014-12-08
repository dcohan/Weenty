using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(companyMetadata))]
    public partial class company
    {

    }
    public class companyMetadata
    {
        [Display(Name = "Nombre")]
        [Required(ErrorMessage = "Debe ingresar un nombre de compañia.")]
        [MaxLength(100, ErrorMessage = "El nombre de la compañia debe tener como máximo de 100 caracteres."), MinLength(3, ErrorMessage = "El nombre de la compañia debe tener como mínimo 3 caracteres.")]
        public string Name { get; set; }
    }
}
