package webkul.opencart.mobikul.model.GetAddressModel

/**
 * Created by manish.choudhary on 17/10/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressDatum {

    @SerializedName("address_id")
    @Expose
    var addressId: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

}