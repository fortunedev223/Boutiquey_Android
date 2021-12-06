package webkul.opencart.mobikul.callback

/**
 * Created by manish.choudhary on 4/8/17.
 */

interface BillingAddressId {
    fun getAddressID(id: String)
    fun getGuestShippingMethod(shippingMethod: webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod)
}
