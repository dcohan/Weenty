using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(productMetadata))]
    public partial class product
    {

    }
    public class productMetadata
    {
        [Required(ErrorMessage = "Debe ingresar un titulo válido para el producto.")]
        [MaxLength(100, ErrorMessage = "El titulo del producto debe tener como máximo de 100 caracteres."), MinLength(5, ErrorMessage = "El titulo del producto debe tener como mínimo 10 caracteres.")]
        public string Title { get; set; }

        [Required(ErrorMessage = "Debe ingresar una descripción válida para el producto.")]
        [MaxLength(100, ErrorMessage = "la descripción del producto debe tener como máximo de 100 caracteres."), MinLength(10, ErrorMessage = "La descripción del producto debe tener como mínimo 10 caracteres.")]
        public string Description { get; set; }

        [Required(ErrorMessage = "Debe ingresar una fecha de activación válida.")]
        [Display(Name = "Fecha inicio")]
        [DataType(DataType.Date)]
        public DateTime StartDatetime { get; set; }

        [Display(Name = "Fecha fin")]
        [DataType(DataType.Date)]
        public DateTime ExpirationDatetime { get; set; }

        [Required(ErrorMessage = "Debe asociar el producto a una categoria.")]
        public int IdCategory { get; set; }

    }
}
