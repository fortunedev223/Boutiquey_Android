package webkul.opencart.mobikul.model.PaymentMethodModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentMethod_ {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("terms")
    @Expose
    var terms: String? = null
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null

}