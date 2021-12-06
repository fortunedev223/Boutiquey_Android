package webkul.opencart.mobikul.model.SellerOrderModel



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class SellerOrder : BaseModel() {

    @SerializedName("data")
    @Expose
    var data: Data? = null

}
