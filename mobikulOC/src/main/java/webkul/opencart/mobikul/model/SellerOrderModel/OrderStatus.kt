package webkul.opencart.mobikul.model.SellerOrderModel



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderStatus {

    @SerializedName("order_status_id")
    @Expose
    var orderStatusId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
