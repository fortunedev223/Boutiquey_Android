package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Map {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_order")
    @Expose
    var textOrder: String? = null
    @SerializedName("text_sale")
    @Expose
    var textSale: String? = null

}
