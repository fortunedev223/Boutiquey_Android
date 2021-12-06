package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 9/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Continue {

    @SerializedName("text_recurring_item")
    @Expose
    var textRecurringItem: String? = null
    @SerializedName("text_payment_recurring")
    @Expose
    var textPaymentRecurring: String? = null
    @SerializedName("column_name")
    @Expose
    var columnName: String? = null
    @SerializedName("column_model")
    @Expose
    var columnModel: String? = null
    @SerializedName("column_quantity")
    @Expose
    var columnQuantity: String? = null
    @SerializedName("column_price")
    @Expose
    var columnPrice: String? = null
    @SerializedName("column_total")
    @Expose
    var columnTotal: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Any>? = null
    @SerializedName("vouchers")
    @Expose
    var vouchers: List<Any>? = null
    @SerializedName("totals")
    @Expose
    var totals: List<Total>? = null
    @SerializedName("order_details")
    @Expose
    var orderDetails: OrderDetails? = null
    @SerializedName("telr")
    @Expose
    var apgsenangpay: Apgsenangpay? = null
    @SerializedName("paypal_data")
    @Expose
    var paypalData: PaypalData? = null
    @SerializedName("order_id")
    @Expose
    var orderId: Int? = null
    @SerializedName("paytabs")
    @Expose
    var payTabs: PayTabs? = null

}