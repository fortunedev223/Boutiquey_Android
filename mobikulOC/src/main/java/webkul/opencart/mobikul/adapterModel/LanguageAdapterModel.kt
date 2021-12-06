package webkul.opencart.mobikul.adapterModel



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class LanguageAdapterModel(var selectCode: String?, var name: String?, var code: String?, var image: String?) {
    val languageTitle: String
        get() = name!!.trim().substring(0, 1)
}
