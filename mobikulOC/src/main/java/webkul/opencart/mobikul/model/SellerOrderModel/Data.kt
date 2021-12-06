package webkul.opencart.mobikul.model.SellerOrderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("chkIsPartner")
    @Expose
    var chkIsPartner: Boolean? = null
    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_orderid")
    @Expose
    var textOrderid: String? = null
    @SerializedName("text_added_date")
    @Expose
    var textAddedDate: String? = null
    @SerializedName("text_products")
    @Expose
    var textProducts: String? = null
    @SerializedName("text_customer")
    @Expose
    var textCustomer: String? = null
    @SerializedName("text_total")
    @Expose
    var textTotal: String? = null
    @SerializedName("text_status")
    @Expose
    var textStatus: String? = null
    @SerializedName("text_action")
    @Expose
    var textAction: String? = null
    @SerializedName("text_no_results")
    @Expose
    var textNoResults: String? = null
    @SerializedName("order_statuses")
    @Expose
    var orderStatuses: List<OrderStatus>? = null
    @SerializedName("orderTotals")
    @Expose
    var orderTotals: Int? = null
    @SerializedName("orders")
    @Expose
    var orders: List<Order>? = null
}