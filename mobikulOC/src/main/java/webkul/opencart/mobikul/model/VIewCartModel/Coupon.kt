package webkul.opencart.mobikul.model.VIewCartModel

/**
 * Created by manish.choudhary on 14/7/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coupon {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("entry_coupon")
    @Expose
    var entryCoupon: String? = null
    @SerializedName("button_coupon")
    @Expose
    var buttonCoupon: String? = null
    @SerializedName("coupon")
    @Expose
    var coupon: String? = null
}