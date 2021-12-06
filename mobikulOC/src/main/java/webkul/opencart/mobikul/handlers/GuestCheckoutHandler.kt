package webkul.opencart.mobikul.handlers

import android.content.Context
import android.util.Log
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.fragment.GuestFragment
import webkul.opencart.mobikul.fragment.GuestShippingAddressFragment
import webkul.opencart.mobikul.fragment.ShippingMethod
import webkul.opencart.mobikul.callback.ShippingAddressGuest
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.twoWayBindingModel.GuestCheckoutModel
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.FragmentGuestBinding
import webkul.opencart.mobikul.databinding.FragmentGuestShippingAddressBinding

import android.content.ContentValues.TAG
import webkul.opencart.mobikul.utils.SweetAlertBox


class GuestCheckoutHandler(private val mcontext: Context) : ShippingAddressGuest {
    private var shippingMethodCallback: Callback<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>? = null
    private val function = "saveGuest"
    private var countryId: String? = null
    private var zoneId: String? = null
    private var shippingAddress: String? = null
    private var guestBinding: FragmentGuestBinding? = null

    fun onClickGuestCheckout(view: View, guestCheckoutModel: GuestCheckoutModel) {
        if (guestCheckoutModel.firstName == "" || Validation.isEmoji(guestCheckoutModel.firstName)) {
            guestBinding!!.firstname.error = mcontext.resources.getString(R.string.fname_is_required)
            guestBinding!!.firstname.requestFocus()
        } else if (guestCheckoutModel.firstName.length < 1 || guestCheckoutModel.firstName.length > 32) {
            guestBinding!!.firstname.error = mcontext.resources.getString(R.string.first_name_length)
            guestBinding!!.firstname.requestFocus()
        } else if (guestCheckoutModel.lastName == "" || Validation.isEmoji(guestCheckoutModel.lastName)) {
            guestBinding!!.lastname.error = mcontext.getString(R.string.lname_is_required)
            guestBinding!!.lastname.requestFocus()
        } else if (guestCheckoutModel.lastName.length < 1 || guestCheckoutModel.lastName.length > 32) {
            guestBinding!!.lastname.error = mcontext.resources.getString(R.string.last_name_length)
            guestBinding!!.lastname.requestFocus()
        } else if (guestBinding!!.emailAddress.text.toString() == "") {
            guestBinding!!.emailAddress.requestFocus()
            guestBinding!!.emailAddress.error = mcontext.resources.getString(R.string.fill_email_address)
        } else if (!Validation.isEmailValid(guestCheckoutModel.email)) {
            guestBinding!!.emailAddress.requestFocus()
            guestBinding!!.emailAddress.error = mcontext.resources.getString(R.string.enter_valid_email)
        } else if (guestCheckoutModel.telephone == "") {
            guestBinding!!.telephone.error = mcontext.resources.getString(R.string.telephone_is_required)
            guestBinding!!.telephone.requestFocus()
        } else if (guestCheckoutModel.telephone.length < 3 || guestCheckoutModel.telephone.length > 32) {
            guestBinding!!.telephone.error = mcontext.resources.getString(R.string.number_error)
            guestBinding!!.telephone.requestFocus()
        } else if (guestCheckoutModel.city == "" || Validation.isEmoji(guestCheckoutModel.city)) {
            guestBinding!!.addBookCityValue.error = mcontext.resources.getString(R.string.city_address_is_required)
            guestBinding!!.addBookCityValue.requestFocus()
        } else if (guestCheckoutModel.city.length < 2 || guestCheckoutModel.city.length > 128) {
            guestBinding!!.addBookCityValue.error = mcontext.resources.getString(R.string.city_length)
            guestBinding!!.addBookCityValue.requestFocus()
        } else if (guestCheckoutModel.address1 == "" || Validation.isEmoji(guestCheckoutModel.address1)) {
            guestBinding!!.addBookStreetAddValue.error = mcontext.resources.getString(R.string.street_address_is_required)
            guestBinding!!.addBookStreetAddValue.requestFocus()
        } else if (guestCheckoutModel.address1.length < 3 || guestCheckoutModel.address1.length > 128) {
            guestBinding!!.addBookStreetAddValue.error = mcontext.resources.getString(R.string.address_length)
            guestBinding!!.addBookStreetAddValue.requestFocus()
        } else if (guestCheckoutModel.zip == "" || Validation.isEmoji(guestCheckoutModel.zip)) {
            guestBinding!!.addBookZipValue.error = mcontext.resources.getString(R.string.zip_is_required)
            guestBinding!!.addBookZipValue.requestFocus()
        } else if (guestCheckoutModel.zip.length < 1 || guestCheckoutModel.zip.length > 10) {
            guestBinding!!.addBookZipValue.error = mcontext.resources.getString(R.string.postcode_length)
            guestBinding!!.addBookZipValue.requestFocus()
        } else {
            shippingMethodCallback = object : Callback<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod> {
                override fun onResponse(call: Call<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>,
                                        response: Response<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    val shipping_fragment = ShippingMethod()
                    shipping_fragment.getAddressID("guest")
                    (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.checkout_container, shipping_fragment, ShippingMethod::class.java.simpleName)
                            .addToBackStack(ShippingMethod::class.java.simpleName)
                            .commit()
                    shipping_fragment.getGuestShippingMethod(response.body()!!)
                }

                override fun onFailure(call: Call<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            if (shippingAddress != null && shippingAddress == "1") {
                SweetAlertBox.instance.showProgressDialog(mcontext)
                Log.d(TAG, "onClickGuestCheckout: " + guestCheckoutModel.email + " " + guestCheckoutModel.telephone + " " + guestCheckoutModel.fax + " " +
                        guestCheckoutModel.firstName + " " + guestCheckoutModel.lastName + " " + guestCheckoutModel.company + " " + guestCheckoutModel.address1 + " " + guestCheckoutModel.address2 + " " + guestCheckoutModel.city + " " + guestCheckoutModel.zip + " " + countryId + " " + zoneId)
                RetrofitCallback.guestCheckoutForShippingMethod(mcontext, shippingAddress!!, function, guestCheckoutModel.email, guestCheckoutModel.telephone, guestCheckoutModel.fax,
                        guestCheckoutModel.firstName, guestCheckoutModel.lastName, guestCheckoutModel.company, guestCheckoutModel.address1, guestCheckoutModel.address2, guestCheckoutModel.city, guestCheckoutModel.zip, countryId!!, zoneId!!, RetrofitCustomCallback(shippingMethodCallback, mcontext))
            } else {
                val guest_Shipping_fragment = GuestShippingAddressFragment()
                guest_Shipping_fragment.getGuestCheckoutModel(guestCheckoutModel)
                guest_Shipping_fragment.getShippingAddress(shippingAddress!!, countryId!!, zoneId!!)
                val transaction = (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                mcontext.supportFragmentManager.findFragmentByTag(GuestFragment::class.java.simpleName)?.let { transaction.hide(it) }
                transaction.add(R.id.checkout_container, guest_Shipping_fragment, GuestShippingAddressFragment::class.java.simpleName)
                        .addToBackStack(GuestShippingAddressFragment::class.java.simpleName)
                        .commit()
            }
        }
    }

    override fun getShippingAddress(check: String) {
        shippingAddress = check
    }

    override fun getCountryId(countryId: String) {
        this.countryId = countryId
    }

    override fun getZoneId(zoneId: String) {
        this.zoneId = zoneId
    }

    override fun getBinding(guestBinding: FragmentGuestBinding) {
        this.guestBinding = guestBinding
    }

    override fun getGuestShippingFragmentBinding(guestShippingAddressBinding: FragmentGuestShippingAddressBinding) {

    }
}
