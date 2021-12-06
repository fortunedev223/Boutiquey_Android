package webkul.opencart.mobikul.model.GDPRStatus

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

class GdprModel : BaseModel {
    constructor() : super()

    @SerializedName("mobikul_gdpr_status")
    @Expose
    private var mobikulGdprStatus: Int = 0
    @SerializedName("mobikul_gdpr_request_account_status")
    @Expose
    private var mobikulGdprRequestAccountStatus: Int = 0
    @SerializedName("mobikul_gdpr_request_address_status")
    @Expose
    private var mobikulGdprRequestAddressStatus: Int = 0
    @SerializedName("mobikul_gdpr_request_order_status")
    @Expose
    private var mobikulGdprRequestOrderStatus: Int = 0
    @SerializedName("mobikul_gdpr_account_status")
    @Expose
    private var mobikulGdprAccountStatus: Int = 0
    @SerializedName("mobikul_gdpr_account_description")
    @Expose
    private var mobikulGdprAccountDescription: String? = null
    @SerializedName("mobikul_gdpr_notification_status")
    @Expose
    private var mobikulGdprNotificationStatus: Int = 0
    @SerializedName("mobikul_gdpr_notification_description")
    @Expose
    private var mobikulGdprNotificationDescription: String? = null
    @SerializedName("mobikul_gdpr_address_status")
    @Expose
    private var mobikulGdprAddressStatus: Int = 0
    @SerializedName("mobikul_gdpr_address_description")
    @Expose
    private var mobikulGdprAddressDescription: String? = null
    @SerializedName("mobikul_gdpr_review_status")
    @Expose
    private var mobikulGdprReviewStatus: Int = 0
    @SerializedName("mobikul_gdpr_review_description")
    @Expose
    private var mobikulGdprReviewDescription: String? = null
    @SerializedName("mobikul_gdpr_password_status")
    @Expose
    private var mobikulGdprPasswordStatus: Int = 0
    @SerializedName("mobikul_gdpr_password_description")
    @Expose
    private var mobikulGdprPasswordDescription: String? = null
    @SerializedName("mobikul_gdpr_shipping_method_status")
    @Expose
    private var mobikulGdprShippingMethodStatus: Int = 0
    @SerializedName("mobikul_gdpr_shipping_method_description")
    @Expose
    private var mobikulGdprShippingMethodDescription: String? = null

    fun getMobikulGdprStatus(): Int {
        return mobikulGdprStatus
    }

    fun setMobikulGdprStatus(mobikulGdprStatus: Int) {
        this.mobikulGdprStatus = mobikulGdprStatus
    }

    fun getMobikulGdprRequestAccountStatus(): Int {
        return mobikulGdprRequestAccountStatus
    }

    fun setMobikulGdprRequestAccountStatus(mobikulGdprRequestAccountStatus: Int) {
        this.mobikulGdprRequestAccountStatus = mobikulGdprRequestAccountStatus
    }

    fun getMobikulGdprRequestAddressStatus(): Int {
        return mobikulGdprRequestAddressStatus
    }

    fun setMobikulGdprRequestAddressStatus(mobikulGdprRequestAddressStatus: Int) {
        this.mobikulGdprRequestAddressStatus = mobikulGdprRequestAddressStatus
    }

    fun getMobikulGdprRequestOrderStatus(): Int {
        return mobikulGdprRequestOrderStatus
    }

    fun setMobikulGdprRequestOrderStatus(mobikulGdprRequestOrderStatus: Int) {
        this.mobikulGdprRequestOrderStatus = mobikulGdprRequestOrderStatus
    }

    fun getMobikulGdprAccountStatus(): Int {
        return mobikulGdprAccountStatus
    }

    fun setMobikulGdprAccountStatus(mobikulGdprAccountStatus: Int) {
        this.mobikulGdprAccountStatus = mobikulGdprAccountStatus
    }

    fun getMobikulGdprAccountDescription(): String? {
        return mobikulGdprAccountDescription
    }

    fun setMobikulGdprAccountDescription(mobikulGdprAccountDescription: String) {
        this.mobikulGdprAccountDescription = mobikulGdprAccountDescription
    }

    fun getMobikulGdprNotificationStatus(): Int {
        return mobikulGdprNotificationStatus
    }

    fun setMobikulGdprNotificationStatus(mobikulGdprNotificationStatus: Int) {
        this.mobikulGdprNotificationStatus = mobikulGdprNotificationStatus
    }

    fun getMobikulGdprNotificationDescription(): String? {
        return mobikulGdprNotificationDescription
    }

    fun setMobikulGdprNotificationDescription(mobikulGdprNotificationDescription: String) {
        this.mobikulGdprNotificationDescription = mobikulGdprNotificationDescription
    }

    fun getMobikulGdprAddressStatus(): Int {
        return mobikulGdprAddressStatus
    }

    fun setMobikulGdprAddressStatus(mobikulGdprAddressStatus: Int) {
        this.mobikulGdprAddressStatus = mobikulGdprAddressStatus
    }

    fun getMobikulGdprAddressDescription(): String? {
        return mobikulGdprAddressDescription
    }

    fun setMobikulGdprAddressDescription(mobikulGdprAddressDescription: String) {
        this.mobikulGdprAddressDescription = mobikulGdprAddressDescription
    }

    fun getMobikulGdprReviewStatus(): Int {
        return mobikulGdprReviewStatus
    }

    fun setMobikulGdprReviewStatus(mobikulGdprReviewStatus: Int) {
        this.mobikulGdprReviewStatus = mobikulGdprReviewStatus
    }

    fun getMobikulGdprReviewDescription(): String? {
        return mobikulGdprReviewDescription
    }

    fun setMobikulGdprReviewDescription(mobikulGdprReviewDescription: String) {
        this.mobikulGdprReviewDescription = mobikulGdprReviewDescription
    }

    fun getMobikulGdprPasswordStatus(): Int {
        return mobikulGdprPasswordStatus
    }

    fun setMobikulGdprPasswordStatus(mobikulGdprPasswordStatus: Int) {
        this.mobikulGdprPasswordStatus = mobikulGdprPasswordStatus
    }

    fun getMobikulGdprPasswordDescription(): String? {
        return mobikulGdprPasswordDescription
    }

    fun setMobikulGdprPasswordDescription(mobikulGdprPasswordDescription: String) {
        this.mobikulGdprPasswordDescription = mobikulGdprPasswordDescription
    }

    fun getMobikulGdprShippingMethodStatus(): Int {
        return mobikulGdprShippingMethodStatus
    }

    fun setMobikulGdprShippingMethodStatus(mobikulGdprShippingMethodStatus: Int) {
        this.mobikulGdprShippingMethodStatus = mobikulGdprShippingMethodStatus
    }

    fun getMobikulGdprShippingMethodDescription(): String? {
        return mobikulGdprShippingMethodDescription
    }

    fun setMobikulGdprShippingMethodDescription(mobikulGdprShippingMethodDescription: String) {
        this.mobikulGdprShippingMethodDescription = mobikulGdprShippingMethodDescription
    }
}
