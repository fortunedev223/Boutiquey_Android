package webkul.opencart.mobikul.model.SellerOrderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Total {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
}