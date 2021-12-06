package webkul.opencart.mobikul.model.RegisterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryDatum {

    @SerializedName("country_id")
    @Expose
    var countryId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("zone")
    @Expose
    var zone: List<Zone>? = null

}
