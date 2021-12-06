package webkul.opencart.mobikul.handlers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.PaymentWebViewActivity
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.Constants
import webkul.opencart.mobikul.callback.ConfirmOrderCall
import webkul.opencart.mobikul.callback.GetPaymentGatewayData
import webkul.opencart.mobikul.model.ConfirmModel.Apgsenangpay
import webkul.opencart.mobikul.model.ConfirmOrderModel.ConfirmOrder
import webkul.opencart.mobikul.OrderPlaceActivity
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox

class ConfirmOrderhandler(private val mcontext: Activity) : ConfirmOrderCall, GetPaymentGatewayData {


    private var confirmOrderCallback: Callback<ConfirmOrder>? = null
    private var checked: Boolean = false
    private val Check = 200
    private var apgsenangpay: Apgsenangpay? = null
    private var state: String? = null
    private var type: String? = null
    private var confirm: webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder? = null

    fun onClickedContinue(view: View) {

        confirmOrderCallback = object : Callback<ConfirmOrder> {
            override fun onResponse(call: Call<ConfirmOrder>, response: Response<ConfirmOrder>) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response.body()!!.error != 1) {
                    val i = Intent(mcontext, OrderPlaceActivity::class.java)
                    i.putExtra("heading", response.body()!!.success!!.headingTitle)
                    i.putExtra("message", response.body()!!.success!!.textMessage)
                    (mcontext as CheckoutActivity).startActivityForResult(i, Check)
                }
            }

            override fun onFailure(call: Call<ConfirmOrder>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
        }
        Log.d("kk", " ----:---- "+type)

        if (type.equals("paytabs") && confirm != null && confirm!!.`continue`!!.payTabs != null) {
            val pref = mcontext.getSharedPreferences(Constant.PAYTABS_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("merchantId", confirm!!.`continue`!!.payTabs!!.getPtMerchantEmail())
            editor.putString("sercetKey", confirm!!.`continue`!!.payTabs!!.getPtSecretKey())
            editor.putString("orderId", confirm!!.`continue`!!.payTabs!!.getPtOrderId().toString())
            editor.apply()
            callPatTabsSdkToPay()
        } else if (type.equals("telr") && confirm != null && confirm!!.`continue`!!.apgsenangpay != null) {
            val i = Intent(mcontext, PaymentWebViewActivity::class.java)
            i.putExtra("long_url",apgsenangpay!!.url)
            i.putExtra("success_url",apgsenangpay!!.success)
            i.putExtra("fail_url",apgsenangpay!!.cancel)
            //            i.putExtra("order_id",)
            //            i.putExtra("callback_url",apgsenangpay.getCallbackUrl());
            //            i.putExtra("success_redirect_url", mainObjectForOrderReview.getJSONObject("continue").getJSONObject("paysbuy").getString("reqURL"));
            //            i.putExtra("fail_redirect_url", mainObjectForOrderReview.getJSONObject("continue").getJSONObject("paysbuy").getString("postURL"));
            mcontext.startActivity(i)
        } else {
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.confirmOrderCall(mcontext, state, RetrofitCustomCallback(confirmOrderCallback, mcontext))
        }
    }


    fun callPatTabsSdkToPay() {
        val i = Intent(mcontext, PayTabActivity::class.java)
        i.putExtra("pt_merchant_email", confirm!!.`continue`!!.payTabs!!.getPtMerchantEmail()) //this a demo account for
        i.putExtra("pt_secret_key", confirm!!.`continue`!!.payTabs!!.getPtSecretKey())
        i.putExtra("pt_transaction_title", confirm!!.`continue`!!.payTabs!!.getPtTransactionTitle())
        i.putExtra("pt_amount", confirm!!.`continue`!!.payTabs!!.getPtAmount().toDouble())
        i.putExtra("pt_currency_code", confirm!!.`continue`!!.payTabs!!.getPtCurrencyCode()) //Use Standard 3 character ISO
        i.putExtra("pt_shared_prefs_name", "myapp_shared")
        i.putExtra("pt_customer_email", confirm!!.`continue`!!.payTabs!!.getPtCustomerEmail())
        i.putExtra("pt_customer_phone_number", confirm!!.`continue`!!.payTabs!!.getPtCustomerPhoneNumber())
        i.putExtra("pt_order_id", confirm!!.`continue`!!.payTabs!!.getPtOrderId().toString())
        i.putExtra("pt_product_name", confirm!!.`continue`!!.payTabs!!.getPtProductName())
        i.putExtra("pt_timeout_in_seconds", confirm!!.`continue`!!.payTabs!!.getPtTimeoutInSeconds().toString())
        i.putExtra("pt_address_billing", confirm!!.`continue`!!.payTabs!!.getPtAddressBilling())
        i.putExtra("pt_city_billing", confirm!!.`continue`!!.payTabs!!.getPtCityBilling())
        i.putExtra("pt_state_billing", confirm!!.`continue`!!.payTabs!!.getPtStateBilling())
        i.putExtra("pt_country_billing", confirm!!.`continue`!!.payTabs!!.getPtPaymentIsoCode3())
        i.putExtra("pt_postal_code_billing", confirm!!.`continue`!!.payTabs!!.getPtPostalCodeBilling())
        i.putExtra("pt_address_shipping", confirm!!.`continue`!!.payTabs!!.getPtAddressShipping())
        i.putExtra("pt_city_shipping", confirm!!.`continue`!!.payTabs!!.getPtCityShipping())
        i.putExtra("pt_state_shipping", confirm!!.`continue`!!.payTabs!!.getPtStateShipping())
        i.putExtra("pt_country_shipping", confirm!!.`continue`!!.payTabs!!.getPtShippingIsoCode3())
        i.putExtra("pt_postal_code_shipping", confirm!!.`continue`!!.payTabs!!.getPtPostalCodeShipping()) //Put Country Phone code if Postal
        i.putExtra("pt_is_existing_customer", "no")
        i.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#E60086")
        i.putExtra(PaymentParams.THEME, PaymentParams.THEME_LIGHT)
        val requestCode = 1010
        mcontext.startActivityForResult(i, requestCode)
    }

    fun paymentMethodType(type: String) {
        this.type = type
    }

    override fun shipChecked(checked: Boolean) {
        this.checked = checked
    }


    override fun getData(confirm: webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder, orderId: String) {
        this.confirm = confirm

    }
}
