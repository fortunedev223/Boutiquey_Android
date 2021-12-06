package webkul.opencart.mobikul.fragment

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.PaymethodMethodHandler
import webkul.opencart.mobikul.helper.ResponseHelper
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentPaymentMethodBinding

class PaymentMethod : Fragment(), webkul.opencart.mobikul.callback.ShippingMethod {
    private var paymentMethodBinding: FragmentPaymentMethodBinding? = null
    private var payment_method: LinearLayout? = null
    private val function = "paymentMethod"
    private var paymentMethodCallback: Callback<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>? = null
    private var shippingMethod: String? = null
    private var handler: PaymethodMethodHandler? = null
    private var comment: String? = null
    private var termsCondtion: TextView? = null
    private var paymentMethod: webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod? = null
    private val wallet: CheckBox? = null

    fun getPaymentMethod(paymentMethod: webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod) {
        this.paymentMethod = paymentMethod
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        paymentMethodBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_method, container, false)
        payment_method = paymentMethodBinding!!.paymentMethod
        handler = PaymethodMethodHandler(activity!!)
        handler!!.getPaymentBinding(paymentMethodBinding!!)
        paymentMethodBinding!!.handler = handler
        termsCondtion = paymentMethodBinding!!.termsTxt
        termsCondtion?.setOnClickListener {
            if (paymentMethod != null) {
                val mDialog = Dialog(activity!!)
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog.setContentView(R.layout.terms_and_conditions_text)
                val webView = mDialog.findViewById<View>(R.id.webView) as WebView
                webView.settings.displayZoomControls = true
                mDialog.findViewById<View>(R.id.container).layoutParams = RelativeLayout.LayoutParams(
                        Utils.getDeviceScreenWidth(),
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                webView.loadData(paymentMethod!!.paymentMethods!!.textAgreeInfo, "text/html; charset=UTF-8", null)
                mDialog.findViewById<View>(R.id.button3).setOnClickListener { mDialog.dismiss() }

                mDialog.findViewById<View>(R.id.close).setOnClickListener { mDialog.dismiss() }
                mDialog.show()
            }
        }
        val block = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding!!.paymentPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.paymentMethodImage.background = block
        val select = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.billingPipeView1.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.shippingAddressImage.background = select
        (activity as CheckoutActivity).binding!!.billingAddressImage1.background = select

        paymentMethodCallback = object : Callback<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod> {
            override fun onResponse(call: Call<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>,
                                    response: Response<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (ResponseHelper.isValidResponse(activity, response, false)) {
                    paymentMethod = response.body()
                    val radioGroup = RadioGroup(activity)
                    if (response.body()?.paymentMethods?.paymentMethods!!.size != 0) {
                        paymentMethodBinding?.errorTv?.visibility = View.GONE
                        for (i in 0 until response.body()?.paymentMethods?.paymentMethods!!.size) {
                            val radioButton = RadioButton(activity)
                            radioButton.text = response.body()!!.paymentMethods!!.paymentMethods!![i].title
                            radioButton.tag = response.body()!!.paymentMethods!!.paymentMethods!![i].code
                            radioGroup.addView(radioButton)
                        }
                        radioGroup.setOnCheckedChangeListener { group, checkedId -> handler!!.getPaymentMethod(group.findViewById<View>(checkedId).tag as String) }
                        payment_method?.addView(radioGroup)
                    } else {
                        paymentMethodBinding?.checkoutContinue?.isClickable = false
                        paymentMethodBinding?.errorTv?.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod>, t: Throwable) {

            }
        }

        if (paymentMethod != null) {
            val radioGroup = RadioGroup(activity)
            if (paymentMethod?.paymentMethods?.paymentMethods!!.size != 0) {
                for (i in 0 until paymentMethod?.paymentMethods?.paymentMethods!!.size) {
                    val radioButton = RadioButton(activity)
                    radioButton.text = paymentMethod?.paymentMethods?.paymentMethods!![i].title
                    radioButton.tag = paymentMethod?.paymentMethods?.paymentMethods!![i].code
                    radioGroup.addView(radioButton)
                }
                radioGroup.setOnCheckedChangeListener { group, checkedId ->
                    handler?.getPaymentMethod(group.findViewById<View>(checkedId).tag as String)
                }
                payment_method?.addView(radioGroup)
            } else {
                paymentMethodBinding?.checkoutContinue?.isClickable = false
                paymentMethodBinding?.checkoutContinue?.isEnabled = false
                paymentMethodBinding?.errorTv?.visibility = View.VISIBLE
            }
        } else if (shippingMethod != null) {
            SweetAlertBox().showProgressDialog(activity!!)
            RetrofitCallback.paymentMethodCheckoutCall(activity!!, function, comment!!,
                    shippingMethod!!, RetrofitCustomCallback(paymentMethodCallback, activity))
        }

        return paymentMethodBinding!!.root
    }

    override fun getShippingMethod(shippingMethod: String, comment: String) {
        this.shippingMethod = shippingMethod
        this.comment = comment
    }
}
