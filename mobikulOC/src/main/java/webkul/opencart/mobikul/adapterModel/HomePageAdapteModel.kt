package webkul.opencart.mobikul.adapterModel

import webkul.opencart.mobikul.helper.Utils

class HomePageAdapteModel(var imageUrl: String?, var price: String?, product: String, var productId: String?,
                          var specialPrice: String?, var formattedSpecial: String, var isHasOption: Boolean,
                          var wishlist_status: Boolean?,
                          var dominiantColor: String?) {
    var product: String? = null
        get() = Utils.fromHtml(field).toString()

    init {
        this.product = product
    }
}
