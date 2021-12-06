package webkul.opencart.mobikul.model.GetSellDataModel



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class GetSellData : BaseModel() {

    @SerializedName("data")
    @Expose
    var data: Data? = null

}
