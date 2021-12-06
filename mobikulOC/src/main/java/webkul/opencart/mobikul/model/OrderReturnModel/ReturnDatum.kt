package webkul.opencart.mobikul.model.OrderReturnModel

/**
 * Created by manish.choudhary on 31/5/18.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReturnDatum {

    @SerializedName("return_id")
    @Expose
    var returnId: String? = null
    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null

}
