package webkul.opencart.mobikul.model.ShippingMethodModel

/**
 * Created by manish.choudhary on 4/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShippingMethods {

    @SerializedName("text_shipping_method")
    @Expose
    var textShippingMethod: String? = null
    @SerializedName("text_comments")
    @Expose
    var textComments: String? = null
    @SerializedName("gdpr_status")
    @Expose
    var gdprStatus: Int? = 101


    @SerializedName("gdpr_content")
    @Expose
    var gdprContent: String? = null

    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null

    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null
    @SerializedName("error_warning")
    @Expose
    var errorWarning: String? = null
    @SerializedName("shipping_methods")
    @Expose
    var shippingMethods: List<ShippingMethod_>? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null

}
