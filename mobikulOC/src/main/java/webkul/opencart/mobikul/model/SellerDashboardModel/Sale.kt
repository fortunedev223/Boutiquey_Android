package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sale {

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
    var total: String? = null
    @SerializedName("admin_amount")
    @Expose
    var adminAmount: String? = null
    @SerializedName("seller_amount")
    @Expose
    var sellerAmount: String? = null
    @SerializedName("payable_amount")
    @Expose
    var payableAmount: Int? = null
    @SerializedName("paid_amount")
    @Expose
    var paidAmount: String? = null
}
