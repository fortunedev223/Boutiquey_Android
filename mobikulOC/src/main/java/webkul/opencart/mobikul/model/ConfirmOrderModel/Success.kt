package webkul.opencart.mobikul.model.ConfirmOrderModel

/**
 * Created by manish.choudhary on 11/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Success {

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



