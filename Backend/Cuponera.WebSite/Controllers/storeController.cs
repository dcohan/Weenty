using System;
using System.Collections.Generic;
using System.Collections;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;
using System.Configuration;
using Cuponera.WebSite.Models;
using System.Threading;
using Cuponera.WebSite.Helpers;

namespace Cuponera.WebSite.Controllers
{
    public class storeController : UploadImagesBaseController
    {
        private CuponeraEntities db = new CuponeraEntities();


        public storeController()
            : base(UploadImagesEnum.store)
        {

        }

        [Authorize]
        public void GetCompanies(store store = null)
        {
            IQueryable<company> companies = db.company;

            if (store == null || (store != null && store.company != null && store.company.DeletionDatetime == null))
            {
                companies = companies.Where(s => s.DeletionDatetime == null);
            }
            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                companies = db.store.Where(s => CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore) &&
                                                     s.DeletionDatetime == null).Select(s => s.company);
            }

            companies = companies.OrderBy(c => c.Name);

            ViewBag.Companies = companies;
            ViewBag.IdCompany = new SelectList(companies, "IdCompany", "Name", store != null ? store.IdCompany : 0);
        }

        public void GetStates(store store = null)
        {
            IQueryable<state> states = db.state;
            if (store == null || (store != null && store.state != null && store.state.DeletionDatetime == null))
            {
                states = states.Where(s => s.DeletionDatetime == null);
            }

            states = states.OrderBy(s => s.Name);

            ViewBag.IdState = new SelectList(states, "IdState", "Name", store != null ? store.IdState : 0);
            ViewBag.States = states;

        }

        public void GetCategories(store store = null)
        {
            IQueryable<storeCategory> categoriesAndSubcategories = db.storeCategory.Where(sc => sc.DeletionDatetime == null);
            ArrayList categories = new ArrayList();

            foreach (category category in db.category.Where(c => c.DeletionDatetime == null).OrderBy(c => c.Name))
            {
                categories.Add(new { Id = "C-" + category.IdCategory, Name = category.Name, Category = category.Name });

                foreach (subcategory subcategory in db.subcategory.Where(c => c.DeletionDatetime == null && c.IdCategory.Equals(category.IdCategory)).OrderBy(s => s.Name))
                {
                    
                    categories.Add(new { Id = "S-" + subcategory.IdSubCategory, Name = "      " + subcategory.category.Name + " - " + subcategory.Name, Category = category.Name });
                }
            }


            ViewBag.Categories = categories;
            ViewBag.IdCategory = new SelectList(categories, "Id", "Name", "Category", 1);
        }


        [AuthorizeUserStoreAttribute]
        private IEnumerable<store> get(bool all, int idCompany, string name, string zipCode, int idState, int pageNumber)
        {
            IQueryable<store> stores = db.store;
            if (!all)
            {
                stores = stores.Where(s => !s.DeletionDatetime.HasValue && !s.company.DeletionDatetime.HasValue);
            }

            foreach (var store in stores)
            {
                if (store.company.DeletionDatetime != null)
                {
                    store.DeletionDatetime = store.company.DeletionDatetime;
                }
            }

            if (idCompany > 0)
            {
                stores = stores.Where(s => s.company.IdCompany == idCompany);
            }

            if (name != null)
            {
                stores = stores.Where(s => s.Name.Contains(name));
            }

            if (zipCode != null)
            {
                stores = stores.Where(s => s.ZipCode == zipCode);
            }

            if (idState > 0)
            {
                stores = stores.Where(s => s.state.IdState == idState);
            }

            if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
            {
                if (CuponeraIdentity.AdminCompany > 0)
                {
                    stores = stores.Where(s => CuponeraIdentity.AdminCompany == s.IdCompany);
                }
                else
                {
                    stores = stores.Where(s => CuponeraIdentity.CurrentAvaiableStores.Contains(s.IdStore));
                }
            }

            stores = stores.OrderBy(s => s.Name);

            int pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["ElementsPerPage"]);
            ViewBag.Pages = Convert.ToInt32(Math.Ceiling((double)stores.Count() / pageSize));

            int elemsToSkip = pageSize * (pageNumber - 1);
            return stores.Skip(elemsToSkip).Take(pageSize);
        }

        [Authorize]
        public string GetAllBasicData()
        {
            Cuponera.Backend.Controllers.categoryController cb = new Backend.Controllers.categoryController();
            IEnumerable<category> categories = cb.Getcategory(false);

            return Helpers.JSONHelper.SerializeJSON(categories.ToList().Select(category => new { id = category.IdCategory, name = category.Name }));
        }

        [AuthorizeUserStoreAttribute]

        // GET: store
        public async Task<ActionResult> Index(bool all = false, int company = 0, string name = null, string zipCode = null, int state = 0, int page = 1)
        {
            var stores = get(all, company, name, zipCode, state, page);

            ViewBag.CanSelectCompany = true;
            return View(stores);
        }

        [AuthorizeUserStoreAttribute]
        // GET: store/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            var state = db.state.Where(s => s.IdState == store.IdState);
            if (state != null && state.ToList().Count() > 0) { ViewBag.StateName = state.FirstOrDefault().Name; }

            var company = db.company.Where(s => s.IdCompany == store.IdCompany);
            if (company != null && company.ToList().Count() > 0) { ViewBag.CompanyName = company.FirstOrDefault().Name; }

            if (store.Latitude != null) { ViewBag.Latitude = store.Latitude.ToString().Replace(",", "."); }
            if (store.Longitude != null) { ViewBag.Longitude = store.Longitude.ToString().Replace(",", "."); }

            GetCompanies(store);
            GetCategories(store);
            return View(store);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: store/Create
        public ActionResult Create()
        {
            IEnumerable<state> states = (new stateController()).get(false);
            GetStates();
            GetCompanies();
            GetCategories();
            return View();
        }

        private List<string> GetNonRepeatedCategories(string categories)
        {
            List<string> categoriesList = new List<string>();

            string[] _categories = categories.Split(new char[] { ',' });
            foreach (var _category in _categories)
            {
                if (!categoriesList.Contains(_category))
                {
                    categoriesList.Add(_category);
                }
            }

            return categoriesList;
                
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // POST: store/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Name,Address,ContactNumber,ZipCode,IdState,IdCompany,StoreHours,Email,FacebookUrl,WhatsApp,Description,WebPage")] store store, string Latitude, string Longitude, List<HttpPostedFileBase> fileUpload, string selectedCategories)
        {
            if (ModelState.IsValid)
            {
                var categories = GetNonRepeatedCategories(selectedCategories);

                if (Latitude != null) { store.Latitude = Convert.ToDouble(Latitude.Replace(".", ",")); }
                if (Longitude != null) { store.Longitude = Convert.ToDouble(Longitude.Replace(".", ",")); }

                fileUpload = FilterFiles(fileUpload);
                string imagePath = null;
                HttpPostedFileBase fileImagePath;
                if (fileUpload.Count() == 1)
                {
                    fileImagePath = fileUpload.FirstOrDefault();
                    fileUpload.RemoveAt(0);
                    imagePath = GeneratePhisicalFile(fileImagePath);
                }

                store.ImagePath = imagePath;

                if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
                {
                    var stores = db.userCompany.Where(uc => uc.IdUser == CuponeraIdentity.CurrentUserId && uc.IdCompany == store.IdCompany);
                    if (stores.Count() == 0)
                    {
                        return View(store);
                    }
                }

                db.store.Add(store);
                db.SaveChanges();

                InsertCategoriesFromStore(categories, store);

                //Save aditional images
                UploadImages(fileUpload, store.IdStore);
                return RedirectToAction("Index");
            }

            GetCompanies(store);
            GetCategories(store);
            return View(store);
        }

        private void InsertCategoriesFromStore(List<string> categories, store store){
            IQueryable<storeCategory> sc;
            foreach (var category in db.storeCategory.Where(scs => scs.IdStore == store.IdStore))
            {
                category.DeletionDatetime = DateTime.Now;
            }


            // TERMINAR LA INSERCIÓN.
            foreach (var category in categories)
            {
                if (category.Contains("C-"))
                {
                    //is a category
                    int id = Convert.ToInt32(category.Split(new char[] { 'C', '-' })[2]);

                    sc = db.storeCategory.Where(scs => scs.IdCategory == id && scs.IdStore == store.IdStore);
                    if (sc.Count() == 0)
                    {
                        db.storeCategory.Add(new storeCategory()
                        {
                            IdStore = store.IdStore,
                            IdCategory = Convert.ToInt32(id)
                        });
                    }
                    else
                    {
                        sc.FirstOrDefault().DeletionDatetime = null;
                    }
                }
                else
                {
                    //is a subcategory
                    int id = Convert.ToInt32(category.Split(new char[] { 'S', '-' })[2]);
                    sc = db.storeCategory.Where(scs => scs.IdCategory == id && scs.IdStore == store.IdStore);

                    if (sc.Count() == 0)
                    {
                        db.storeCategory.Add(new storeCategory()
                        {
                            IdStore = store.IdStore,
                            IdSubCategory = Convert.ToInt32(id)
                        });

                    }
                    else
                    {
                        sc.FirstOrDefault().DeletionDatetime = null;
                    }

                }
            }

            try
            {
                db.SaveChanges();
            }
            catch (Exception e) { }
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: store/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }





            if (store.Latitude != null) { ViewBag.Latitude = store.Latitude.ToString().Replace(",", "."); }
            if (store.Longitude != null) { ViewBag.Longitude = store.Longitude.ToString().Replace(",", "."); }

            GetStates(store);
            GetCompanies(store);
            GetCategories(store);

            return View(store);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // POST: store/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdStore,Name,Address,ContactNumber,ZipCode,IdState,StoreHours,Email,FacebookUrl,WhatsApp,Description,WebPage,IdCompany")] store store, string Latitude, string Longitude, List<HttpPostedFileBase> fileUpload, string imagesToRemove, string ImagePath)
        {
            if (ModelState.IsValid)
            {

                fileUpload = FilterFiles(fileUpload);

                HttpPostedFileBase fileImagePath;
                if (String.IsNullOrEmpty(ImagePath) && fileUpload.Count() == 1)
                {
                    fileImagePath = fileUpload.FirstOrDefault();
                    fileUpload.RemoveAt(0);
                    ImagePath = GeneratePhisicalFile(fileImagePath);
                }

                string previousImagePath = db.store.Where(s => s.IdStore == store.IdStore).Select(s => s.ImagePath).FirstOrDefault();

                string[] images_to_remove = imagesToRemove.Split(new Char[] { ',' });
                RemoveImages(images_to_remove);

                string remainingImagePath = GetRemainImageName(store.IdStore);
                if (!string.IsNullOrEmpty(remainingImagePath))
                {
                    ImagePath = remainingImagePath;
                }

                store.ImagePath = ChangeCoverImage(previousImagePath, ImagePath, images_to_remove.Contains("main"), store.IdStore);



                if (Latitude != null) { store.Latitude = Convert.ToDouble(Latitude.Replace(".", ",")); }
                if (Longitude != null) { store.Longitude = Convert.ToDouble(Longitude.Replace(".", ",")); }

                if (!new CuponeraPrincipal(new CuponeraIdentity(User.Identity)).IsInRole("admin"))
                {
                    var stores = db.userCompany.Where(uc => uc.IdUser == CuponeraIdentity.CurrentUserId && uc.IdCompany == store.IdCompany);
                    if (stores.Count() == 0)
                    {
                        return View(store);
                    }
                }



                db.Entry(store).State = EntityState.Modified;
                store.ModificationDatetime = DateTime.Now;

                db.SaveChanges();
                //Save aditional images
                UploadImages(fileUpload, store.IdStore);

                return RedirectToAction("Index");
            }
            GetCompanies(store);
            GetCategories(store);
            return View(store);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: store/Delete/5
        public async Task<ActionResult> Delete(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            store.DeletionDatetime = DateTime.Now;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // GET: store/Activate/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Activate(int id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            store store = await db.store.FindAsync(id);
            if (store == null)
            {
                return HttpNotFound();
            }

            store.DeletionDatetime = null;
            await db.SaveChangesAsync();


            return new HttpStatusCodeResult(HttpStatusCode.OK);
        }

        [AuthorizeUserStoreAttribute(MustBeCompanyAdmin = true)]
        // POST: store/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            category category = await db.category.FindAsync(id);
            db.category.Remove(category);
            await db.SaveChangesAsync();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
