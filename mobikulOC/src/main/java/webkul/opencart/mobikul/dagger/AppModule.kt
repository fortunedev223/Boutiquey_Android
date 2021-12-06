package webkul.opencart.mobikul.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import webkul.opencart.mobikul.networkManager.ApiClient
import webkul.opencart.mobikul.networkManager.ApiInteface
import javax.inject.Singleton

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

@Module(includes = [ContextModule::class])
class AppModule {
    @Provides
    @Singleton
    fun getApiInterface(mContext: Context): ApiInteface? {
        return ApiClient.getClient(mContext)?.create(ApiInteface::class.java)
    }
}
