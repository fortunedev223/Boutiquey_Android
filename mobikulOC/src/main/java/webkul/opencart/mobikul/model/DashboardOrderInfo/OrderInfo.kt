package webkul.opencart.mobikul.model.DashboardOrderInfo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class OrderInfo : BaseModel() {

    @SerializedName("gdpr_request_status")
    @Expose
    var gdprRequestStatus: Int? = 101
    @SerializedName("invoice_no")
    @Expose
    var invoiceNo: String? = null
    @SerializedName("delivery_code")
    @Expose
    var deliveryBoyCode: String? = ""
    @SerializedName("customer_shipping")
    @Expose
    var customerShipping: String? = ""
    @SerializedName("boy_name")
    @Expose
    var boyName: String? = ""
    @SerializedName("boy_image")
    @Expose
    var boyImage: String? = ""
    @SerializedName("boy_id")
    @Expose
    var boyId: String? = ""
    @SerializedName("boy_vehicle")
    @Expose
    var boyVehicle: String? = ""
    @SerializedName("boy_telephone")
    @Expose
    var boyTelephone: String? = ""
    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("payment_address")
    @Expose
    var paymentAddress: String? = null
    @SerializedName("payment_method")
    @Expose
    var paymentMethod: String? = null
    @SerializedName("shipping_address")
    @Expose
    var shippingAddress: String? = null
        get() = if (field != null) {
            field
        } else {
            null
        }
    @SerializedName("shipping_method")
    @Expose
    var shippingMethod: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null
    @SerializedName("vouchers")
    @Expose
    var vouchers: List<Any>? = null
    @SerializedName("totals")
    @Expose
    var totals: List<Total>? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("histories")
    @Expose
    var histories: List<History>? = null


}
