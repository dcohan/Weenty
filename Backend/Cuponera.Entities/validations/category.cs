using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(categoryMetadata))]
    public partial class category
    {

    }
    public class categoryMetadata
    {
        [Display(Name = "Nombre")]
        [Required(ErrorMessage = "Debe ingresar un nombre de categoría.")]
        [MaxLength(100, ErrorMessage = "El nombre de la categoría debe tener como máximo de 100 caracteres."), MinLength(5, ErrorMessage = "El nombre de la categoría debe tener como mínimo 10 caracteres.")]
        public string Name { get; set; }
    }
}
