package webkul.opencart.mobikul.model.GuestCheckoutModel

/**
 * Created by manish.choudhary on 17/8/17.
 */
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
