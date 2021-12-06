package webkul.opencart.mobikul.model.VIewCartModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ViewCart : BaseModel() {
    @SerializedName("cart")
    @Expose
    var cart: Cart? = null
    @SerializedName("coupon")
    @Expose
    var coupon: Coupon? = null

    @SerializedName("voucher")
    @Expose
    var voucher: Voucher? = null
    @SerializedName("reward")
    @Expose
    var reward: Reward_? = null
    @SerializedName("shipping")
    @Expose
    var shipping: String? = null
}
