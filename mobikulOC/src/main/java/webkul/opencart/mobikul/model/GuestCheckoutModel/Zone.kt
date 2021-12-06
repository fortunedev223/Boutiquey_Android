package webkul.opencart.mobikul.model.GuestCheckoutModel

/**
 * Created by manish.choudhary on 17/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Zone {

    @SerializedName("zone_id")
    @Expose
    var zoneId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

   }
