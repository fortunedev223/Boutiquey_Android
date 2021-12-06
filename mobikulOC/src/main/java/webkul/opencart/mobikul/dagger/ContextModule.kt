package webkul.opencart.mobikul.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

@Module
class ContextModule(val mContext: Context) {
    @Provides
    fun getContext() = mContext
}
