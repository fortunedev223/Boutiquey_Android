package webkul.opencart.mobikul.model.MyWalletModel

/**
 * Created by manish.choudhary on 14/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Credit {

    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("amount")
    @Expose
    var amount: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("desc")
    @Expose
    var desc: String? = null
}
