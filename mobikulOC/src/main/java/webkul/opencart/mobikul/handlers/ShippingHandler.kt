package webkul.opencart.mobikul.handlers

import android.content.Context
import android.view.View

import org.json.JSONObject

import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.fragment.PaymentMethod
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentShippingMethodBinding



class ShippingHandler(private val mcontext: Context) : webkul.opencart.mobikul.callback.ShippingMethod {
    private var shippingMethod: String? = null
    private var shippingMethodBinding: FragmentShippingMethodBinding? = null
    private lateinit var comment: String

    fun onClickedContinue(view: View) {
        val jo = JSONObject()
        if (shippingMethod != null) {
            if (shippingMethodBinding!!.gdprCheckbox!!.visibility == View.VISIBLE && !shippingMethodBinding!!.gdprCheckbox!!.isChecked) {
                MakeToast.instance.longToast(mcontext,"Please select GDPR Option.")
                SweetAlertBox.instance.showErrorPopUp(mcontext,"Error","Please select GDPR Option.")
            } else {
                val payment_fragment = PaymentMethod()
                payment_fragment.getShippingMethod(shippingMethod!!, if (comment != "") comment else "good")
                (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, payment_fragment, PaymentMethod::class.java.simpleName)
                        .addToBackStack(PaymentMethod::class.java.simpleName)
                        .commit()
            }
        } else {
            SweetAlertBox().showWarningPopUpPaymentMethod(mcontext, "", mcontext.resources.getString(R.string.select_shipping_method))
        }

    }


    override fun getShippingMethod(shippingMethod: String, comment: String) {
        this.shippingMethod = shippingMethod
        this.comment = comment
    }

    fun setBinding(binding: FragmentShippingMethodBinding) {
        this.shippingMethodBinding=binding
    }
}
