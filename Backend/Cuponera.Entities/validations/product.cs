﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cuponera.Entities.validations;

namespace Cuponera.Entities
{
    [MetadataType(typeof(productMetadata))]
    public partial class product
    {
        public bool Active
        {
            get
            {
                return !DeletionDatetime.HasValue;
            }
            set
            {
                if (value)
                {
                    DeletionDatetime = null;
                }
                else
                {
                    DeletionDatetime = DateTime.Now;
                }
            }
        }

        /// <summary>
        /// It is modifiable when is not deleted, and has an active subscription
        /// </summary>
        public bool Modifiable
        {
            get
            {
                if (store == null) {
                    return true;
                }
                return store.DeletionDatetime == null && store.company.DeletionDatetime == null && store.company.companySubscription.Where(cs => cs.EndDate >= DateTime.Now).FirstOrDefault() != null;
            }
        }
    }
    public class productMetadata
    {
        [Required(ErrorMessage = "Debe ingresar un titulo válido para el producto.")]
        [MaxLength(100, ErrorMessage = "El titulo del producto debe tener como máximo de 100 caracteres."), MinLength(5, ErrorMessage = "El titulo del producto debe tener como mínimo 5 caracteres.")]
        public string Title { get; set; }

        [Required(ErrorMessage = "Debe ingresar una descripción válida para el producto.")]
        [MaxLength(500, ErrorMessage = "la descripción del producto debe tener como máximo de 500 caracteres."), MinLength(10, ErrorMessage = "La descripción del producto debe tener como mínimo 10 caracteres.")]
        [DataType(DataType.MultilineText)]
        public string Description { get; set; }

        [Display(Name="Activo")]
        public bool Active { get; set; }

        [Required(ErrorMessage = "Debe ingresar una fecha de activación válida.")]
        [Display(Name = "Fecha inicio")]
        [DisplayFormat(DataFormatString = "{0:dd/MM/yyyy}", ApplyFormatInEditMode = true)]
 
        public DateTime StartDatetime { get; set; }

        [Display(Name = "Fecha fin")]
        [DisplayFormat(DataFormatString = "{0:dd/MM/yyyy}", ApplyFormatInEditMode = true)]
        [CurrentDate()]
        public Nullable<DateTime> ExpirationDatetime { get; set; }

        [Required(ErrorMessage = "Debe asociar el producto a una categoria.")]
        public int IdCategory { get; set; }

        [Required(ErrorMessage = "Debe ingresar un precio.")]
        public Nullable<double> Price { get; set; }

    }
}
