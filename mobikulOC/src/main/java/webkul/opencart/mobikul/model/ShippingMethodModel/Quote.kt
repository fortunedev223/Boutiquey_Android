package webkul.opencart.mobikul.model.ShippingMethodModel

/**
 * Created by manish.choudhary on 4/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Quote {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("cost")
    @Expose
    var cost: Double = 0.toDouble()
        private set
    @SerializedName("tax_class_id")
    @Expose
    var taxClassId: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
}
