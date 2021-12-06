package webkul.opencart.mobikul.model.GetProduct

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LangArray : Parcelable {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("direction")
    @Expose
    var direction: String? = null
    @SerializedName("date_format_short")
    @Expose
    var dateFormatShort: String? = null
    @SerializedName("date_format_long")
    @Expose
    var dateFormatLong: String? = null
    @SerializedName("time_format")
    @Expose
    var timeFormat: String? = null
    @SerializedName("datetime_format")
    @Expose
    var datetimeFormat: String? = null
    @SerializedName("decimal_point")
    @Expose
    var decimalPoint: String? = null
    @SerializedName("thousand_point")
    @Expose
    var thousandPoint: String? = null
    @SerializedName("text_home")
    @Expose
    var textHome: String? = null
    @SerializedName("text_yes")
    @Expose
    var textYes: String? = null
    @SerializedName("text_no")
    @Expose
    var textNo: String? = null
    @SerializedName("text_none")
    @Expose
    var textNone: String? = null
    @SerializedName("text_select")
    @Expose
    var textSelect: String? = null
    @SerializedName("text_all_zones")
    @Expose
    var textAllZones: String? = null
    @SerializedName("text_pagination")
    @Expose
    var textPagination: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("text_no_results")
    @Expose
    var textNoResults: String? = null
    @SerializedName("button_address_add")
    @Expose
    var buttonAddressAdd: String? = null
    @SerializedName("button_back")
    @Expose
    var buttonBack: String? = null
    @SerializedName("button_continue")
    @Expose
    var buttonContinue: String? = null
    @SerializedName("button_cart")
    @Expose
    var buttonCart: String? = null
    @SerializedName("button_cancel")
    @Expose
    var buttonCancel: String? = null
    @SerializedName("button_compare")
    @Expose
    var buttonCompare: String? = null
    @SerializedName("button_wishlist")
    @Expose
    var buttonWishlist: String? = null
    @SerializedName("button_checkout")
    @Expose
    var buttonCheckout: String? = null
    @SerializedName("button_confirm")
    @Expose
    var buttonConfirm: String? = null
    @SerializedName("button_coupon")
    @Expose
    var buttonCoupon: String? = null
    @SerializedName("button_delete")
    @Expose
    var buttonDelete: String? = null
    @SerializedName("button_download")
    @Expose
    var buttonDownload: String? = null
    @SerializedName("button_edit")
    @Expose
    var buttonEdit: String? = null
    @SerializedName("button_filter")
    @Expose
    var buttonFilter: String? = null
    @SerializedName("button_new_address")
    @Expose
    var buttonNewAddress: String? = null
    @SerializedName("button_change_address")
    @Expose
    var buttonChangeAddress: String? = null
    @SerializedName("button_reviews")
    @Expose
    var buttonReviews: String? = null
    @SerializedName("button_write")
    @Expose
    var buttonWrite: String? = null
    @SerializedName("button_login")
    @Expose
    var buttonLogin: String? = null
    @SerializedName("button_update")
    @Expose
    var buttonUpdate: String? = null
    @SerializedName("button_remove")
    @Expose
    var buttonRemove: String? = null
    @SerializedName("button_reorder")
    @Expose
    var buttonReorder: String? = null
    @SerializedName("button_return")
    @Expose
    var buttonReturn: String? = null
    @SerializedName("button_shopping")
    @Expose
    var buttonShopping: String? = null
    @SerializedName("button_search")
    @Expose
    var buttonSearch: String? = null
    @SerializedName("button_shipping")
    @Expose
    var buttonShipping: String? = null
    @SerializedName("button_submit")
    @Expose
    var buttonSubmit: String? = null
    @SerializedName("button_guest")
    @Expose
    var buttonGuest: String? = null
    @SerializedName("button_view")
    @Expose
    var buttonView: String? = null
    @SerializedName("button_voucher")
    @Expose
    var buttonVoucher: String? = null
    @SerializedName("button_upload")
    @Expose
    var buttonUpload: String? = null
    @SerializedName("button_reward")
    @Expose
    var buttonReward: String? = null
    @SerializedName("button_quote")
    @Expose
    var buttonQuote: String? = null
    @SerializedName("button_list")
    @Expose
    var buttonList: String? = null
    @SerializedName("button_grid")
    @Expose
    var buttonGrid: String? = null
    @SerializedName("button_map")
    @Expose
    var buttonMap: String? = null
    @SerializedName("error_exception")
    @Expose
    var errorException: String? = null
    @SerializedName("error_upload_1")
    @Expose
    var errorUpload1: String? = null
    @SerializedName("error_upload_2")
    @Expose
    var errorUpload2: String? = null
    @SerializedName("error_upload_3")
    @Expose
    var errorUpload3: String? = null
    @SerializedName("error_upload_4")
    @Expose
    var errorUpload4: String? = null
    @SerializedName("error_upload_6")
    @Expose
    var errorUpload6: String? = null
    @SerializedName("error_upload_7")
    @Expose
    var errorUpload7: String? = null
    @SerializedName("error_upload_8")
    @Expose
    var errorUpload8: String? = null
    @SerializedName("error_upload_999")
    @Expose
    var errorUpload999: String? = null
    @SerializedName("error_curl")
    @Expose
    var errorCurl: String? = null
    @SerializedName("text_token_message")
    @Expose
    var textTokenMessage: String? = null
    @SerializedName("text_api_logout_message")
    @Expose
    var textApiLogoutMessage: String? = null
    @SerializedName("text_api_login_message")
    @Expose
    var textApiLoginMessage: String? = null
    @SerializedName("text_language_message")
    @Expose
    var textLanguageMessage: String? = null
    @SerializedName("text_customer_login_message")
    @Expose
    var textCustomerLoginMessage: String? = null
    @SerializedName("text_edit_password_message")
    @Expose
    var textEditPasswordMessage: String? = null
    @SerializedName("text_coupon_message")
    @Expose
    var textCouponMessage: String? = null
    @SerializedName("text_voucher_message")
    @Expose
    var textVoucherMessage: String? = null
    @SerializedName("text_reward_message")
    @Expose
    var textRewardMessage: String? = null
    @SerializedName("text_product_message")
    @Expose
    var textProductMessage: String? = null
    @SerializedName("text_key_message")
    @Expose
    var textKeyMessage: String? = null
    @SerializedName("text_update_cart_message")
    @Expose
    var textUpdateCartMessage: String? = null
    @SerializedName("text_update_cart_error")
    @Expose
    var textUpdateCartError: String? = null
    @SerializedName("text_order_id_error")
    @Expose
    var textOrderIdError: String? = null
    @SerializedName("text_order_status_id_error")
    @Expose
    var textOrderStatusIdError: String? = null
    @SerializedName("text_notify_error")
    @Expose
    var textNotifyError: String? = null
    @SerializedName("text_seller_id_error")
    @Expose
    var textSellerIdError: String? = null
    @SerializedName("text_subject_error")
    @Expose
    var textSubjectError: String? = null
    @SerializedName("text_message_error")
    @Expose
    var textMessageError: String? = null
    @SerializedName("text_path_error")
    @Expose
    var textPathError: String? = null
    @SerializedName("text_address_id_error")
    @Expose
    var textAddressIdError: String? = null
    @SerializedName("text_login_error")
    @Expose
    var textLoginError: String? = null
    @SerializedName("text_collection_message")
    @Expose
    var textCollectionMessage: String? = null
    @SerializedName("text_complete")
    @Expose
    var textComplete: String? = null
    @SerializedName("text_pending")
    @Expose
    var textPending: String? = null
    @SerializedName("text_search")
    @Expose
    var textSearch: String? = null
    @SerializedName("text_brand")
    @Expose
    var textBrand: String? = null
    @SerializedName("text_manufacturer")
    @Expose
    var textManufacturer: String? = null
    @SerializedName("text_model")
    @Expose
    var textModel: String? = null
    @SerializedName("text_reward")
    @Expose
    var textReward: String? = null
    @SerializedName("text_points")
    @Expose
    var textPoints: String? = null
    @SerializedName("text_stock")
    @Expose
    var textStock: String? = null
    @SerializedName("text_instock")
    @Expose
    var textInstock: String? = null
    @SerializedName("text_tax")
    @Expose
    var textTax: String? = null
    @SerializedName("text_discount")
    @Expose
    var textDiscount: String? = null
    @SerializedName("text_option")
    @Expose
    var textOption: String? = null
    @SerializedName("text_minimum")
    @Expose
    var textMinimum: String? = null
    @SerializedName("text_reviews")
    @Expose
    var textReviews: String? = null
    @SerializedName("text_write")
    @Expose
    var textWrite: String? = null
    @SerializedName("text_login")
    @Expose
    var textLogin: String? = null
    @SerializedName("text_no_reviews")
    @Expose
    var textNoReviews: String? = null
    @SerializedName("text_note")
    @Expose
    var textNote: String? = null
    @SerializedName("text_success")
    @Expose
    var textSuccess: String? = null
    @SerializedName("text_related")
    @Expose
    var textRelated: String? = null
    @SerializedName("text_tags")
    @Expose
    var textTags: String? = null
    @SerializedName("text_error")
    @Expose
    var textError: String? = null
    @SerializedName("text_payment_recurring")
    @Expose
    var textPaymentRecurring: String? = null
    @SerializedName("text_trial_description")
    @Expose
    var textTrialDescription: String? = null
    @SerializedName("text_payment_description")
    @Expose
    var textPaymentDescription: String? = null
    @SerializedName("text_payment_cancel")
    @Expose
    var textPaymentCancel: String? = null
    @SerializedName("text_day")
    @Expose
    var textDay: String? = null
    @SerializedName("text_week")
    @Expose
    var textWeek: String? = null
    @SerializedName("text_semi_month")
    @Expose
    var textSemiMonth: String? = null
    @SerializedName("text_month")
    @Expose
    var textMonth: String? = null
    @SerializedName("text_year")
    @Expose
    var textYear: String? = null
    @SerializedName("entry_qty")
    @Expose
    var entryQty: String? = null
    @SerializedName("entry_name")
    @Expose
    var entryName: String? = null
    @SerializedName("entry_review")
    @Expose
    var entryReview: String? = null
    @SerializedName("entry_rating")
    @Expose
    var entryRating: String? = null
    @SerializedName("entry_good")
    @Expose
    var entryGood: String? = null
    @SerializedName("entry_bad")
    @Expose
    var entryBad: String? = null
    @SerializedName("tab_description")
    @Expose
    var tabDescription: String? = null
    @SerializedName("tab_attribute")
    @Expose
    var tabAttribute: String? = null
    @SerializedName("tab_review")
    @Expose
    var tabReview: String? = null
    @SerializedName("error_name")
    @Expose
    var errorName: String? = null
    @SerializedName("error_text")
    @Expose
    var errorText: String? = null
    @SerializedName("error_rating")
    @Expose
    var errorRating: String? = null


    protected constructor(`in`: Parcel) {
        code = `in`.readString()
        direction = `in`.readString()
        dateFormatShort = `in`.readString()
        dateFormatLong = `in`.readString()
        timeFormat = `in`.readString()
        datetimeFormat = `in`.readString()
        decimalPoint = `in`.readString()
        thousandPoint = `in`.readString()
        textHome = `in`.readString()
        textYes = `in`.readString()
        textNo = `in`.readString()
        textNone = `in`.readString()
        textSelect = `in`.readString()
        textAllZones = `in`.readString()
        textPagination = `in`.readString()
        textLoading = `in`.readString()
        textNoResults = `in`.readString()
        buttonAddressAdd = `in`.readString()
        buttonBack = `in`.readString()
        buttonContinue = `in`.readString()
        buttonCart = `in`.readString()
        buttonCancel = `in`.readString()
        buttonCompare = `in`.readString()
        buttonWishlist = `in`.readString()
        buttonCheckout = `in`.readString()
        buttonConfirm = `in`.readString()
        buttonCoupon = `in`.readString()
        buttonDelete = `in`.readString()
        buttonDownload = `in`.readString()
        buttonEdit = `in`.readString()
        buttonFilter = `in`.readString()
        buttonNewAddress = `in`.readString()
        buttonChangeAddress = `in`.readString()
        buttonReviews = `in`.readString()
        buttonWrite = `in`.readString()
        buttonLogin = `in`.readString()
        buttonUpdate = `in`.readString()
        buttonRemove = `in`.readString()
        buttonReorder = `in`.readString()
        buttonReturn = `in`.readString()
        buttonShopping = `in`.readString()
        buttonSearch = `in`.readString()
        buttonShipping = `in`.readString()
        buttonSubmit = `in`.readString()
        buttonGuest = `in`.readString()
        buttonView = `in`.readString()
        buttonVoucher = `in`.readString()
        buttonUpload = `in`.readString()
        buttonReward = `in`.readString()
        buttonQuote = `in`.readString()
        buttonList = `in`.readString()
        buttonGrid = `in`.readString()
        buttonMap = `in`.readString()
        errorException = `in`.readString()
        errorUpload1 = `in`.readString()
        errorUpload2 = `in`.readString()
        errorUpload3 = `in`.readString()
        errorUpload4 = `in`.readString()
        errorUpload6 = `in`.readString()
        errorUpload7 = `in`.readString()
        errorUpload8 = `in`.readString()
        errorUpload999 = `in`.readString()
        errorCurl = `in`.readString()
        textTokenMessage = `in`.readString()
        textApiLogoutMessage = `in`.readString()
        textApiLoginMessage = `in`.readString()
        textLanguageMessage = `in`.readString()
        textCustomerLoginMessage = `in`.readString()
        textEditPasswordMessage = `in`.readString()
        textCouponMessage = `in`.readString()
        textVoucherMessage = `in`.readString()
        textRewardMessage = `in`.readString()
        textProductMessage = `in`.readString()
        textKeyMessage = `in`.readString()
        textUpdateCartMessage = `in`.readString()
        textUpdateCartError = `in`.readString()
        textOrderIdError = `in`.readString()
        textOrderStatusIdError = `in`.readString()
        textNotifyError = `in`.readString()
        textSellerIdError = `in`.readString()
        textSubjectError = `in`.readString()
        textMessageError = `in`.readString()
        textPathError = `in`.readString()
        textAddressIdError = `in`.readString()
        textLoginError = `in`.readString()
        textCollectionMessage = `in`.readString()
        textComplete = `in`.readString()
        textPending = `in`.readString()
        textSearch = `in`.readString()
        textBrand = `in`.readString()
        textManufacturer = `in`.readString()
        textModel = `in`.readString()
        textReward = `in`.readString()
        textPoints = `in`.readString()
        textStock = `in`.readString()
        textInstock = `in`.readString()
        textTax = `in`.readString()
        textDiscount = `in`.readString()
        textOption = `in`.readString()
        textMinimum = `in`.readString()
        textReviews = `in`.readString()
        textWrite = `in`.readString()
        textLogin = `in`.readString()
        textNoReviews = `in`.readString()
        textNote = `in`.readString()
        textSuccess = `in`.readString()
        textRelated = `in`.readString()
        textTags = `in`.readString()
        textError = `in`.readString()
        textPaymentRecurring = `in`.readString()
        textTrialDescription = `in`.readString()
        textPaymentDescription = `in`.readString()
        textPaymentCancel = `in`.readString()
        textDay = `in`.readString()
        textWeek = `in`.readString()
        textSemiMonth = `in`.readString()
        textMonth = `in`.readString()
        textYear = `in`.readString()
        entryQty = `in`.readString()
        entryName = `in`.readString()
        entryReview = `in`.readString()
        entryRating = `in`.readString()
        entryGood = `in`.readString()
        entryBad = `in`.readString()
        tabDescription = `in`.readString()
        tabAttribute = `in`.readString()
        tabReview = `in`.readString()
        errorName = `in`.readString()
        errorText = `in`.readString()
        errorRating = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(code)
        dest.writeString(direction)
        dest.writeString(dateFormatShort)
        dest.writeString(dateFormatLong)
        dest.writeString(timeFormat)
        dest.writeString(datetimeFormat)
        dest.writeString(decimalPoint)
        dest.writeString(thousandPoint)
        dest.writeString(textHome)
        dest.writeString(textYes)
        dest.writeString(textNo)
        dest.writeString(textNone)
        dest.writeString(textSelect)
        dest.writeString(textAllZones)
        dest.writeString(textPagination)
        dest.writeString(textLoading)
        dest.writeString(textNoResults)
        dest.writeString(buttonAddressAdd)
        dest.writeString(buttonBack)
        dest.writeString(buttonContinue)
        dest.writeString(buttonCart)
        dest.writeString(buttonCancel)
        dest.writeString(buttonCompare)
        dest.writeString(buttonWishlist)
        dest.writeString(buttonCheckout)
        dest.writeString(buttonConfirm)
        dest.writeString(buttonCoupon)
        dest.writeString(buttonDelete)
        dest.writeString(buttonDownload)
        dest.writeString(buttonEdit)
        dest.writeString(buttonFilter)
        dest.writeString(buttonNewAddress)
        dest.writeString(buttonChangeAddress)
        dest.writeString(buttonReviews)
        dest.writeString(buttonWrite)
        dest.writeString(buttonLogin)
        dest.writeString(buttonUpdate)
        dest.writeString(buttonRemove)
        dest.writeString(buttonReorder)
        dest.writeString(buttonReturn)
        dest.writeString(buttonShopping)
        dest.writeString(buttonSearch)
        dest.writeString(buttonShipping)
        dest.writeString(buttonSubmit)
        dest.writeString(buttonGuest)
        dest.writeString(buttonView)
        dest.writeString(buttonVoucher)
        dest.writeString(buttonUpload)
        dest.writeString(buttonReward)
        dest.writeString(buttonQuote)
        dest.writeString(buttonList)
        dest.writeString(buttonGrid)
        dest.writeString(buttonMap)
        dest.writeString(errorException)
        dest.writeString(errorUpload1)
        dest.writeString(errorUpload2)
        dest.writeString(errorUpload3)
        dest.writeString(errorUpload4)
        dest.writeString(errorUpload6)
        dest.writeString(errorUpload7)
        dest.writeString(errorUpload8)
        dest.writeString(errorUpload999)
        dest.writeString(errorCurl)
        dest.writeString(textTokenMessage)
        dest.writeString(textApiLogoutMessage)
        dest.writeString(textApiLoginMessage)
        dest.writeString(textLanguageMessage)
        dest.writeString(textCustomerLoginMessage)
        dest.writeString(textEditPasswordMessage)
        dest.writeString(textCouponMessage)
        dest.writeString(textVoucherMessage)
        dest.writeString(textRewardMessage)
        dest.writeString(textProductMessage)
        dest.writeString(textKeyMessage)
        dest.writeString(textUpdateCartMessage)
        dest.writeString(textUpdateCartError)
        dest.writeString(textOrderIdError)
        dest.writeString(textOrderStatusIdError)
        dest.writeString(textNotifyError)
        dest.writeString(textSellerIdError)
        dest.writeString(textSubjectError)
        dest.writeString(textMessageError)
        dest.writeString(textPathError)
        dest.writeString(textAddressIdError)
        dest.writeString(textLoginError)
        dest.writeString(textCollectionMessage)
        dest.writeString(textComplete)
        dest.writeString(textPending)
        dest.writeString(textSearch)
        dest.writeString(textBrand)
        dest.writeString(textManufacturer)
        dest.writeString(textModel)
        dest.writeString(textReward)
        dest.writeString(textPoints)
        dest.writeString(textStock)
        dest.writeString(textInstock)
        dest.writeString(textTax)
        dest.writeString(textDiscount)
        dest.writeString(textOption)
        dest.writeString(textMinimum)
        dest.writeString(textReviews)
        dest.writeString(textWrite)
        dest.writeString(textLogin)
        dest.writeString(textNoReviews)
        dest.writeString(textNote)
        dest.writeString(textSuccess)
        dest.writeString(textRelated)
        dest.writeString(textTags)
        dest.writeString(textError)
        dest.writeString(textPaymentRecurring)
        dest.writeString(textTrialDescription)
        dest.writeString(textPaymentDescription)
        dest.writeString(textPaymentCancel)
        dest.writeString(textDay)
        dest.writeString(textWeek)
        dest.writeString(textSemiMonth)
        dest.writeString(textMonth)
        dest.writeString(textYear)
        dest.writeString(entryQty)
        dest.writeString(entryName)
        dest.writeString(entryReview)
        dest.writeString(entryRating)
        dest.writeString(entryGood)
        dest.writeString(entryBad)
        dest.writeString(tabDescription)
        dest.writeString(tabAttribute)
        dest.writeString(tabReview)
        dest.writeString(errorName)
        dest.writeString(errorText)
        dest.writeString(errorRating)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LangArray> = object : Parcelable.Creator<LangArray> {
            override fun createFromParcel(`in`: Parcel): LangArray {
                return LangArray(`in`)
            }

            override fun newArray(size: Int): Array<LangArray?> {
                return arrayOfNulls(size)
            }
        }
    }
}