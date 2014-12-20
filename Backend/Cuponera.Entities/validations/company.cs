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

        [Display(Name = "Contacto")]
        public string Contact { get; set; }

        [Display(Name = "Teléfono")]
        [RegularExpression(@"^[0-9]*$", ErrorMessage = "El formato del teléfono no es válido. Solamente números")]
        public string Telephone { get; set; }

        [Display(Name = "E-mail")]
        [RegularExpression(@"^([\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+\.)*[\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\.)+[a-zA-Z]{2,6})|(\d{1,3}\.){3}\d{1,3}(\:\d{1,5})?)$", ErrorMessage = "El formato de e-mail no es válido.")]
        public string Email { get; set; }
    }
}
