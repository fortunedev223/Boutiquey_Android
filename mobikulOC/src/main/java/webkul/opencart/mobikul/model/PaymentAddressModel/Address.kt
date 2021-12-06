package webkul.opencart.mobikul.model.PaymentAddressModel

/**
 * Created by manish.choudhary on 3/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Address {

    @SerializedName("address_id")
    @Expose
    var addressId: String? = null
    @SerializedName("formatted")
    @Expose
    var formatted: String? = null
    @SerializedName("address")
    @Expose
    var address: Address_? = null

}