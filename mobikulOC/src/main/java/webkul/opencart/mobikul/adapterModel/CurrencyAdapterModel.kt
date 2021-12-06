package webkul.opencart.mobikul.adapterModel



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CurrencyAdapterModel(var defaultCode: String?, var title: String?, var code: String?) {

    val currencyTitle: String
        get() = title!!.trim().substring(0, 1)
}
