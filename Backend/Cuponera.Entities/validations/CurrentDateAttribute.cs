using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities.validations
{
    public class CurrentDateAttribute : ValidationAttribute
    {
        public CurrentDateAttribute()
        {
            ErrorMessage = "La fecha debe ser mayor a la actual.";
        }

        public override bool IsValid(object value)
        {
            var dt = (DateTime?)value;
            if (dt == null)
            {
                return true;
            }

            if (dt >= DateTime.Now)
            {
                return true;
            }
            return false;
        }
    }
}
