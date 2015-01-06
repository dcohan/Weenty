using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;

namespace Cuponera.WebSite.Controllers
{
    public class ValidatorController : Controller
    {
        protected CuponeraEntities db = new CuponeraEntities();

        // GET: Validator
        public ActionResult ValidateEntity(int IdObject, string typeObject)
        {
            switch(typeObject)
            { 
                case "product": 
                    ViewBag.IsValid = StoreEnabled(IdObject);
                    break;
                case "offer":
                    ViewBag.IsValid = ProductEnabled(IdObject);
                    break;
                case "store":
                    ViewBag.IsValid = CompanyEnabled(IdObject);
                    break;
            }

            return View();
        }

        private bool CompanyEnabled(int IdCompany)
        {
            return !(db.company.Where(c => c.IdCompany.Equals(IdCompany) && c.DeletionDatetime.HasValue).FirstOrDefault() != null);
        }

        private bool StoreEnabled(int IdStore)
        {
            var store = db.store.Where(s => s.IdStore.Equals(IdStore)).FirstOrDefault();
            if (store.DeletionDatetime.HasValue || !CompanyEnabled(store.IdCompany))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        private bool ProductEnabled(int IdProduct)
        {
            var product = db.product.Where(p => p.IdProduct.Equals(IdProduct)).FirstOrDefault();
            if (product.DeletionDatetime.HasValue || !StoreEnabled(product.IdStore))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}