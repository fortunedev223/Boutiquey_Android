package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.AddrBookActivity
import webkul.opencart.mobikul.fragment.PaymentMethod
import webkul.opencart.mobikul.fragment.ShippingMethod
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.EditChangeAddressBinding


/**
Webkul Software. *
@Mobikul
@OpencartMobikul
@author Webkul
@copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
@license https://store.webkul.com/license.html
 */

class BillingHandler(private val mcontext: Context) {
    private val function = "shippingAddress"
    private val newAddress = 101
    private var shippingAddressCallback: Callback<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress>? = null
    private var paymentMethodCallback: Callback<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>? = null
    private var SHIPPING_REQUIRED: Boolean = false

    fun shipingRequired(required: Boolean) {
        SHIPPING_REQUIRED = required
    }

    fun onClickNewAddress(v: View) {
        val intent = Intent(mcontext, AddrBookActivity::class.java)
        (mcontext as CheckoutActivity).startActivityForResult(intent, newAddress)
    }

    fun onClickedContinue(view: View) {
        val ship = (mcontext as CheckoutActivity).binding?.checkoutContainer?.findViewById<CheckBox>(R.id.ship_to_this_address)
        shippingAddressCallback = object : Callback<ShippingAddress> {
            override fun onResponse(call: Call<ShippingAddress>, response: Response<ShippingAddress>) {
                SweetAlertBox.dissmissSweetAlertBox()
                val shipping_fragment = ShippingMethod()
                shipping_fragment.getAddressID(ship?.tag.toString())
                mcontext.supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, shipping_fragment, ShippingMethod::class.java.simpleName)
                        .addToBackStack(ShippingMethod::class.java.simpleName)
                        .commit()
            }

            override fun onFailure(call: Call<ShippingAddress>, t: Throwable) {

            }
        }

        if (!SHIPPING_REQUIRED && ship?.tag != null) {
            SweetAlertBox().showProgressDialog(mcontext)
            paymentMethodCallback = object : Callback<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod> {
                override fun onResponse(call: Call<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>,
                                        response: Response<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.paymentMethods != null) {
                        if (response.body()!!.paymentMethods!!.paymentMethods != null) {
                            val paymentFragment = PaymentMethod()
                            paymentFragment.getPaymentMethod(response.body()!!)
                            mcontext.supportFragmentManager.beginTransaction()
                                    .replace(R.id.checkout_container, paymentFragment,
                                            PaymentMethod::class.java.simpleName)
                                    .addToBackStack(PaymentMethod::class.java.simpleName)
                                    .commit()
                        }
                    }
                }

                override fun onFailure(call: Call<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            RetrofitCallback.shippingAddressForPaymentMethodCheckout(mcontext, function, ship.tag.toString(), "existing",
                    RetrofitCustomCallback(paymentMethodCallback, mcontext))
        } else if (ship?.tag != null && ship.isChecked) {
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.shippingAddressCheckoutCall(mcontext, function, ship.tag.toString(), RetrofitCustomCallback(shippingAddressCallback, mcontext))
        } else {
            val shippingFragment = webkul.opencart.mobikul.fragment.ShippingAddress()
            if (ship?.tag != null) {
                shippingFragment.getAddressID(ship.tag.toString())
                mcontext.supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, shippingFragment, webkul.opencart.mobikul.fragment.ShippingAddress::class.java.simpleName)
                        .addToBackStack(ShippingAddress::class.java.simpleName)
                        .commit()
            }
        }
    }

    fun onClickGetAddress(view: View) {


    }

    fun onClickEditChange(view: View) {
        var linearLayout: LinearLayout? = null
        val changeAddressBinding = EditChangeAddressBinding.inflate(LayoutInflater.from(mcontext))
        linearLayout = (mcontext as CheckoutActivity).binding!!.checkoutContainer.findViewById<View>(R.id.edit_change_address) as LinearLayout
        if (editCheck) {
            linearLayout.visibility = View.GONE
            linearLayout.removeAllViews()
            editCheck = false
        } else {
            linearLayout.visibility = View.VISIBLE
            linearLayout.addView(changeAddressBinding.root)
            editCheck = true
        }
    }

    fun onClickAddAddress(view: View) {
        Toast.makeText(mcontext, "Add Address", Toast.LENGTH_LONG).show()
    }

    companion object {
        private var editCheck = false
    }
}
