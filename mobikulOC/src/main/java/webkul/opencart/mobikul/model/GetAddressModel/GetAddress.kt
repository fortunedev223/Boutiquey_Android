package webkul.opencart.mobikul.model.GetAddressModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel


class GetAddress : BaseModel() {

    @SerializedName("addressData")
    @Expose
    var addressData: List<AddressDatum>? = null
    @SerializedName("default")
    @Expose
    var default: String? = null
}
