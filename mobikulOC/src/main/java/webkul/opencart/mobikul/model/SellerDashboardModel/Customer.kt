package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_view")
    @Expose
    var textView: String? = null
    @SerializedName("percentage")
    @Expose
    var percentage: Int? = null
    @SerializedName("total")
    @Expose
    var total: Int? = null
}
