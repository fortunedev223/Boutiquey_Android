package webkul.opencart.mobikul.utils

import android.content.Context
import webkul.opencart.mobikul.helper.Constant
import android.content.Context.MODE_PRIVATE

object AppSharedPreference {
    //    ADD PRODUCT
    fun addProductGeneral(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("general", check).commit()
    }

    fun addProductData(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("data", check).commit()
    }

    fun addProductLinks(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("links", check).commit()
    }

    fun addProductLinksStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("links", false)
    }

    fun addProductAttribute(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("attribute", check).commit()
    }

    fun addProductAttributeStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("attribute", false)
    }

    fun addProductOptions(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("options", check).commit()
    }

    fun addProductOptionsStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("options", false)
    }

    fun addProductDiscount(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("discount", check).commit()
    }

    fun addProductDiscountStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("discount", false)
    }

    fun addProductSpecial(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("special", check).commit()
    }

    fun addProductSpecialStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("special", false)
    }

    fun addProductImages(context: Context, check: Boolean) {
        context.getSharedPreferences("addProduct", MODE_PRIVATE).edit().putBoolean("images", check).commit()
    }

    fun addProductImagesStatus(context: Context): Boolean {
        return context.getSharedPreferences("addProduct", MODE_PRIVATE).getBoolean("images", false)
    }
//    ADD PRODUCT


    fun getLanguageCode(mContext: Context): String? {
        return mContext.getSharedPreferences(Constant.KEY_LANGUAGE_CODE, MODE_PRIVATE).getString(Constant.KEY_LANGUAGE_CODE, "")
    }

    fun setLanguageCode(mContext: Context, languageCode: String?) {
        mContext.getSharedPreferences(Constant.KEY_LANGUAGE_CODE, MODE_PRIVATE).edit().putString(Constant.KEY_LANGUAGE_CODE, languageCode).commit()
    }

    fun updateWishlistStatus(mContext: Context, status: Boolean) {
        mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putBoolean("wishlist", status).apply()
    }

    fun getWishlistStatus(mContext: Context): Boolean {
        return mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getBoolean("wishlist", false)
    }

    fun setPatner(mContext: Context, patner: Int) {
        mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putString(Constant.IS_SELLER, patner.toString()).apply()
    }

    fun isPatner(mContext: Context): Int {
        return mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString(Constant.IS_SELLER, "0").toInt()
    }

    fun setGDPRStatus(mContext: Context, status: Boolean) {
        mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putBoolean(Constant.GDPR_STATUS, status).apply()
    }

    fun getGDPRStatus(mContext: Context): Boolean {
        return mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getBoolean(Constant.GDPR_STATUS, false)
    }

    fun setGuestStatus(mContext: Context, isGuest: Boolean?) {
        mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putBoolean(Constant.GUEST_STATUS, isGuest!!).apply()
    }

    fun isGuest(mContext: Context): Boolean {
        return mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getBoolean(Constant.GUEST_STATUS, false)
    }

    fun isLogin(context: Context, prefName: String): Boolean {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getBoolean("isLoggedIn", false)
    }

    fun getcustomerEmail(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("customerEmail", "")
    }

    fun getWkToken(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("wk_token", "Session_Not_Loggin")
    }

    fun isSeller(context: Context): Int {
        return Integer.parseInt(context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString(Constant.IS_SELLER, "0"))
    }

    fun getCustomerName(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("customerName", "")
    }

    fun getCustomerId(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("customerId", "")
    }

    fun getCartItems(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("cartItems", "0")
    }

    fun addCartItems(context: Context, item: String?) {
        context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putString("cartItems", item).apply()
    }

    fun getStoreCode(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("storeCode", "")
    }

    fun getCurrencyCode(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString("currencyCode", "")
    }

    fun setCurrencyCode(mContext: Context, currencyCode: String) {
        mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putString("currencyCode", currencyCode).apply()
    }

    fun putRecentSearchData(mContext: Context, set: Set<String>) {
        mContext.getSharedPreferences(Constant.RECENT_SEARCH, MODE_PRIVATE).edit().putStringSet(Constant.RECENT_SEARCH, set).apply()
    }

    fun putGdprStatus(mContext: Context, set: String) {
        mContext.getSharedPreferences(Constant.RECENT_SEARCH, MODE_PRIVATE).edit().putString(Constant.GDPR_STATUS, set).apply()
    }

    fun getGdprStatus(mContext: Context): String? {
        return mContext.getSharedPreferences(Constant.RECENT_SEARCH, MODE_PRIVATE).getString(Constant.GDPR_STATUS, "100")
    }

    fun getRecentSearchData(mContext: Context): Set<String>? {
        return mContext.getSharedPreferences(Constant.RECENT_SEARCH, MODE_PRIVATE).getStringSet(Constant.RECENT_SEARCH, null)
    }

    fun removeRecentView(mContext: Context) {
        mContext.getSharedPreferences(Constant.RECENT_SEARCH, MODE_PRIVATE).edit().clear().apply()
    }

    fun editSharedPreference(context: Context, prefName: String, key: String, value: String?) {
        context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putString(key, value).apply()
    }

    fun editBooleanSharedPreference(context: Context, prefName: String, key: String, value: Boolean) {
        context.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE).edit().putBoolean(key, value).apply()
    }

    fun removeKeySharedPreference(context: Context, prefName: String, key: String) {
        context.getSharedPreferences(prefName, MODE_PRIVATE).edit().remove(key).apply()
    }

    fun clearSharedPreference(context: Context, prefName: String) {
        context.getSharedPreferences(prefName, MODE_PRIVATE).edit().clear().apply()
    }

    fun putSyncStatusData(mContext: Context, set: Boolean) {
        mContext.getSharedPreferences(Constant.SYNC_STATUS, MODE_PRIVATE).edit().putBoolean(Constant.SYNC_STATUS, set).apply()
    }

    fun getSyncStatusData(mContext: Context): Boolean? {
        return mContext.getSharedPreferences(Constant.SYNC_STATUS, MODE_PRIVATE).getBoolean(Constant.SYNC_STATUS, true)
    }


    fun getStoreCode(mContext: Context): String? {
        return mContext.getSharedPreferences(Constant.KEY_STORE_CODE, MODE_PRIVATE).getString(Constant.KEY_STORE_CODE, "")
    }

    fun setStoreCode(mContext: Context, languageCode: String) {
        mContext.getSharedPreferences(Constant.KEY_STORE_CODE, MODE_PRIVATE).edit().putString(Constant.KEY_STORE_CODE, languageCode).commit()
    }


    fun getStoreTime(mContext: Context): String? {
        return mContext.getSharedPreferences(Constant.KEY_STORE_TIME, MODE_PRIVATE).getString(Constant.KEY_STORE_TIME, "")
    }

    fun setStoreTime(mContext: Context, languageCode: String) {
        mContext.getSharedPreferences(Constant.KEY_STORE_TIME, MODE_PRIVATE).edit().putString(Constant.KEY_STORE_TIME, languageCode).commit()
    }

    fun getPayTabsData(context: Context, prefName: String): String {
        return context.getSharedPreferences(Constant.PAYTABS_SHARED_PREFERENCE_NAME, MODE_PRIVATE).getString(prefName, "")
    }

}

