package webkul.opencart.mobikul.model.GuestCheckoutModel

/**
 * Created by manish.choudhary on 17/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Guest {

    @SerializedName("text_select")
    @Expose
    var textSelect: String? = null
    @SerializedName("text_none")
    @Expose
    var textNone: String? = null
    @SerializedName("text_your_details")
    @Expose
    var textYourDetails: String? = null
    @SerializedName("text_your_account")
    @Expose
    var textYourAccount: String? = null
    @SerializedName("text_your_address")
    @Expose
    var textYourAddress: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("entry_firstname")
    @Expose
    var entryFirstname: String? = null
    @SerializedName("entry_lastname")
    @Expose
    var entryLastname: String? = null
    @SerializedName("entry_email")
    @Expose
    var entryEmail: String? = null
    @SerializedName("entry_telephone")
    @Expose
    var entryTelephone: String? = null
    @SerializedName("entry_fax")
    @Expose
    var entryFax: String? = null
    @SerializedName("entry_company")
    @Expose
    var entryCompany: String? = null
    @SerializedName("entry_customer_group")
    @Expose
    var entryCustomerGroup: String? = null
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
    @SerializedName("entry_shipping")
    @Expose
    var entryShipping: String? = null
    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null
    @SerializedName("button_upload")
    @Expose
    var buttonUpload: String? = null
    @SerializedName("customer_groups")
    @Expose
    var customerGroups: List<CustomerGroup>? = null
    @SerializedName("customer_group_id")
    @Expose
    var customerGroupId: String? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("telephone")
    @Expose
    var telephone: String? = null
    @SerializedName("fax")
    @Expose
    var fax: String? = null
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
    @SerializedName("custom_fields")
    @Expose
    var customFields: List<CustomField>? = null
    @SerializedName("guest_custom_field")
    @Expose
    var guestCustomField: List<Any>? = null
    @SerializedName("shipping_required")
    @Expose
    var shippingRequired: Boolean? = null
    @SerializedName("shipping_address")
    @Expose
    var shippingAddress: Boolean? = null

  }
