package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.twoWayBindingModel.GuestCheckoutModel

/**
 * Created by manish.choudhary on 24/8/17.
 */

interface GetGuestCheckoutModel {
    fun getGuestCheckoutModel(model: GuestCheckoutModel)
    fun getShippingAddress(id: String, countryID: String, zoneID: String)
}
