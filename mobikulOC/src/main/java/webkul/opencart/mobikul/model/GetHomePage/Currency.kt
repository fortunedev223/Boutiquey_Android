package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Currency {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null

}