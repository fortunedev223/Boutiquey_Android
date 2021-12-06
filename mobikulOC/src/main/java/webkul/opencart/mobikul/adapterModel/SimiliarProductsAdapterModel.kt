package webkul.opencart.mobikul.adapterModel

import android.util.Log


class SimiliarProductsAdapterModel(var productName: String?, var productId: String?, productPrice: String,
                                   var productImage: String?, var productStock: String?,
                                   var isProductSpecial: String?, var formatedPrice: String?,
                                   var isHasOption: Boolean,var wishlistStatus:Boolean,
                                   var dominantColor:String?) {
    var productPrice: String? = null
        get() {
            Log.d("RelatedProducts", "getProductPrice: :------------>" + field!!)
            return field
        }

    init {
        this.productPrice = productPrice
    }
}
