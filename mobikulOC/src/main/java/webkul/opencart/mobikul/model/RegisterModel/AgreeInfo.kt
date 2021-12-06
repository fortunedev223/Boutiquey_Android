package webkul.opencart.mobikul.model.RegisterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AgreeInfo {
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null
}
