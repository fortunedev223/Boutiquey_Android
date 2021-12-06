package webkul.opencart.mobikul.cartNotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.utils.AppSharedPreference

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CartBroadCast : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (AppSharedPreference.isLogin(p0!!, Constant.CUSTOMER_SHARED_PREFERENCE_NAME)) {
            p0.startService(Intent(p0, CartService::class.java))
        }
    }
}
