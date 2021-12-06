package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class SellerDashboard : BaseModel() {

    @SerializedName("data")
    @Expose
    var data: Data? = null

}