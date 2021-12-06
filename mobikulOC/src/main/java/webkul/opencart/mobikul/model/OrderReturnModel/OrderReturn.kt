package webkul.opencart.mobikul.model.OrderReturnModel

import webkul.opencart.mobikul.model.BaseModel.BaseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by manish.choudhary on 31/5/18.
 */

class OrderReturn : BaseModel() {

    @SerializedName("returnData")
    @Expose
    var returnData: List<ReturnDatum>? = null
    @SerializedName("returnTotals")
    @Expose
    var returnTotals: String? = null

}
