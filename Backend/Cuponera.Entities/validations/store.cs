using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(storeMetadata))]
    public partial class store
    {

    }
    public class storeMetadata
    {
        [Display(Name = "Compañía")]
        public int IdCompany { get; set; }

        [Display(Name = "Categoría")]
        [Required(ErrorMessage = "Debe ingresar la categoría.")]
        public int IdCategory { get; set; }

        [Display(Name = "Nombre")]
        [Required(ErrorMessage = "Debe ingresar el nombre.")]
        [MaxLength(100, ErrorMessage = "El nombre de la sucursal debe tener como máximo de 100 caracteres."), MinLength(2, ErrorMessage = "El nombre de la sucursal debe tener como mínimo 2 caracteres.")]
        public string Name { get; set; }

        [MaxLength(500, ErrorMessage = "la descripción de la sucursal debe tener como máximo de 500 caracteres."), MinLength(10, ErrorMessage = "La descripción de la sucursal debe tener como mínimo 10 caracteres.")]
        [DataType(DataType.MultilineText)]
        public string Description { get; set; }

        [Display(Name = "Latitud")]
        public double? Latitude { get; set; }

        [Display(Name = "Longitud")]
        public double? Longitude { get; set; }

        [Display(Name = "Dirección")]
        [Required(ErrorMessage = "Debe ingresar la dirección.")]
        [MaxLength(200, ErrorMessage = "La dirección debe tener como máximo de 200 caracteres."), MinLength(5, ErrorMessage = "La dirección debe tener como mínimo 5 caracteres.")]
        public string Address { get; set; }

        [Display(Name = "Teléfono de contacto")]
        [MaxLength(20, ErrorMessage = "El teléfono debe tener como máximo de 20 caracteres."), MinLength(6, ErrorMessage = "El teléfono debe tener como mínimo 6 caracteres.")]
        public string ContactNumber { get; set; }

        [Display(Name = "E-mail")]
        [RegularExpression(@"^([\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+\.)*[\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\.)+[a-zA-Z]{2,6})|(\d{1,3}\.){3}\d{1,3}(\:\d{1,5})?)$", ErrorMessage = "El formato de e-mail no es válido.")]
        public string Email { get; set; }

        [Display(Name = "Código Postal")]
        public string ZipCode { get; set; }

        [Display(Name = "Horario de atención")]
        public string StoreHours { get; set; }

        [Display(Name = "Página Web")]
        public string WebPage { get; set; }

        [Display(Name = "Facebook URL")]
        [RegularExpression(@"^(https?:\/\/)www.facebook.com\/(\w+)$", ErrorMessage = "El Facebook URL no es válido.")]
        public string FacebookUrl { get; set; }

        [Display(Name = "Whats App")]
        public string WhatsApp { get; set; }
    }
}
