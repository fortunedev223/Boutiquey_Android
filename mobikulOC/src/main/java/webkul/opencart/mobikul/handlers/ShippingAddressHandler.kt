package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.view.View

import webkul.opencart.mobikul.AddrBookActivity
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.fragment.ShippingMethod
import webkul.opencart.mobikul.callback.GetAddress
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentShippingAddressBinding

/**
 * Created by manish.choudhary on 19/8/17.
 */

class ShippingAddressHandler(private val mcontext: Context) : GetAddress {
    private lateinit var addressId: String
    private val newAddress = 101
    private var addressBinding: FragmentShippingAddressBinding? = null
    fun onClickNewAddress(v: View) {
        val intent = Intent(mcontext, AddrBookActivity::class.java)
        (mcontext as CheckoutActivity).startActivityForResult(intent, newAddress)
    }

    fun onClickedContinue(view: View) {
        if (addressBinding!!.gdprCheckbox.visibility == View.VISIBLE && !addressBinding!!.gdprCheckbox.isChecked) {
//            MakeToast.instance.longToast(mContext, "Please select GDPR option.")
            SweetAlertBox.instance.showErrorPopUp(mcontext,"Error","Please select GDPR option.")
        } else {
            if (addressId != null) {
                val shipping_fragment = ShippingMethod()
                shipping_fragment.getAddressID(addressId)
                (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, shipping_fragment, "shippingMethod")
                        .addToBackStack("shippingMethod")
                        .commit()
            }
        }

    }

    override fun getAddressResponse(response: String) {
        addressId = response
    }

    fun setBinding(addressBinding: FragmentShippingAddressBinding) {
        this.addressBinding = addressBinding
    }
}
