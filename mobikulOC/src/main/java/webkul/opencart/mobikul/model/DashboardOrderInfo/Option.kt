package webkul.opencart.mobikul.model.DashboardOrderInfo

/**
 * Created by manish.choudhary on 2/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Option {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

}
