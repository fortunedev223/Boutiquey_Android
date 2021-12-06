package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chartdata {

    @SerializedName("order")
    @Expose
    var order: Order_? = null
    @SerializedName("customer")
    @Expose
    var customer: Customer_? = null
    @SerializedName("xaxis")
    @Expose
    var xaxis: List<List<String>>? = null
    @SerializedName("range")
    @Expose
    var range: String? = null
}
