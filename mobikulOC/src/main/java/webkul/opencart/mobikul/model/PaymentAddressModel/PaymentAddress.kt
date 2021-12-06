package webkul.opencart.mobikul.model.PaymentAddressModel

/**
 * Created by manish.choudhary on 3/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class PaymentAddress : BaseModel() {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_checkout_option")
    @Expose
    var textCheckoutOption: String? = null
    @SerializedName("text_checkout_account")
    @Expose
    var textCheckoutAccount: String? = null
    @SerializedName("text_checkout_payment_address")
    @Expose
    var textCheckoutPaymentAddress: String? = null
    @SerializedName("text_checkout_shipping_address")
    @Expose
    var textCheckoutShippingAddress: String? = null
    @SerializedName("text_checkout_shipping_method")
    @Expose
    var textCheckoutShippingMethod: String? = null
    @SerializedName("text_checkout_payment_method")
    @Expose
    var textCheckoutPaymentMethod: String? = null
    @SerializedName("text_checkout_confirm")
    @Expose
    var textCheckoutConfirm: String? = null
    @SerializedName("shipping_required")
    @Expose
    var shippingRequired: Boolean? = null
    @SerializedName("paymentAddress")
    @Expose
    var paymentAddress: PaymentAddress_? = null
    @SerializedName("account")
    @Expose
    var account: String? = null
    @SerializedName("logged")
    @Expose
    var logged: String? = null
    @SerializedName("cartCount")
    @Expose
    var cartCount: String? = null
    @SerializedName("currency_code")
    @Expose
    var currencyCode: String? = null
}