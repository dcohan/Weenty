using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cuponera.Entities.validations;

namespace Cuponera.Entities
{
    [MetadataType(typeof(offerMetadata))]
    public partial class offer
    {

        public bool Active
        {
            get { return true; }
        }
        public bool Modifiable
        {
            get
            {
                if (product == null)
                {
                    return true;
                }
                bool producEnabled = !product.DeletionDatetime.HasValue;
                bool storeEnabled = !product.store.DeletionDatetime.HasValue;
                bool companyEnabled = !product.store.company.DeletionDatetime.HasValue && (product.store.company.companySubscription.Where(cs => cs.EndDate >= DateTime.Now).FirstOrDefault() != null);
                return producEnabled && storeEnabled && companyEnabled;
            }
        }
    }

    public class offerMetadata
    {
        [Display(Name="Titulo")]
        [Required(ErrorMessage = "Debe ingresar un titulo válido para la oferta.")]
        [MaxLength(500, ErrorMessage = "El titulo de la oferta debe tener como máximo de 500 caracteres."), MinLength(5, ErrorMessage = "El titulo de la oferta debe tener como mínimo 5 caracteres.")]
        public string Title { get; set; }

        [Required(ErrorMessage = "Debe ingresar una descripción válida para la oferta.")]
        [MaxLength(500, ErrorMessage = "la descripción de la oferta debe tener como máximo de 500 caracteres."), MinLength(10, ErrorMessage = "La descripción de la oferta debe tener como mínimo 10 caracteres.")]
        [DataType(DataType.MultilineText)]
        public string Description { get; set; }

        [Required(ErrorMessage = "Debe ingresar una fecha de activación válida.")]
        [Display(Name = "Fecha inicio")]
        [DisplayFormat(DataFormatString = "{0:dd/MM/yyyy}", ApplyFormatInEditMode = true)]
        [CurrentDate()]
        public DateTime StartDatetime { get; set; }

        [Display(Name = "Fecha fin")]
        [DisplayFormat(DataFormatString = "{0:dd/MM/yyyy}", ApplyFormatInEditMode = true)]
        [CurrentDate()]
        public Nullable<DateTime> ExpirationDatetime { get; set; }


        [Required(ErrorMessage = "Debe ingresar un precio.")]
        public Nullable<double> Price { get; set; }


    }
}
