package webkul.opencart.mobikul.helper

import android.content.Context

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

object Constant {
    //    http://prabhatgautam.com/d8/index.php/
    val BASE_URL: String = "https://boutiquey.net/"
    //    val BASE_URL: String = "https://oc.webkul.com/mobikul/Network/index.php/"
    //    val BASE_URL: String = "https://oc.webkul.com/mobikul/Network/MP/index.php/"
    val API_USERNAME: String = "marwan"
    val API_PASSWORD: String = "87654321"
    val CART_TO_HOMEPAGE: String = "cart"
    fun getApplicationVersionCheckerUrl(mContext: Context) = "https://play.google.com/store/apps/details?id=" + mContext.applicationContext.packageName
    val CUSTOMER_SHARED_PREFERENCE_NAME: String = "customerData"
    val GUEST_STATUS = "isGuest"
    val GDPR_STATUS = "gdpr"
    val CONFIGUREVIEW_SHARED_PREFERENCE_NAME: String = "configureView"
    val CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_EMAIL: String = "customerEmail"
    val CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_NAME: String = "customerName"
    val CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_ID: String = "customerId"
    val CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS: String = "cartItems"
    val CUSTOMER_SHARED_PREFERENCE_KEY_IS_LOGGED_IN: String = "isLoggedIn"
    val CUSTOMER_SHARED_PREFERENCE_KEY_IS_SELLER: String = "isSeller"
    val RECENT_SEARCH: String = "recentSearch"
    val CONFIGUREVIEW_SHARED_PREFERENCE_KEY_CURRENCY_CODE: String = "currencyCode"
    val CONFIGUREVIEW_SHARED_PREFERENCE_KEY_STORE_CODE: String = "storeCode"
    val CUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN: String = "wk_token"
    val CONFIGURATION_PREFERENCE_NAME: String = "configurationPreference"
    val CONFIGURATION_PREFERENCE_KEY_STOREID_NAME: String = "storeId"
    val CONFIGURATION_PREFERENCE_KEY_STORECODE_NAME: String = "storeCode"
    val CONFIGURATION_PREFERENCE_KEY_STORELANGUAGE_NAME: String = "storeLanguage"
    val CATEGORYVIEW_PREFERENCE_NAME: String = "categoryView"
    val IS_SELLER: String = "isSeller"
    val RETURN_ID: String = "return_id"
    val ORDER_ID: String = "orderId"
    val SYNC_STATUS: String = "syncData"
    val PRODUCT_ID: String = "productID"
    val KEY_STORE_CODE = "storeCode"
    val KEY_STORE_TIME = "storeTime"
    val DEFAULT_STORE_CODE = "en"
    val KEY_LANGUAGE_CODE = "langaugeCode"
    val PAYTABS_SHARED_PREFERENCE_NAME: String = "payTabsData"
}
