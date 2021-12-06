package webkul.opencart.mobikul.model.GuestShippingAddressModel

/**
 * Created by manish.choudhary on 24/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShippingAddress {

    @SerializedName("text_select")
    @Expose
    var textSelect: String? = null
    @SerializedName("text_none")
    @Expose
    var textNone: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("entry_firstname")
    @Expose
    var entryFirstname: String? = null
    @SerializedName("entry_lastname")
    @Expose
    var entryLastname: String? = null
    @SerializedName("entry_company")
    @Expose
    var entryCompany: String? = null
    @SerializedName("entry_address_1")
    @Expose
    var entryAddress1: String? = null
    @SerializedName("entry_address_2")
    @Expose
    var entryAddress2: String? = null
    @SerializedName("entry_postcode")
    @Expose
    var entryPostcode: String? = null
    @SerializedName("entry_city")
    @Expose
    var entryCity: String? = null
    @SerializedName("entry_country")
    @Expose
    var entryCountry: String? = null
    @SerializedName("entry_zone")
    @Expose
    var entryZone: String? = null
    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null
    @SerializedName("button_upload")
    @Expose
    var buttonUpload: String? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("company")
    @Expose
    var company: String? = null
    @SerializedName("address_1")
    @Expose
    var address1: String? = null
    @SerializedName("address_2")
    @Expose
    var address2: String? = null
    @SerializedName("postcode")
    @Expose
    var postcode: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("country_id")
    @Expose
    var countryId: String? = null
    @SerializedName("zone_id")
    @Expose
    var zoneId: String? = null
    @SerializedName("countryData")
    @Expose
    var countryData: List<CountryDatum>? = null
        get() = if (field != null) field else null
    @SerializedName("custom_fields")
    @Expose
    var customFields: List<Any>? = null
    @SerializedName("address_custom_field")
    @Expose
    var addressCustomField: List<Any>? = null

}
