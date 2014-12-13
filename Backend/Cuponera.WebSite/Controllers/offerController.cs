using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;
using System.Configuration;
using System.IO;

namespace Cuponera.WebSite.Controllers
{
    public class offerController : Controller
    {
        private CuponeraEntities db = new CuponeraEntities();

        // GET: /offer/
        public async Task<ActionResult> Index()
        {
            var offer = db.offer.Include(o => o.product);
            return View(await offer.ToListAsync());
        }

        // GET: /offer/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = await db.offer.FindAsync(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            return View(offer);
        }

        // GET: /offer/Create
        public ActionResult Create()
        {
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title");
            return View();
        }

        // POST: /offer/Create
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "IdOffer,Title,Active,StartDatetime,ExpirationDatetime,IdProduct,ImagePath, Price")] offer offer, List<HttpPostedFileBase> fileUpload)
        {
            var relativePath = ConfigurationManager.AppSettings["ImagePath"];
            var serverPath = ConfigurationManager.AppSettings["ServerPath"];

            if (ModelState.IsValid)
            {
                db.offer.Add(offer);
                
                // Handling Attachments - 
                bool firstItem = true;
                foreach (HttpPostedFileBase item in fileUpload)
                {
                    if (item != null)
                    {
                        var extension = System.IO.Path.GetExtension(item.FileName);
                        var fileName = Guid.NewGuid().ToString() + extension;
                        var phisicalPath = System.Web.HttpRuntime.AppDomainAppPath + relativePath + "\\" + fileName;
                        var virtualPath = serverPath + "/" + relativePath + "/" + fileName;
                        if (firstItem)
                        {
                            offer.ImagePath =  virtualPath;
                            firstItem = false;
                            
                            //Save Offer
                            db.SaveChanges();
                        }
                        else
                        {
                            images i = new images() { IdOffer = offer.IdOffer, ImagePath = virtualPath };
                            db.images.Add(i);
                            
                            //Save Images
                            db.SaveChanges();
                        }

                        //Save file
                        using (var fileStream = System.IO.File.Create(phisicalPath))
                        {
                            item.InputStream.CopyTo(fileStream);
                        }

                    }
                }

                if (firstItem)
                {
                    db.SaveChanges();
                }
                 
                
                return RedirectToAction("Index");
            }

            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // GET: /offer/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = await db.offer.FindAsync(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // POST: /offer/Edit/5
        // Para protegerse de ataques de publicación excesiva, habilite las propiedades específicas a las que desea enlazarse. Para obtener 
        // más información vea http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "IdOffer,Title,Active,StartDatetime,ExpirationDatetime,IdProduct,CreationDatetime,ModificationDatetime,DeletionDatetime,ImagePath, Price")] offer offer)
        {
            if (ModelState.IsValid)
            {
                db.Entry(offer).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            ViewBag.IdProduct = new SelectList(db.product, "IdProduct", "Title", offer.IdProduct);
            return View(offer);
        }

        // GET: /offer/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            offer offer = await db.offer.FindAsync(id);
            if (offer == null)
            {
                return HttpNotFound();
            }
            return View(offer);
        }

        // POST: /offer/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            offer offer = await db.offer.FindAsync(id);
            db.offer.Remove(offer);
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
