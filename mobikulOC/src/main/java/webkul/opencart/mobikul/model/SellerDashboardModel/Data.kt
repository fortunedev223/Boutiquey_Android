package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("chkIsPartner")
    @Expose
    var chkIsPartner: Boolean? = null
    @SerializedName("dashbord")
    @Expose
    var dashbord: Dashbord? = null
    @SerializedName("order")
    @Expose
    var order: Order? = null
    @SerializedName("sale")
    @Expose
    var sale: Sale? = null
    @SerializedName("customer")
    @Expose
    var customer: Customer? = null
    @SerializedName("map")
    @Expose
    var map: Map? = null
    @SerializedName("mapdata")
    @Expose
    var mapdata: List<Mapdatum>? = null
    @SerializedName("chart")
    @Expose
    var chart: Chart? = null
    @SerializedName("chartdata")
    @Expose
    var chartdata: Chartdata? = null
    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_no_results")
    @Expose
    var textNoResults: String? = null
    @SerializedName("column_order_id")
    @Expose
    var columnOrderId: String? = null
    @SerializedName("column_customer")
    @Expose
    var columnCustomer: String? = null
    @SerializedName("column_status")
    @Expose
    var columnStatus: String? = null
    @SerializedName("column_date_added")
    @Expose
    var columnDateAdded: String? = null
    @SerializedName("column_total")
    @Expose
    var columnTotal: String? = null
    @SerializedName("column_action")
    @Expose
    var columnAction: String? = null
    @SerializedName("button_view")
    @Expose
    var buttonView: String? = null
    @SerializedName("orders")
    @Expose
    var orders: List<Order__>? = null
}
