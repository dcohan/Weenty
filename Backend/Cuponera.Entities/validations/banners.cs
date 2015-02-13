using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cuponera.Entities
{
    [MetadataType(typeof(bannersMetadata))]
    public partial class banners
    {
        public string HomeBannerLink { get; set; }

        public string HomeBannerFile { get; set; }

        public string ListBannerLink { get; set; }

        public string ListBannerFile { get; set; }

        public int IdState { get; set; }
    }

    public class bannersMetadata
    {

    }
}
