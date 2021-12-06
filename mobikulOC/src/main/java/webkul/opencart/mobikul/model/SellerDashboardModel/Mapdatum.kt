package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Mapdatum {

    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("amount")
    @Expose
    var amount: String? = null

}
