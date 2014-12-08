﻿using System;
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
        public string PTitle {get;set;}
        public string PImagePath {get;set;}
        public string PDescription {get;set;}

        public string OTitle {get;set;}
        public string OImagePath { get; set; }

        public double Distance { get; set; }
    }

    public partial class CuponeraEntities 
    {
        public List<GetProductAndOffers> GetProductAndOffers(int IdStore, int IdCategoria, double Latittud, double Longitude)
        {
            return Database.SqlQuery<GetProductAndOffers>(
                    "dbo.GetProductAndOffers @IdStore, @IdCategoria, @Latittud, @Longitude",
                    new SqlParameter("IdStore", IdStore),
                    new SqlParameter("IdCategoria", IdCategoria),
                    new SqlParameter("Latittud", Latittud),
                    new SqlParameter("Longitude", Longitude)
                ).ToList<GetProductAndOffers>();
        }
    }
}
