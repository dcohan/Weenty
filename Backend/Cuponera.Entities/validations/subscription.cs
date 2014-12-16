using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(subscriptionMetadata))]
    public partial class subscription
    {

    }
    public class subscriptionMetadata
    {
        [Display(Name = "Nombre")]
        [Required(ErrorMessage="Debe completar el nombre de la suscripción.")]
        [MaxLength(100, ErrorMessage = "El nombre de la suscripción debe tener como máximo de 100 caracteres."), MinLength(2, ErrorMessage = "El nombre de la suscripción debe tener como mínimo 2 caracteres.")]
        public string Name { get; set; }

        [Display(Name = "Precio")]
        [Required(ErrorMessage = "Debe completar el precio de la suscripción.")]
        public double Pricing { get; set; }

        [Display(Name = "Orden")]
        [Required(ErrorMessage = "Debe completar el orden de la suscripción.")]
        [Range(1, Int32.MaxValue, ErrorMessage = "El orden de la suscripción debe ser mayor a 0.")]
        public int SortFactor { get; set; }

        [Display(Name = "Duración")]
        [Required(ErrorMessage = "Debe completar la duración de la suscripción.")]
        [Range(1, Int32.MaxValue, ErrorMessage = "El orden de la suscripción debe ser mayor a 0.")]
        public int duration { get; set; }
    }
}
