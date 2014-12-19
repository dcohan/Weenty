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
        [Display(Name="Titulo")]
        [Required(ErrorMessage = "Debe ingresar un titulo válido para la oferta.")]
        [MaxLength(500, ErrorMessage = "El titulo de la oferta debe tener como máximo de 500 caracteres."), MinLength(5, ErrorMessage = "El titulo de la oferta debe tener como mínimo 10 caracteres.")]
        public string Title { get; set; }

        [Display(Name = "Activo")]
        public int Active { get; set; }

        [Display(Name = "Fecha fin")]
        [DataType(DataType.DateTime)]
        public DateTime ExpirationDatetime { get; set; }

        [Display(Name = "Producto")]
        [Required(ErrorMessage = "Debe asociar la oferta a un producto.")]
        public int IdProduct { get; set; }

        [Display(Name = "Fecha inicio")]
        [DataType(DataType.DateTime)]
        public Nullable<DateTime> StartDatetime {get;set;}

        [Display(Name = "Fecha creacion")]
        [DataType(DataType.DateTime)]
        public Nullable<DateTime> CreationDatetime { get; set; }

        [Display(Name = "Fecha modificacion")]
        [DataType(DataType.DateTime)]
        public Nullable<DateTime> ModificationDatetime { get; set; }

    }
}
