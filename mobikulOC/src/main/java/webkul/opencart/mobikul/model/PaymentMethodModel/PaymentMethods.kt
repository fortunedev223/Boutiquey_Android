package webkul.opencart.mobikul.model.PaymentMethodModel

/**
 * Created by manish.choudhary on 4/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentMethods {

    @SerializedName("text_payment_method")
    @Expose
    var textPaymentMethod: String? = null
    @SerializedName("text_comments")
    @Expose
    var textComments: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null
    @SerializedName("error_warning")
    @Expose
    var errorWarning: String? = null
    @SerializedName("payment_methods")
    @Expose
    var paymentMethods: List<PaymentMethod_>? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("scripts")
    @Expose
    var scripts: List<Any>? = null
    @SerializedName("text_agree")
    @Expose
    var textAgree: String? = null
    @SerializedName("text_agree_info")
    @Expose
    var textAgreeInfo: String? = null
    @SerializedName("agree")
    @Expose
    var agree: String? = null
    @SerializedName("wk_wallet_system_status")
    @Expose
    var wkWalletSystemStatus: Boolean? = null
    @SerializedName("customer_login")
    @Expose
    var customerLogin: String? = null
    @SerializedName("wk_wallet_payment")
    @Expose
    var wkWalletPayment: Boolean? = null
    @SerializedName("text_use_wallet")
    @Expose
    var textUseWallet: String? = null
}
