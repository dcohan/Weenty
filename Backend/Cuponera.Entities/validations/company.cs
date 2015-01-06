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
        /// <summary>
        /// It is modifiable when is not deleted, and has an active subscription
        /// </summary>
        public bool Modifiable
        {
            get
            {
                return !DeletionDatetime.HasValue && companySubscription.Where(cs => cs.EndDate >= DateTime.Now).FirstOrDefault() != null;
            }
        }
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
        [MaxLength(20, ErrorMessage = "El teléfono debe tener como máximo de 20 caracteres."), MinLength(6, ErrorMessage = "El teléfono debe tener como mínimo 6 caracteres.")]
        public string Telephone { get; set; }

        [Display(Name = "E-mail")]
        [RegularExpression(@"^([\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+\.)*[\w\!\#$\%\&\'\*\+\-\/\=\?\^\`{\|\}\~]+@((((([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,62}[a-zA-Z0-9]{1})|[a-zA-Z])\.)+[a-zA-Z]{2,6})|(\d{1,3}\.){3}\d{1,3}(\:\d{1,5})?)$", ErrorMessage = "El formato de e-mail no es válido.")]
        public string Email { get; set; }
    }
}
