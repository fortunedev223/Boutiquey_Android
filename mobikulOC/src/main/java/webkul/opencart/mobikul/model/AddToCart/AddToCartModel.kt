package webkul.opencart.mobikul.model.AddToCart

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class AddToCartModel : BaseModel() {

    @SerializedName("total")
    @Expose
    var total: String? = null

}