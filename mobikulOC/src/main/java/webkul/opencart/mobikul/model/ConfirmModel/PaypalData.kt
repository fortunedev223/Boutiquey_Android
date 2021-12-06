package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 17/11/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaypalData {

    @SerializedName("tax")
    @Expose
    var tax: Double = 0.toDouble()
    @SerializedName("sub_total")
    @Expose
    var subTotal: Double = 0.toDouble()
    @SerializedName("shipping")
    @Expose
    var shipping: Double = 0.toDouble()
    @SerializedName("total")
    @Expose
    var total: Double = 0.toDouble()
    @SerializedName("discount")
    @Expose
    var discount: Double = 0.toDouble()

}
