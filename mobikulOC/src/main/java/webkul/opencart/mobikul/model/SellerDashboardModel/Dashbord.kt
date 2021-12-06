package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Dashbord {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_sale")
    @Expose
    var textSale: String? = null
    @SerializedName("text_map")
    @Expose
    var textMap: String? = null
    @SerializedName("text_activity")
    @Expose
    var textActivity: String? = null
    @SerializedName("text_recent")
    @Expose
    var textRecent: String? = null

 }
