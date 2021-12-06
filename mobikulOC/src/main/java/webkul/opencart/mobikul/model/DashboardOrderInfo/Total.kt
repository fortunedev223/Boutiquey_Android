package webkul.opencart.mobikul.model.DashboardOrderInfo

/**
 * Created by manish.choudhary on 2/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Total {

    @SerializedName("title")
    @Expose
    var title: String? = null
        get() = if (field != null) {
            field
        } else {
            null
        }
    @SerializedName("text")
    @Expose
    var text: String? = null

}
