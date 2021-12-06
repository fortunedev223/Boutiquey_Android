package webkul.opencart.mobikul.model.EditAddressBookModel

/**
 * Created by manish.choudhary on 7/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class EditAddressbook : BaseModel() {


    @SerializedName("gdpr_status")
    @Expose
    var gdprStatus: Int? = 101

    @SerializedName("gdpr_content")
    @Expose
    var gdprContent: String? = null

    @SerializedName("gdpr_request_status")
    @Expose
    var gdprRequestStatus: Int? = 101

    @SerializedName("data")
    @Expose
    var data: Data? = null
    @SerializedName("store_country_id")
    @Expose
    var countryId: String? = null

    @SerializedName("default")
    @Expose
    var default: Int? = null
    @SerializedName("countryData")
    @Expose
    var countryData: List<CountryDatum>? = null

}
