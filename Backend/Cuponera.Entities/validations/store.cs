﻿using System;
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
        /// <summary>
        /// It is modifiable when is not deleted, and has an active subscription
        /// </summary>
        public bool Modifiable
        {
            get
            {
                if (company == null)
                {
                    return true;
                }
                return company.DeletionDatetime == null && company.companySubscription.Where(cs => cs.EndDate >= DateTime.Now).FirstOrDefault() != null;
            }
        }
    }
    public class storeMetadata
    {
        [Display(Name = "Compañía")]
        public int IdCompany { get; set; }

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
        [Phone(ErrorMessage="Por favor inserte un teléfono válido")]
        public string ContactNumber { get; set; }

        
        [Display(Name = "E-mail")]
        [EmailAddress(ErrorMessage = "Por favor inserte un e-mail válido")]
        public string Email { get; set; }

        [Display(Name = "Código Postal")]
        public string ZipCode { get; set; }

        [Display(Name = "Horario de atención")]
        public string StoreHours { get; set; }

        [Display(Name = "Página Web")]
        [Url(ErrorMessage = "Por favor inserte una URL válida")]
        public string WebPage { get; set; }

        [Display(Name = "Facebook URL")]
        [RegularExpression(@"^(https?:\/\/)www.facebook.com\/(\w+)$", ErrorMessage = "El Facebook URL no es válido.")]
        public string FacebookUrl { get; set; }

        [Display(Name = "Whats App")]
        public string WhatsApp { get; set; }

        [Display(Name = "Nextel")]
        [Phone(ErrorMessage = "Por favor inserte un teléfono válido")]
        public string Nextel { get; set; }
    }
}
