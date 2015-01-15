using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Cuponera.Entities;
using System.Collections;

namespace Cuponera.Backend.Controllers
{
    public class GetProductAndOffersController : ApiController
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: GetProductAndOffers
        [HttpGet]
        public object GetProductAndOffers([FromUri] int idStore, [FromUri] int idCategory)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var response = new { value = db.GetProductAndOffers(idStore, idCategory) };

            return response;
        }


        // GET: GetNearestStores
        [HttpGet]
        public object GetNearestStores([FromUri] int IdCategory, [FromUri] double Latitude, [FromUri] double Longitude)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var response = new { value = db.GetNearestStores(IdCategory, Latitude, Longitude) };

            return new { value = parseResponse(db.GetNearestStores(IdCategory, Latitude, Longitude)) };
        }

        // GET: GetNearestStoresByName
        [HttpGet]
        public object GetNearestStoresByName([FromUri] int IdCategory, [FromUri] double Latitude, [FromUri] double Longitude, [FromUri] String Name)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();

            return new { value = parseResponse(db.GetNearestStoresByName(IdCategory, Latitude, Longitude, Name) };
        }

        // GET: GetNearestStoresWithOffers
        [HttpGet]
        public object GetNearestStoresWithOffers([FromUri] double Latitude, [FromUri] double Longitude)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();

            return new { value = parseResponse(db.GetNearestStoresWithOffers(Latitude, Longitude) };
        }

        private object parseResponse(List<GetNearestStores> response)
        {
            ArrayList stores = new ArrayList();
            List<category> categoryList;
            List<subcategory> subCategoryList;
            IQueryable<storeCategory> sc;
            foreach (GetNearestStores store in response)
            {

                categoryList = new List<category>();
                subCategoryList = new List<subcategory>();

                sc = db.storeCategory.Where(scs => scs.IdStore == store.IdStore);
                if (sc.Count() > 0)
                {
                    foreach (storeCategory _sc in sc)
                    {
                        category cObject = new category();
                        subcategory scObject = new subcategory();

                        if (_sc.IdCategory != null)
                        {
                            cObject.IdCategory = (int)_sc.IdCategory;
                            cObject.Name = db.category.Find(_sc.IdCategory).Name;

                        }
                        else
                        {
                            scObject.IdSubCategory = (int)_sc.IdSubCategory;
                            scObject.Name = db.subcategory.Find(_sc.IdSubCategory).Name;
                            subCategoryList.Add(scObject);
                            cObject.subcategory = subCategoryList;

                        }
                        categoryList.Add(cObject);
                    }


                }

                stores.Add(new
                {
                    IdStore = store.IdStore,
                    Name = store.Name,
                    Address = store.Address,
                    ContactNumber = store.ContactNumber,
                    ZipCode = store.ZipCode,
                    Latitude = store.Latitude,
                    Longitude = store.Longitude,
                    IdState = store.IdState,
                    StoreHours = store.StoreHours,
                    Email = store.Email,
                    WebPage = store.WebPage,
                    FacebookUrl = store.FacebookUrl,
                    WhatsApp = store.WhatsApp,
                    ImagePath = store.ImagePath,
                    Description = store.Description,
                    Distance = store.Distance,
                    HasOffers = store.HasOffers,
                    HasProducts = store.HasProducts,
                    CategoriesAndSubcategories = categoryList.Select(c => new { c.IdCategory, c.Name, c.subcategory })
                });
            }

            return stores;
        }
    }
}
