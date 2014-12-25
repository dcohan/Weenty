using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Cuponera.Entities;

namespace Cuponera.WebSite.Controllers
{
    /// <summary>
    /// Wich classess need to implement UploadImagesController
    /// </summary>
    public enum UploadImagesEnum
    {
        offer,
        product,
        store,
        subscription
    }

    public abstract class UploadImagesBaseController : Controller
    {
        private static string _relativePath;
        private static string _serverPath;
        private UploadImagesEnum _definedType;
        
        protected CuponeraEntities db = new CuponeraEntities();

        public UploadImagesBaseController(UploadImagesEnum defType)
        {
            _relativePath = ConfigurationManager.AppSettings["ImagePath"];
            _serverPath = ConfigurationManager.AppSettings["ServerPath"];
            _definedType = defType;
        }

        public List<HttpPostedFileBase> FilterFiles(List<HttpPostedFileBase> fileUpload)
        {
            return fileUpload.Where(f => f != null && f.ContentType.Contains("image")).ToList();
        }

        public string GetRemainImageName(int IdObject)
        {
            IQueryable<images> images = db.images;
            switch (_definedType)
            {
                case UploadImagesEnum.offer:
                    break;
                case UploadImagesEnum.product:
                    images = images.Where(i => i.IdProduct == IdObject);
                    break;
                case UploadImagesEnum.store:                    
                    break;
            }

            if (images.Count() == 1)
            {
                return images.FirstOrDefault().ImagePath;
            }

            return null;
        }

        public void UploadImages(List<HttpPostedFileBase> fileUpload, int IdObject)
        {
            if (fileUpload.Count() == 0) return;

            List<HttpPostedFileBase> finalFilesToUpload = fileUpload;

            foreach (HttpPostedFileBase item in fileUpload)
            {
                if (item != null)
                {
                    //Save phisical File & generate virtual Path
                    var virtualPath = GeneratePhisicalFile(item);

                    images i=null;
                    switch (_definedType)
                    {
                        case UploadImagesEnum.offer:
                            i = new images() { IdOffer = IdObject, ImagePath = virtualPath };
                            break;
                        case UploadImagesEnum.product:
                            i = new images() { IdProduct = IdObject, ImagePath = virtualPath };
                            break;
                        case UploadImagesEnum.store:
                            i = new images() { IdStore = IdObject, ImagePath = virtualPath };
                            break;
                    }

                    //Save Images
                    db.images.Add(i);
                    db.SaveChanges();


                    
                }

            }
        }


        public string ChangeCoverImage(string exMainPath, string newMainPath, bool mustRemovePrevious, int idObject)
        {

            if (exMainPath == newMainPath)
            {
                return exMainPath;
            }

            if (!String.IsNullOrEmpty(newMainPath))
            {
                images exMain = null;
                IQueryable<images> newMainQuery = null;

                switch (_definedType)
                {
                    case UploadImagesEnum.product:
                        exMain = new images() { IdProduct = idObject, ImagePath = exMainPath };
                        newMainQuery = from i in db.images
                                        where i.ImagePath == newMainPath 
                                        && i.IdProduct == idObject
                                        select i;

                        break;

                    case UploadImagesEnum.offer:
                        exMain = new images() { IdOffer = idObject, ImagePath = exMainPath };
                        newMainQuery = from i in db.images
                                        where i.ImagePath == newMainPath 
                                        && i.IdOffer == idObject
                                        select i;
                        break;
                    case UploadImagesEnum.store:
                        exMain = new images() { IdStore = idObject, ImagePath = exMainPath };
                        newMainQuery = from i in db.images
                                        where i.ImagePath == newMainPath 
                                        && i.IdStore == idObject
                                        select i;

                        break;
                    default:
                        break;
                }


                if (!mustRemovePrevious && !String.IsNullOrEmpty(exMainPath) && exMain != null)
                {
                    //the previous main is one more image.
                    db.images.Add(exMain);
                }

                if (!String.IsNullOrEmpty(newMainPath))
                {
                    var toRemove = newMainQuery.FirstOrDefault();
                    if (toRemove != null)
                    {
                        db.images.Remove(toRemove);
                    }
                }

                db.SaveChanges();

                return newMainPath;
            }

            return null;
        }

        public void RemoveImages(string[] imagesToRemove)
        {
            foreach (string image_to_remove in imagesToRemove)
            {
                if (image_to_remove == "main" || string.IsNullOrEmpty(image_to_remove))
                {
                    continue;
                }
                int current_image_to_remove = Convert.ToInt32(image_to_remove);
                var image = db.images.Where(i => i.IdImage == current_image_to_remove);
                db.images.Remove(image.FirstOrDefault());
            }

            db.SaveChanges();
        }

        /// <summary>
        /// Save phisically a file 
        /// </summary>
        /// <param name="input">Posted file to be saved</param>
        /// <returns>dynamically virtual path for generated file</returns>
        public string GeneratePhisicalFile(HttpPostedFileBase input)
        {
            var extension = System.IO.Path.GetExtension(input.FileName);
            var fileName = Guid.NewGuid().ToString() + extension;
            var phisicalPath = System.Web.HttpRuntime.AppDomainAppPath + _relativePath + "\\" + fileName;

            //Save file
            using (var fileStream = System.IO.File.Create(phisicalPath))
            {
                input.InputStream.CopyTo(fileStream);
            }

            //Return virtual path generated
            return _serverPath + "/" + _relativePath + "/" + fileName;
        }
    }
}