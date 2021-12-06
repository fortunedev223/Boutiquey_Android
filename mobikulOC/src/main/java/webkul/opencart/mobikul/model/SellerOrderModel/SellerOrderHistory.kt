package webkul.opencart.mobikul.model.SellerOrderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

class SellerOrderHistory : BaseModel() {

    @SerializedName("data")
    @Expose
    var data: DataSellerHistory? = null

}