package webkul.opencart.mobikul.handlers

import android.content.Context
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.callback.ShippingAddressGuest
import webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.twoWayBindingModel.GuestCheckoutModel
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.FragmentGuestBinding
import webkul.opencart.mobikul.databinding.FragmentGuestShippingAddressBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class GuestShippingAddressHandler(private val mcontext: Context) : ShippingAddressGuest {
    private var countryId: String? = null
    private var zoneId: String? = null
    private val function = "saveGuestShipping"
    private var shippingMethodCallback: Callback<ShippingMethod>? = null
    private var guestShippingAddressBinding: FragmentGuestShippingAddressBinding? = null

    fun onClickGuestShippingAddressCheckout(view: View, model: GuestCheckoutModel) {

        shippingMethodCallback = object : Callback<ShippingMethod> {
            override fun onResponse(call: Call<ShippingMethod>, response: Response<ShippingMethod>) {
                SweetAlertBox.dissmissSweetAlertBox()
                val shipping_fragment = webkul.opencart.mobikul.fragment.ShippingMethod()
                shipping_fragment.getAddressID("guest")
                (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, shipping_fragment,
                                webkul.opencart.mobikul.fragment.ShippingMethod::class.java.simpleName)
                        .addToBackStack(webkul.opencart.mobikul.fragment.ShippingMethod::class.java.simpleName)
                        .commit()
                shipping_fragment.getGuestShippingMethod(response.body()!!)
            }

            override fun onFailure(call: Call<ShippingMethod>, t: Throwable) {

            }
        }
        if (guestShippingAddressBinding != null) {
            if (model.firstName == "" || Validation.isEmoji(model.firstName)) {
                guestShippingAddressBinding!!.firstname.error = mcontext.resources.getString(R.string.fname_is_required)
                guestShippingAddressBinding!!.firstname.requestFocus()
            } else if (model.firstName.length < 1 || model.firstName.length > 32) {
                guestShippingAddressBinding!!.firstname.error = mcontext.resources.getString(R.string.first_name_length)
                guestShippingAddressBinding!!.firstname.requestFocus()
            } else if (model.lastName == "" || Validation.isEmoji(model.lastName)) {
                guestShippingAddressBinding!!.lastname.error = mcontext.resources.getString(R.string.lname_is_required)
                guestShippingAddressBinding!!.lastname.requestFocus()
            } else if (model.lastName.length < 1 || model.lastName.length > 32) {
                guestShippingAddressBinding!!.lastname.error = mcontext.resources.getString(R.string.last_name_length)
                guestShippingAddressBinding!!.lastname.requestFocus()
            } else if (!Validation.isEmailValid(model.email)) {
                guestShippingAddressBinding!!.emailAddress.requestFocus()
                guestShippingAddressBinding!!.emailAddress.error = mcontext.resources.getString(R.string.enter_valid_email)
            } else if (model.telephone == "") {
                guestShippingAddressBinding!!.telephone.error = mcontext.resources.getString(R.string.telephone_is_required)
                guestShippingAddressBinding!!.telephone.requestFocus()
            } else if (model.telephone.length < 3 || model.telephone.length > 32) {
                guestShippingAddressBinding!!.telephone.error = mcontext.resources.getString(R.string.number_error)
                guestShippingAddressBinding!!.telephone.requestFocus()
            } else if (model.city == "" || Validation.isEmoji(model.city)) {
                guestShippingAddressBinding!!.addBookCityValue.error = mcontext.resources.getString(R.string.city_address_is_required)
                guestShippingAddressBinding!!.addBookCityValue.requestFocus()
            } else if (model.city.length < 2 || model.city.length > 128) {
                guestShippingAddressBinding!!.addBookCityValue.error = mcontext.resources.getString(R.string.city_length)
                guestShippingAddressBinding!!.addBookCityValue.requestFocus()
            } else if (model.address1 == "" || Validation.isEmoji(model.address1)) {
                guestShippingAddressBinding!!.addBookStreetAddValue.error = mcontext.resources.getString(R.string.street_address_is_required)
                guestShippingAddressBinding!!.addBookStreetAddValue.requestFocus()
            } else if (model.address1.length < 3 || model.address1.length > 128) {
                guestShippingAddressBinding!!.addBookStreetAddValue.error = mcontext.resources.getString(R.string.address_length)
                guestShippingAddressBinding!!.addBookStreetAddValue.requestFocus()
            } else if (model.zip == "" || Validation.isEmoji(model.zip)) {
                guestShippingAddressBinding!!.addBookZipValue.error = mcontext.resources.getString(R.string.zip_is_required)
                guestShippingAddressBinding!!.addBookZipValue.requestFocus()
            } else if (model.zip.length < 1 || model.zip.length > 10) {
                guestShippingAddressBinding!!.addBookZipValue.error = mcontext.resources.getString(R.string.postcode_length)
                guestShippingAddressBinding!!.addBookZipValue.requestFocus()
            } else if (countryId != null && zoneId != null) {
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.guestCheckoutForShippingMethod(mcontext, "", function, model.email, model.telephone, model.fax, model.firstName, model.lastName, model.company, model.address1, model.address2, model.city, model.zip, countryId!!, zoneId!!, RetrofitCustomCallback(shippingMethodCallback, mcontext))
            }
        }

    }

    override fun getShippingAddress(check: String) {

    }

    override fun getCountryId(countryId: String) {
        this.countryId = countryId
    }

    override fun getZoneId(zoneId: String) {
        this.zoneId = zoneId
    }

    override fun getBinding(guestBinding: FragmentGuestBinding) {

    }

    override fun getGuestShippingFragmentBinding(guestShippingAddressBinding: FragmentGuestShippingAddressBinding) {
        this.guestShippingAddressBinding = guestShippingAddressBinding
    }
}
