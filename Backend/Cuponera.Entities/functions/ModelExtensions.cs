using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Core.Objects;
using System.Data.Entity.Infrastructure;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    public class GetProductAndOffers
    {
        public int IdProduct { get; set; }
        public string PTitle { get; set; }
        public string PImagePath { get; set; }
        public string PDescription { get; set; }
        public double PPrice { get; set; }

        public Nullable<int> IdOffer { get; set; }
        public string OTitle { get; set; }
        public string OImagePath { get; set; }

        public string ODescription { get; set; }

        public Nullable<double> OPrice { get; set; }

    }

    public class GetNearestStores : store
    {
        public double Distance { get; set; }
        public int HasOffers { get; set; }

        public int IdCategory { get; set; }
    }

    public partial class CuponeraEntities
    {
        public List<GetProductAndOffers> GetProductAndOffers(int IdStore, int IdCategoria)
        {
            return Database.SqlQuery<GetProductAndOffers>(
                    "dbo.GetProductAndOffers @IdStore, @IdCategoria",
                    new SqlParameter("IdStore", IdStore),
                    new SqlParameter("IdCategoria", IdCategoria)
                ).ToList<GetProductAndOffers>();
        }

        public List<GetNearestStores> GetNearestStores(int IdCategory, double Latitude, double Longitude)
        {
            return Database.SqlQuery<GetNearestStores>(
                    "dbo.GetNearestStores @IdCategory, @Latitude, @Longitude",
                    new SqlParameter("IdCategory", IdCategory),
                    new SqlParameter("Latitude", Latitude),
                    new SqlParameter("Longitude", Longitude)
                ).ToList<GetNearestStores>();
        }

        public List<GetNearestStores> GetNearestStoresByName(int IdCategory, double Latitude, double Longitude, String Name)
        {
            return Database.SqlQuery<GetNearestStores>(
                    "dbo.GetNearestStoresByName @IdCategory, @Latitude, @Longitude, @Name",
                    new SqlParameter("IdCategory", IdCategory),
                    new SqlParameter("Latitude", Latitude),
                    new SqlParameter("Longitude", Longitude),
                    new SqlParameter("Name", Name)
                ).ToList<GetNearestStores>();
        }

        public List<GetNearestStores> GetNearestStoresWithOffers(double Latitude, double Longitude)
        {
            return Database.SqlQuery<GetNearestStores>(
                    "dbo.GetNearestStoresWithOffers @Latitude, @Longitude",
                    new SqlParameter("Latitude", Latitude),
                    new SqlParameter("Longitude", Longitude)
                ).ToList<GetNearestStores>();
        }
    }
}
