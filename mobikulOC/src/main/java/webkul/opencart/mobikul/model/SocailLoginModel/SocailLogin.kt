package webkul.opencart.mobikul.model.SocailLoginModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class SocailLogin : BaseModel() {

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
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

}

