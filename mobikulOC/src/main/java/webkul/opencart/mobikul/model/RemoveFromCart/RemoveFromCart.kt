package webkul.opencart.mobikul.model.RemoveFromCart

/**
 * Created by manish.choudhary on 15/7/17.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class RemoveFromCart : BaseModel() {

    @SerializedName("total")
    @Expose
    var total: String? = null

}
