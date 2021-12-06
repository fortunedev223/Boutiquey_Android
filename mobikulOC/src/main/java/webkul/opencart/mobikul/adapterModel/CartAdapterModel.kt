package webkul.opencart.mobikul.adapterModel

import android.text.Html
import android.util.Log

import webkul.opencart.mobikul.model.VIewCartModel.Option



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CartAdapterModel(var imageUrl: String?, productTitle: String, var productPrice: String?,
                       var productId: String?, var productModel: String?, var productKey: String?,
                       var total: String?, var subTotal: String?, private var Quantity: String?,
                       private var Stock: Boolean, var option: List<Option>?, var reward: String?,
                       var dominantColor:String) {

    var productTitle: String? = null
        get() = Html.fromHtml(field).toString()

    var hasOption:Boolean?=false
    get() = option?.size != 0

    var quantity: String?
        get() {
            Log.d("CartQuantity", "getQuantity:------------> " + Quantity!!)
            return Quantity
        }
        set(quantity) = if (Integer.parseInt(quantity) >= 1) {
            Quantity = quantity
        } else {
            Quantity = 1.toString()
        }

    val stock: Boolean?
        get() = java.lang.Boolean.valueOf(Stock)

    fun isStock(): Boolean {
        return Stock
    }

    fun setStock(stock: Boolean) {
        Stock = stock
    }

    init {
        this.productTitle = productTitle
    }
}
