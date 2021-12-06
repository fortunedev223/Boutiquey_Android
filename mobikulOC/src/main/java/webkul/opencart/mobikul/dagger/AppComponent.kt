package webkul.opencart.mobikul.dagger

import dagger.Component
import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.networkManager.ApiInteface
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.ViewProductSimple
import javax.inject.Singleton

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(b: ViewProductSimple)
    fun inject(retro: RetrofitCallback)
    fun getApiInterface():ApiInteface?
    fun inject(productPage: MainActivity)
    fun inject(productPage: CategoryActivity)
}
