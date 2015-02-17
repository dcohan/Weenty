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
        //[RegularExpression(@"^(?:(?:https?|ftp)://)(?:\S+(?::\S*)?@)?(?:(?!10(?:\.\d{1,3}){3})(?!127(?:\.\d{1,3}){3})(?!169\.254(?:\.\d{1,3}){2})(?!192\.168(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)(?:\.(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)*(?:\.(?:[a-z\x{00a1}-\x{ffff}]{2,})))(?::\d{2,5})?(?:/[^\s]*)?$", ErrorMessage = "El Facebook URL no es válido.")]
        public string Link { get; set; }

    }
}
