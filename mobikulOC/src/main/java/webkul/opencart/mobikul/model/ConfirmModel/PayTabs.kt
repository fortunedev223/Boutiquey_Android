package webkul.opencart.mobikul.model.ConfirmModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PayTabs {

    @SerializedName("pt_merchant_email")
    @Expose
    private var ptMerchantEmail: String? = null
    @SerializedName("pt_secret_key")
    @Expose
    private var ptSecretKey: String? = null
    @SerializedName("pt_transaction_title")
    @Expose
    private var ptTransactionTitle: String? = null
    @SerializedName("pt_amount")
    @Expose
    private var ptAmount: Float = 0.toFloat()
    @SerializedName("pt_currency_code")
    @Expose
    private var ptCurrencyCode: String? = null
    @SerializedName("pt_customer_email")
    @Expose
    private var ptCustomerEmail: String? = null
    @SerializedName("pt_customer_phone_number")
    @Expose
    private var ptCustomerPhoneNumber: String? = null
    @SerializedName("pt_order_id")
    @Expose
    private var ptOrderId: Int = 0
    @SerializedName("pt_product_name")
    @Expose
    private var ptProductName: String? = null
    @SerializedName("pt_timeout_in_seconds")
    @Expose
    private var ptTimeoutInSeconds: Int = 0
    @SerializedName("pt_address_billing")
    @Expose
    private var ptAddressBilling: String? = null
    @SerializedName("pt_city_billing")
    @Expose
    private var ptCityBilling: String? = null
    @SerializedName("pt_state_billing")
    @Expose
    private var ptStateBilling: String? = null
    @SerializedName("pt_country_billing")
    @Expose
    private var ptCountryBilling: String? = null
    @SerializedName("pt_postal_code_billing")
    @Expose
    private var ptPostalCodeBilling: String? = null
    @SerializedName("pt_address_shipping")
    @Expose
    private var ptAddressShipping: String? = null
    @SerializedName("pt_city_shipping")
    @Expose
    private var ptCityShipping: String? = null
    @SerializedName("pt_state_shipping")
    @Expose
    private var ptStateShipping: String? = null
    @SerializedName("pt_country_shipping")
    @Expose
    private var ptCountryShipping: String? = null
    @SerializedName("pt_postal_code_shipping")
    @Expose
    private var ptPostalCodeShipping: String? = null
    @SerializedName("pt_payment_iso_code_3")
    @Expose
    private var ptPaymentIsoCode3: String? = null
    @SerializedName("pt_shipping_iso_code_3")
    @Expose
    private var ptShippingIsoCode3: String? = null


    fun getPtShippingIsoCode3(): String? {
        return ptShippingIsoCode3
    }

    fun setPtShippingIsoCode3(ptShippingIsoCode3: String) {
        this.ptShippingIsoCode3 = ptShippingIsoCode3
    }

    fun getPtPaymentIsoCode3(): String? {
        return ptPaymentIsoCode3
    }

    fun setPtPaymentIsoCode3(ptPaymentIsoCode3: String) {
        this.ptPaymentIsoCode3 = ptPaymentIsoCode3
    }
    fun getPtMerchantEmail(): String? {
        return ptMerchantEmail
    }

    fun setPtMerchantEmail(ptMerchantEmail: String) {
        this.ptMerchantEmail = ptMerchantEmail
    }

    fun getPtSecretKey(): String? {
        return ptSecretKey
    }

    fun setPtSecretKey(ptSecretKey: String) {
        this.ptSecretKey = ptSecretKey
    }

    fun getPtTransactionTitle(): String? {
        return ptTransactionTitle
    }

    fun setPtTransactionTitle(ptTransactionTitle: String) {
        this.ptTransactionTitle = ptTransactionTitle
    }

    fun getPtAmount(): Float {
        return ptAmount
    }

    fun setPtAmount(ptAmount: Float) {
        this.ptAmount = ptAmount
    }

    fun getPtCurrencyCode(): String? {
        return ptCurrencyCode
    }

    fun setPtCurrencyCode(ptCurrencyCode: String) {
        this.ptCurrencyCode = ptCurrencyCode
    }

    fun getPtCustomerEmail(): String? {
        return ptCustomerEmail
    }

    fun setPtCustomerEmail(ptCustomerEmail: String) {
        this.ptCustomerEmail = ptCustomerEmail
    }

    fun getPtCustomerPhoneNumber(): String? {
        return ptCustomerPhoneNumber
    }

    fun setPtCustomerPhoneNumber(ptCustomerPhoneNumber: String) {
        this.ptCustomerPhoneNumber = ptCustomerPhoneNumber
    }

    fun getPtOrderId(): Int {
        return ptOrderId
    }

    fun setPtOrderId(ptOrderId: Int) {
        this.ptOrderId = ptOrderId
    }

    fun getPtProductName(): String? {
        return ptProductName
    }

    fun setPtProductName(ptProductName: String) {
        this.ptProductName = ptProductName
    }

    fun getPtTimeoutInSeconds(): Int {
        return ptTimeoutInSeconds
    }

    fun setPtTimeoutInSeconds(ptTimeoutInSeconds: Int) {
        this.ptTimeoutInSeconds = ptTimeoutInSeconds
    }

    fun getPtAddressBilling(): String? {
        return ptAddressBilling
    }

    fun setPtAddressBilling(ptAddressBilling: String) {
        this.ptAddressBilling = ptAddressBilling
    }

    fun getPtCityBilling(): String? {
        return ptCityBilling
    }

    fun setPtCityBilling(ptCityBilling: String) {
        this.ptCityBilling = ptCityBilling
    }

    fun getPtStateBilling(): String? {
        return ptStateBilling
    }

    fun setPtStateBilling(ptStateBilling: String) {
        this.ptStateBilling = ptStateBilling
    }

    fun getPtCountryBilling(): String? {
        return ptCountryBilling
    }

    fun setPtCountryBilling(ptCountryBilling: String) {
        this.ptCountryBilling = ptCountryBilling
    }

    fun getPtPostalCodeBilling(): String? {
        return ptPostalCodeBilling
    }

    fun setPtPostalCodeBilling(ptPostalCodeBilling: String) {
        this.ptPostalCodeBilling = ptPostalCodeBilling
    }

    fun getPtAddressShipping(): String? {
        return ptAddressShipping
    }

    fun setPtAddressShipping(ptAddressShipping: String) {
        this.ptAddressShipping = ptAddressShipping
    }

    fun getPtCityShipping(): String? {
        return ptCityShipping
    }

    fun setPtCityShipping(ptCityShipping: String) {
        this.ptCityShipping = ptCityShipping
    }

    fun getPtStateShipping(): String? {
        return ptStateShipping
    }

    fun setPtStateShipping(ptStateShipping: String) {
        this.ptStateShipping = ptStateShipping
    }

    fun getPtCountryShipping(): String? {
        return ptCountryShipping
    }

    fun setPtCountryShipping(ptCountryShipping: String) {
        this.ptCountryShipping = ptCountryShipping
    }

    fun getPtPostalCodeShipping(): String? {
        return ptPostalCodeShipping
    }

    fun setPtPostalCodeShipping(ptPostalCodeShipping: String) {
        this.ptPostalCodeShipping = ptPostalCodeShipping
    }
}