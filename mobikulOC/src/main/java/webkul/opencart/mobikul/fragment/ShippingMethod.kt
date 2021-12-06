package webkul.opencart.mobikul.fragment

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.ShippingHandler
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.callback.BillingAddressId
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.FragmentShippingMethodBinding

import android.content.ContentValues.TAG
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Window
import android.webkit.WebView
import android.widget.*
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel
import webkul.opencart.mobikul.utils.AppSharedPreference


class ShippingMethod : Fragment(), BillingAddressId {
    var binding: FragmentShippingMethodBinding? = null
        private set
    private var shippingMethodLl: LinearLayout? = null
    private var shippingMethodCostLL: LinearLayout? = null
    private var shippingAddressCallback: Callback<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>? = null
    private var gdprStatus: Callback<GdprModel>? = null
    private val function = "shippingMethod"
    private var addressID: String? = null
    private var shippingMethod: webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod? = null
    private var handler: ShippingHandler? = null
    private var group: RadioGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shipping_method, container, false)
        group = RadioGroup(activity)
        shippingMethodLl = binding!!.shippingMethodLl
        shippingMethodCostLL = binding!!.shippingMethodCostLl
        handler = ShippingHandler(activity!!)
        binding!!.handler = handler
        handler!!.setBinding(binding!!)
        val shared = activity!!.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        val select = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.billingPipeView1.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.shippingAddressImage.background = select
        (activity as CheckoutActivity).binding!!.billingAddressImage1.background = select

        shippingAddressCallback = object : Callback<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod> {
            override fun onResponse(call: Call<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>,
                                    response: Response<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>) {
                SweetAlertBox.dissmissSweetAlertBox()
                shippingMethod = response.body()
                if (context != null && AppSharedPreference.getGdprStatus(context!!) == "1" && response.body()?.shippingMethods?.gdprStatus == 1) {
                    binding?.gdprCheckbox?.visibility = View.VISIBLE
                    setGdprSpannable(response.body()?.shippingMethods?.gdprContent)
                } else {
                    binding?.gdprCheckbox?.visibility = View.GONE
                }
                if (isAdded) {
                    setShippingMethod(shippingMethod)
                }
            }

            override fun onFailure(call: Call<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>, t: Throwable) {
            }
        }
        if (shippingMethod != null) {
            if (isAdded) {
                setShippingMethod(shippingMethod)
            }
        }
        if (addressID != null) {
            SweetAlertBox().showProgressDialog(activity!!)
            RetrofitCallback.shippingMethodCheckoutCall(activity!!, function, addressID!!, RetrofitCustomCallback(shippingAddressCallback, activity))
        }
        return binding!!.root
    }

    private fun setGdprSpannable(mobikulGdprShippingMethodDescription: String?) {
        val gdprValue = resources.getString(R.string.gdpr_check_value)
        val ss = SpannableString(gdprValue)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val mDialog = Dialog(activity)
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog.setContentView(R.layout.terms_and_conditions_text)
                val webView = mDialog.findViewById<View>(R.id.webView) as WebView
                webView.settings.displayZoomControls = true
                mDialog.findViewById<View>(R.id.container).layoutParams = RelativeLayout.LayoutParams(
                        webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                        webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight())
                mDialog.findViewById<View>(R.id.close).setOnClickListener { mDialog.dismiss() }
                try {
                    webView.loadData(mobikulGdprShippingMethodDescription, "text/html; charset=UTF-8", null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                mDialog.findViewById<View>(R.id.button3).setOnClickListener { mDialog.dismiss() }
                mDialog.show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 32, gdprValue.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding!!.gdprCheckbox.setText(ss)
        binding!!.gdprCheckbox.setMovementMethod(LinkMovementMethod.getInstance())
    }

    private fun setShippingMethod(shippingMethod: webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod?) {
        if (shippingMethod!!.shippingMethods != null) {
            Log.d(TAG, "setShippingMethod: $group$shippingMethodLl")
            if (shippingMethod.shippingMethods?.shippingMethods == null) {
                binding?.checkoutContinue?.isClickable = false
                binding?.errorTv?.visibility = View.VISIBLE
            } else {
                if (shippingMethod.shippingMethods!!.shippingMethods!!.size == 0) {
                    Log.d(TAG, "setShippingMethod:" + "========>" + shippingMethod.shippingMethods!!.shippingMethods!!.size)
                    binding?.checkoutContinue?.isClickable = false
                    binding?.checkoutContinue?.isEnabled = false
                    binding?.errorTv?.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "setShippingMethod:" + "========>" + shippingMethod.shippingMethods!!.shippingMethods!!.size)
                    binding?.checkoutContinue?.isClickable = true
                    binding?.errorTv?.visibility = View.GONE
                    for (i in 0 until shippingMethod.shippingMethods!!.shippingMethods!!.size) {
                        for (j in 0 until shippingMethod.shippingMethods!!.shippingMethods!![i].quote!!.size) {
                            val radio = RadioButton(activity)
                            radio.text = shippingMethod.shippingMethods!!.shippingMethods!![i].quote!![j].title
                            radio.tag = shippingMethod.shippingMethods!!.shippingMethods!![i].quote!![j].code
                            group?.addView(radio)
                            group?.setOnCheckedChangeListener { group, checkedId ->
                                handler?.getShippingMethod(group.findViewById<View>(checkedId).tag as String, binding!!.comment.text.toString())
                            }
                        }
                    }
                }

                if (shippingMethodLl!!.parent != null) {
                    shippingMethodLl!!.removeAllViews()
                    shippingMethodLl!!.addView(group)
                } else {
                    shippingMethodLl!!.addView(group)
                }

                for (i in 0 until shippingMethod.shippingMethods!!.shippingMethods!!.size) {
                    for (j in 0 until shippingMethod.shippingMethods!!.shippingMethods!![i].quote!!.size) {
                        val textView = TextView(activity)
                        textView.textSize = 15f
                        textView.gravity = View.TEXT_ALIGNMENT_TEXT_END
                        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        params.setMargins(5, 15, 5, 5)
                        textView.layoutParams = params
                        textView.text = shippingMethod.shippingMethods!!.shippingMethods!![i].quote!![j].text
                        shippingMethodCostLL!!.addView(textView)
                    }
                }

            }
        }
    }


    override fun getAddressID(id: String) {
        addressID = id
        if (id == "guest") {
            addressID = null
        }
    }

    override fun getGuestShippingMethod(shippingMethod: webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod) {
        this.shippingMethod = shippingMethod
    }

    override fun onPause() {
        super.onPause()
        shippingMethod = null
    }

    override fun onStop() {
        super.onStop()
        shippingMethod = null
    }
}
