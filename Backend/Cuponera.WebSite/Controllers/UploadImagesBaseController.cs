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

    public class UploadImagesBaseController : Controller
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

        public void UploadImages(List<HttpPostedFileBase> fileUpload, int IdObject, bool skipFirst = true)
        {
            if (fileUpload.Count() == 0 || (fileUpload.Count() == 1 && skipFirst)) return;

            List<HttpPostedFileBase> finalFilesToUpload = fileUpload;
            if (skipFirst) finalFilesToUpload.Remove(finalFilesToUpload[0]);

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