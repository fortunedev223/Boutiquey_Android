package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 17/11/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Apgsenangpay {

    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("success")
    @Expose
    var success: String? = null
    @SerializedName("cancel")
    @Expose
    var cancel: String? = null

}
