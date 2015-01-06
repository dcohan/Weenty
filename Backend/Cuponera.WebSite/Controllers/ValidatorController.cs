using System;
using System.Collections.Generic;
using System.Collections;
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
        public string ValidateEntity(int IdObject, string typeObject)
        {
            var inactiveEntities = new ArrayList();

            switch(typeObject)
            { 
                case "product":
                    inactiveEntities = StoreEnabled(IdObject, inactiveEntities);
                    break;
                case "offer":
                    inactiveEntities = ProductEnabled(IdObject, inactiveEntities);
                    break;
                case "store":
                    inactiveEntities = CompanyEnabled(IdObject, inactiveEntities);
                    break;
            }


            return Helpers.JSONHelper.SerializeJSON(inactiveEntities);
        }

        private ArrayList CompanyEnabled(int IdCompany, ArrayList inactiveEntities)
        {
            if (db.company.Where(c => c.IdCompany.Equals(IdCompany) && c.DeletionDatetime.HasValue).FirstOrDefault() != null)
            {
                inactiveEntities.Add("company");
            }

            return inactiveEntities;
        }

        private ArrayList StoreEnabled(int IdStore, ArrayList inactiveEntities)
        {
            var store = db.store.Where(s => s.IdStore.Equals(IdStore)).FirstOrDefault();
            inactiveEntities = CompanyEnabled(store.IdCompany, inactiveEntities);
            if (store.DeletionDatetime.HasValue){
                inactiveEntities.Add("store");
            }

            return inactiveEntities;
         }

        private ArrayList ProductEnabled(int IdProduct, ArrayList inactiveEntities)
        {
            var product = db.product.Where(p => p.IdProduct.Equals(IdProduct)).FirstOrDefault();
            inactiveEntities = StoreEnabled(product.IdStore, inactiveEntities);
            if (product.DeletionDatetime.HasValue){
                inactiveEntities.Add("product");
            }

            return inactiveEntities;
        }
    }
}