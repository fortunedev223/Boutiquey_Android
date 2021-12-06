package webkul.opencart.mobikul.model.SellerDashboardModel



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Order__ {

    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("customer")
    @Expose
    var customer: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = ""
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null

}
