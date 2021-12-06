package webkul.opencart.mobikul.model.LoginModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class LoginModel : BaseModel() {

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
    @SerializedName("partner")
    @Expose
    var partner: Int? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("store_id")
    @Expose
    var storeId: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("cart_total")
    @Expose
    var cartTotal: String? = null
    @SerializedName("wallet_balance")
    @Expose
    var walletBalance: String? = null

   }