package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 9/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Total_ {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("value")
    @Expose
    var value: Double = 0.toDouble()
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null

}

