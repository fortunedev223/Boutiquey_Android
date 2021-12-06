package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 9/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OrderDetails {

    @SerializedName("totals")
    @Expose
    var totals: List<Total_>? = null
    @SerializedName("invoice_prefix")
    @Expose
    var invoicePrefix: String? = null
    @SerializedName("store_id")
    @Expose
    var storeId: Int? = null
    @SerializedName("store_name")
    @Expose
    var storeName: String? = null
    @SerializedName("store_url")
    @Expose
    var storeUrl: String? = null
    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
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
    @SerializedName("payment_firstname")
    @Expose
    var paymentFirstname: String? = null
    @SerializedName("payment_lastname")
    @Expose
    var paymentLastname: String? = null
    @SerializedName("payment_company")
    @Expose
    var paymentCompany: String? = null
    @SerializedName("payment_address_1")
    @Expose
    var paymentAddress1: String? = null
    @SerializedName("payment_address_2")
    @Expose
    var paymentAddress2: String? = null
    @SerializedName("payment_city")
    @Expose
    var paymentCity: String? = null
    @SerializedName("payment_postcode")
    @Expose
    var paymentPostcode: String? = null
    @SerializedName("payment_zone")
    @Expose
    var paymentZone: String? = null
    @SerializedName("payment_zone_id")
    @Expose
    var paymentZoneId: String? = null
    @SerializedName("payment_country")
    @Expose
    var paymentCountry: String? = null
    @SerializedName("payment_country_id")
    @Expose
    var paymentCountryId: String? = null
    @SerializedName("payment_address_format")
    @Expose
    var paymentAddressFormat: String? = null
    @SerializedName("payment_custom_field")
    @Expose
    var paymentCustomField: List<Any>? = null
    @SerializedName("payment_method")
    @Expose
    var paymentMethod: String? = null
    @SerializedName("payment_code")
    @Expose
    var paymentCode: String? = null
    @SerializedName("shipping_firstname")
    @Expose
    var shippingFirstname: String? = null
    @SerializedName("shipping_lastname")
    @Expose
    var shippingLastname: String? = null
    @SerializedName("shipping_company")
    @Expose
    var shippingCompany: String? = null
    @SerializedName("shipping_address_1")
    @Expose
    var shippingAddress1: String? = null
    @SerializedName("shipping_address_2")
    @Expose
    var shippingAddress2: String? = null
    @SerializedName("shipping_city")
    @Expose
    var shippingCity: String? = null
    @SerializedName("shipping_postcode")
    @Expose
    var shippingPostcode: String? = null
    @SerializedName("shipping_zone")
    @Expose
    var shippingZone: String? = null
    @SerializedName("shipping_zone_id")
    @Expose
    var shippingZoneId: String? = null
    @SerializedName("shipping_country")
    @Expose
    var shippingCountry: String? = null
    @SerializedName("shipping_country_id")
    @Expose
    var shippingCountryId: String? = null
    @SerializedName("shipping_address_format")
    @Expose
    var shippingAddressFormat: String? = null
    @SerializedName("shipping_custom_field")
    @Expose
    var shippingCustomField: List<Any>? = null
    @SerializedName("shipping_method")
    @Expose
    var shippingMethod: String? = null
    @SerializedName("shipping_code")
    @Expose
    var shippingCode: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null
    @SerializedName("vouchers")
    @Expose
    var vouchers: List<Any>? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("total")
    @Expose
    var total: Double = 0.toDouble()
    @SerializedName("affiliate_id")
    @Expose
    var affiliateId: Int? = null
    @SerializedName("commission")
    @Expose
    var commission: Int? = null
    @SerializedName("marketing_id")
    @Expose
    var marketingId: Int? = null
    @SerializedName("tracking")
    @Expose
    var tracking: String? = null
    @SerializedName("language_id")
    @Expose
    var languageId: String? = null
    @SerializedName("currency_id")
    @Expose
    var currencyId: String? = null
    @SerializedName("currency_code")
    @Expose
    var currencyCode: String? = null
    @SerializedName("currency_value")
    @Expose
    var currencyValue: String? = null
    @SerializedName("ip")
    @Expose
    var ip: String? = null
    @SerializedName("forwarded_ip")
    @Expose
    var forwardedIp: String? = null
    @SerializedName("user_agent")
    @Expose
    var userAgent: String? = null
    @SerializedName("accept_language")
    @Expose
    var acceptLanguage: String? = null

}