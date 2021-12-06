package webkul.opencart.mobikul.adapterModel

import android.text.Html
import android.util.Log


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class GetWishlistAdapterModel(var productId: String?, productName: String, var productImage: String?,
                              productPrice: String, var formattedSpecial: String?, var productSpecailPrice: String?,
                              var available: String?, var isHasOption: Boolean,
                              var domiantColor: String?) {
    var productName: String? = null
        get() = Html.fromHtml(field).toString()
    var productPrice: String? = null
        get() {
            Log.d("SpecialPrice", "getProductPrice: :------>" + field!!)
            return field
        }

    init {
        this.productName = productName
        this.productPrice = productPrice
    }
}
