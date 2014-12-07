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
        [MaxLength(100, ErrorMessage = "El titulo de la oferta debe tener como máximo de 100 caracteres."), MinLength(5, ErrorMessage = "El titulo de la oferta debe tener como mínimo 10 caracteres.")]
        public string Title { get; set; }

        [Display(Name = "Activo")]
        [Range(0,1, ErrorMessage="El campo activo debe indicar con un 0 si la oferta se encuentra inactiva, o con 1 si se encuentra activa.")]
        public int Active { get; set; }

        [Display(Name = "Fecha fin")]
        [DataType(DataType.Date)]
        public DateTime ExpirationDatetime { get; set; }

        [Display(Name = "Producto")]
        [Required(ErrorMessage = "Debe asociar la oferta a un producto.")]
        public int IdProduct { get; set; }

        [Display(Name = "Imagen")]
        [Required(ErrorMessage = "Debe ingresar una imagen para la oferta.")]
        [DataType(DataType.ImageUrl)]
        public string ImagePath { get; set; }

        [Display(Name = "Fecha inicio")]
        [DataType(DataType.Date)]
        public Nullable<DateTime> StartDatetime {get;set;}

        [Display(Name = "Link a oferta")]
        public string TargetURL { get; set; }

        [Display(Name = "Fecha creacion")]
        [DataType(DataType.Date)]
        public Nullable<DateTime> CreationDatetime { get; set; }

        [Display(Name = "Fecha modificacion")]
        [DataType(DataType.Date)]
        public Nullable<DateTime> ModificationDatetime { get; set; }

    }
}
