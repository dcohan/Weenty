using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(offerMetadata))]
    public partial class offer
    {

    }
    public class offerMetadata
    {
        [Required(ErrorMessage = "Debe ingresar un titulo válido para la oferta.")]
        [MaxLength(100, ErrorMessage = "El titulo de la oferta debe tener como máximo de 100 caracteres."), MinLength(5, ErrorMessage = "El titulo de la oferta debe tener como mínimo 10 caracteres.")]
        public string Title { get; set; }

        [Range(0,1, ErrorMessage="El campo activo debe indicar con un 0 si la oferta se encuentra inactiva, o con 1 si se encuentra activa.")]
        public int Active { get; set; }

        [DataType(System.ComponentModel.DataAnnotations.DataType.DateTime)]
        public DateTime ExpirationDatetime { get; set; }

        [Required(ErrorMessage = "Debe asociar la oferta a un producto.")]
        public int IdProduct { get; set; }

        [Required(ErrorMessage = "Debe ingresar una imagen para la oferta.")]
        public string ImagePath { get; set; }

    }
}
