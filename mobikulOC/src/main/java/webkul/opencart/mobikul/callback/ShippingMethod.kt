package webkul.opencart.mobikul.callback

/**
 * Created by manish.choudhary on 4/8/17.
 */

interface ShippingMethod {
    fun getShippingMethod(shippingMethod: String, comment: String)
}
