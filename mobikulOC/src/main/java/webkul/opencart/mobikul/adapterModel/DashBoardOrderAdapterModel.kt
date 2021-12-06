package webkul.opencart.mobikul.adapterModel

import android.content.Context

import webkul.opencart.mobikul.R

/**
 * Created by manish.choudhary on 30/6/17.
 */

class DashBoardOrderAdapterModel(private val mcontext: Context, orderId: String, var name: String?, var status: String?, dateAdded: String, private var products: Int, total: String) {

    var orderId: String? = null
        get() = mcontext.resources.getString(R.string.order_id) + " #" + field
    var dateAdded: String? = null
        get() = mcontext.resources.getString(R.string.placed_on) + " " + field
    var total: String? = null
        get() = mcontext.resources.getString(R.string.total_amount) + " " + field

    fun getProducts(): String {
        return mcontext.resources.getString(R.string.total_products) + " :  " + products
    }

    fun setProducts(products: Int) {
        this.products = products
    }

    init {
        this.orderId = orderId
        this.dateAdded = dateAdded
        this.total = total
    }
}
