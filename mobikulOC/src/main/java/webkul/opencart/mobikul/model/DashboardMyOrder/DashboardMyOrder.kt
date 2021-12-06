package webkul.opencart.mobikul.model.DashboardMyOrder

/**
 * Created by manish.choudhary on 15/7/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class DashboardMyOrder : BaseModel() {

    @SerializedName("orderData")
    @Expose
    var orderData: List<OrderDatum>? = null
    @SerializedName("orderTotals")
    @Expose
    var orderTotals: String? = null

}