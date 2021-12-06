package webkul.opencart.mobikul.model.CustomerLoginModel

/**
 * Created by manish.choudhary on 9/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class CustomerLogin : BaseModel() {


    @SerializedName("customer_id")
    @Expose
    var customerId: Int? = null

}
