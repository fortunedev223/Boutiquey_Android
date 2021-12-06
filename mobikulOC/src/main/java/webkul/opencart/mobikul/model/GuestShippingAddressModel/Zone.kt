package webkul.opencart.mobikul.model.GuestShippingAddressModel

/**
 * Created by manish.choudhary on 24/8/17.
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
