package webkul.opencart.mobikul.model.VIewCartModel

/**
 * Created by manish.choudhary on 14/7/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Voucher {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("entry_voucher")
    @Expose
    var entryVoucher: String? = null
    @SerializedName("button_voucher")
    @Expose
    var buttonVoucher: String? = null
    @SerializedName("voucher")
    @Expose
    var voucher: String? = null

}