package webkul.opencart.mobikul.model.RegisterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class RagisterData : BaseModel() {
    @SerializedName("text_becomepartner")
    @Expose
    var textBecomePartner: String? = null

    @SerializedName("text_shop")
    @Expose
    var textShop: String? = null

    @SerializedName("become_seller")
    @Expose
    var becomeSeller: Boolean? = false

    @SerializedName("agreeInfo")
    @Expose
    var agreeInfo: AgreeInfo? = null
    @SerializedName("customerGroup")
    @Expose
    var customerGroup: List<CustomerGroup>? = null
    @SerializedName("customField")
    @Expose
    var customField: List<Any>? = null
    @SerializedName("countryData")
    @Expose
    var countryData: List<CountryDatum>? = null
    @SerializedName("store_country_id")
    @Expose
    var storeCountryId: String? = null

}
