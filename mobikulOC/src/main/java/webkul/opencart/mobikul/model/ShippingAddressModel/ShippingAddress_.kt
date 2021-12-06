package webkul.opencart.mobikul.model.ShippingAddressModel

/**
 * Created by manish.choudhary on 4/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShippingAddress_ {

    @SerializedName("text_address_existing")
    @Expose
    var textAddressExisting: String? = null
    @SerializedName("text_address_new")
    @Expose
    var textAddressNew: String? = null
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
    @SerializedName("address_id")
    @Expose
    var addressId: String? = null
    @SerializedName("addresses")
    @Expose
    var addresses: List<Address>? = null
    @SerializedName("postcode")
    @Expose
    var postcode: String? = null
    @SerializedName("country_id")
    @Expose
    var countryId: String? = null
    @SerializedName("zone_id")
    @Expose
    var zoneId: String? = null
    @SerializedName("countryData")
    @Expose
    var countryData: List<CountryDatum>? = null
    @SerializedName("custom_fields")
    @Expose
    var customFields: List<CustomField>? = null
    @SerializedName("shipping_address_custom_field")
    @Expose
    var shippingAddressCustomField: List<Any>? = null

    }
