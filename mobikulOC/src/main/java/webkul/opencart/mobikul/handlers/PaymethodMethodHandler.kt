package webkul.opencart.mobikul.handlers

import android.content.Context
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.callback.PaymentMethod
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentPaymentMethodBinding
import webkul.opencart.mobikul.helper.Utils


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class PaymethodMethodHandler(private val mcontext: Context) : PaymentMethod {
    var code: String? = null
    private val funtion = "confirm"
    private var confirmOrderCallback: Callback<ConfirmOrder>? = null
    private var paymentMethodBinding: FragmentPaymentMethodBinding? = null

    fun onClickedContinue(view: View) {
        confirmOrderCallback = object : Callback<ConfirmOrder> {
            override fun onResponse(call: Call<ConfirmOrder>, response: Response<ConfirmOrder>) {
                SweetAlertBox.dissmissSweetAlertBox()
                val confirmOrder = webkul.opencart.mobikul.fragment.ConfirmOrder()
                confirmOrder.getConfirmOrder(response.body()!!)
                confirmOrder.getPaymentcode(code!!)
                (mcontext as CheckoutActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.checkout_container, confirmOrder,
                                webkul.opencart.mobikul.fragment.ConfirmOrder::class.java.simpleName)
                        .addToBackStack(webkul.opencart.mobikul.fragment.ConfirmOrder::class.java.simpleName)
                        .commit()
            }

            override fun onFailure(call: Call<ConfirmOrder>, t: Throwable) {

            }
        }

        val comment = paymentMethodBinding!!.comment.text.toString()

        if (code != null) {
            if (paymentMethodBinding!!.termsCondition.isChecked) {
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.confirmCheckoutCall(mcontext, funtion, Utils.getDeviceScreenWidth().toString(), code!!,
                        if (comment != "") comment else "good", "1", RetrofitCustomCallback(confirmOrderCallback, mcontext))
            } else {
                SweetAlertBox().showWarningPopUpPaymentMethod(mcontext, "", mcontext.resources.getString(R.string.warning_terms_condition))
            }
        } else {
            SweetAlertBox().showWarningPopUpPaymentMethod(mcontext, "", mcontext.resources.getString(R.string.select_payment_method))
        }
    }

    override fun getPaymentMethod(code: String) {
        this.code = code
    }

    override fun getPaymentBinding(paymentMethodBinding: FragmentPaymentMethodBinding) {
        this.paymentMethodBinding = paymentMethodBinding
    }
}
