package webkul.opencart.mobikul.model.BaseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseModel {

    @SerializedName("fault")
    @Expose
    var fault = 0

    @SerializedName("message")
    @Expose
    var message: String = ""

    @SerializedName("error")
    @Expose
    var error = 0

    @SerializedName("redirect")
    @Expose
    var redirect: String? = ""

}
