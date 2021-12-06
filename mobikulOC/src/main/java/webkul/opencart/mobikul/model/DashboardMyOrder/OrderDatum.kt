package webkul.opencart.mobikul.model.DashboardMyOrder

/**
 * Created by manish.choudhary on 15/7/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderDatum {

    @SerializedName("orderId")
    @Expose
    var orderId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("dateAdded")
    @Expose
    var dateAdded: String? = null
    @SerializedName("products")
    @Expose
    var products: Int? = null
    @SerializedName("total")
    @Expose
    var total: String? = null

}