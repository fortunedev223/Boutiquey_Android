package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.databinding.FragmentGuestBinding
import webkul.opencart.mobikul.databinding.FragmentGuestShippingAddressBinding

/**
 * Created by manish.choudhary on 18/8/17.
 */

interface ShippingAddressGuest {
    fun getShippingAddress(check: String)
    fun getCountryId(countryId: String)
    fun getZoneId(zoneId: String)
    fun getBinding(guestBinding: FragmentGuestBinding)
    fun getGuestShippingFragmentBinding(guestShippingAddressBinding: FragmentGuestShippingAddressBinding)
}
