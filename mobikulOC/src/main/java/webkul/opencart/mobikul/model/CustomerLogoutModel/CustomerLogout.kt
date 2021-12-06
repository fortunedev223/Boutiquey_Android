package webkul.opencart.mobikul.model.CustomerLogoutModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class CustomerLogout {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_message")
    @Expose
    var textMessage: String? = null
    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null

}


