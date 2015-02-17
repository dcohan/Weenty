using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(stateMetadata))]
    public partial class state
    {
        [Url(ErrorMessage = "Por favor inserte una URL válida")]
        public string HomeBannerLink { get; set; }

        [Url(ErrorMessage = "Por favor inserte una URL válida")]
        public string ListBannerLink { get; set; }
    }
    public class stateMetadata
    {
        [Display(Name = "Nombre")]
        [Required(ErrorMessage="Debe completar el nombre de la ciudad.")]
        [MaxLength(100, ErrorMessage = "El nombre de la ciudad debe tener como máximo de 100 caracteres."), MinLength(2, ErrorMessage = "El nombre de la ciudad debe tener como mínimo 2 caracteres.")]
        public string Name { get; set; }

        [Display(Name = "Latitud")]
        public double? Latitude { get; set; }

        [Display(Name = "Longitud")]
        public double? Longitude { get; set; }

        [Display(Name = "Link")]
        [Url(ErrorMessage = "Por favor inserte una URL válida")]
        public string Link { get; set; }

    }
}
