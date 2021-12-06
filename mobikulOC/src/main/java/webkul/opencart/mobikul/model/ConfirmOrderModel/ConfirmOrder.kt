package webkul.opencart.mobikul.model.ConfirmOrderModel

/**
 * Created by manish.choudhary on 11/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ConfirmOrder : BaseModel() {

    @SerializedName("success")
    @Expose
    var success: Success? = null
}