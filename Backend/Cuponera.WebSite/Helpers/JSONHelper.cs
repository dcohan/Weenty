using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Cuponera.WebSite.Helpers
{
    public class JSONHelper
    {
        public static string SerializeJSON(object unserializedData)
        {
            var jsonSerialiser = new System.Web.Script.Serialization.JavaScriptSerializer();
            var json = jsonSerialiser.Serialize(unserializedData);
            return json;
        }

    }
}