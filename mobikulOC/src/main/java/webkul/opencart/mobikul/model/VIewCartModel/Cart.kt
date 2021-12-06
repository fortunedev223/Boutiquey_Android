package webkul.opencart.mobikul.model.VIewCartModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Cart {
    @SerializedName("guest_status")
    @Expose
    var guestStatus: Boolean? = false
    @SerializedName("voucher_status")
    @Expose
    var voucherStatus: Int? = 0
    @SerializedName("coupon_status")
    @Expose
    var couponStatus: Int? = 0
    @SerializedName("cart")
    @Expose
    var cartCount: Int? = 0
    @SerializedName("download_status")
    @Expose
    var downloadProduct: Int? = 0
    @SerializedName("error_warning")
    @Expose
    var errorWarning: String? = null
    @SerializedName("checkout")
    @Expose
    var checkout: Int? = null
    @SerializedName("attention")
    @Expose
    var attention: String? = null
    @SerializedName("success")
    @Expose
    var success: String? = null
    @SerializedName("action")
    @Expose
    var action: String? = null
    @SerializedName("weight")
    @Expose
    var weight: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null
    @SerializedName("vouchers")
    @Expose
    var vouchers: List<Any>? = null
    @SerializedName("totals")
    @Expose
    var totals: List<Total>? = null
    @SerializedName("total_products")
    @Expose
    var totalProducts: Int? = null
}