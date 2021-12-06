package webkul.opencart.mobikul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ApiLoginModel : BaseModel() {

    @SerializedName("wk_token")
    @Expose
    var wkToken: String? = null
    @SerializedName("language")
    @Expose
    var language: String? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null

}



