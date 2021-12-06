package webkul.opencart.mobikul.model.ReturnOrderRequestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by manish.choudhary on 11/6/18.
 */
class ReturnReason {

    @SerializedName("return_reason_id")
    @Expose
    var returnReasonId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}