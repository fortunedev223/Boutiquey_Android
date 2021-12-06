package webkul.opencart.mobikul.model.AddCustomerModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class AddCustomer : BaseModel() {

    @SerializedName("customer_id")
    @Expose
    var customerId: Int? = null
    @SerializedName("partner")
    @Expose
    var partner: Int = 0


}