package webkul.opencart.mobikul

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.*
import com.paytabs.paytabs_sdk.utils.PaymentParams
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.fragment.GuestFragment
import webkul.opencart.mobikul.fragment.PaymentAddress
import webkul.opencart.mobikul.handlers.CheckoutHandler
import webkul.opencart.mobikul.databinding.ActivityCheckout2Binding
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.ConfirmModel.PayTabsResponse
import webkul.opencart.mobikul.model.ConfirmOrderModel.ConfirmOrder
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox

/**
Webkul Software. *
@Mobikul
@OpencartMobikul
@author Webkul
@copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
@license https://store.webkul.com/license.html
 */
class CheckoutActivity : BaseActivity() {
    private var actionBar: ActionBar? = null
    var binding: ActivityCheckout2Binding? = null
        private set
    private var billingAddress: ImageView? = null
    private var shippingAddress: ImageView? = null
    private var shippingMethod: ImageView? = null
    private var paymentMethod: ImageView? = null
    private var confirmOrder: ImageView? = null
    private var title: TextView? = null
    private val newAddress = 101
    private val newAddressReturn = 201
    private val Check = 200
    private var payTabCallback: Callback<PayTabsResponse>? = null
    private var confirmOrderCallback: Callback<ConfirmOrder>? = null


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        val select = AppCompatResources.getDrawable(this, R.drawable.checkout_selected)
        val unSelect = AppCompatResources.getDrawable(this, R.drawable.checkout_unselected)
        val step5 = AppCompatResources.getDrawable(this, R.drawable.ic_checkout_step_notselected_5)
        if (count == 1) {
            this.finish()
        } else {
            when (count) {
                5 -> {
                    supportFragmentManager.popBackStack()
                    binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
                    binding!!.paymentPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
                    binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.billingAddressImage.background = select
                    binding!!.shippingAddressImage.background = select
                    binding!!.paymentMethodImage.background = unSelect
                    binding!!.confirmOrderImage.background = unSelect
                }
                4 -> {
                    supportFragmentManager.popBackStack()
                    binding!!.billingAddressImage.background = select
                    binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
                    binding!!.shippingAddressImage.background = select
                    binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.paymentMethodImage.background = unSelect
                    binding!!.confirmOrderImage.background = unSelect
                }
                3 -> {
                    supportFragmentManager.popBackStack()
                    binding!!.billingPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.billingPipeView1.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.billingAddressImage.background = select
                    binding!!.shippingAddressImage.background = unSelect
                    binding!!.paymentMethodImage.background = unSelect
                    binding!!.confirmOrderImage.background = unSelect
                }
                2 -> {
                    supportFragmentManager.popBackStack()
                    binding!!.billingPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.paymentPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.confirmPipeView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.billingAddressImage1.background = unSelect
                    binding!!.billingPipeView1.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray_color1))
                    binding!!.billingAddressImage.background = select
                    binding!!.shippingAddressImage.background = unSelect
                    binding!!.paymentMethodImage.background = unSelect
                    binding!!.confirmOrderImage.background = unSelect
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout2)
        hideSoftKeyboard(binding!!.root)
        toolbarLoginActivity = findViewById<View>(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbarLoginActivity)
        actionBar = supportActionBar
        title = binding?.toolbar?.findViewById<View>(R.id.title) as TextView?
        title?.text = getString(R.string.checkout)
        billingAddress = binding?.billingAddressImage
        shippingAddress = binding?.billingAddressImage1
        shippingMethod = binding?.shippingAddressImage
        paymentMethod = binding?.paymentMethodImage
        confirmOrder = binding?.confirmOrderImage
        billingAddress?.setPadding(10, 10, 10, 10)
        shippingAddress?.setPadding(10, 10, 10, 10)
        shippingMethod?.setPadding(10, 10, 10, 10)
        paymentMethod?.setPadding(10, 10, 10, 10)
        confirmOrder?.setPadding(10, 10, 10, 10)
        val params = LinearLayout.LayoutParams(
                (Utils.getDeviceScreenWidth() / 14.4).toInt(),
                (Utils.getDeviceScreenWidth() / 14.4).toInt())
        billingAddress?.layoutParams = params
        shippingAddress?.layoutParams = params
        shippingMethod?.layoutParams = params
        paymentMethod?.layoutParams = params
        confirmOrder?.layoutParams = params
        if (actionBar != null) {
            actionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setTitle(resources.getString(R.string.checkout))
        binding?.handler = CheckoutHandler(this@CheckoutActivity)
        if (intent.hasExtra("check")) {
            val guestFragment = GuestFragment()
            supportFragmentManager.beginTransaction()
                    .add(R.id.checkout_container, guestFragment, GuestFragment::class.java.simpleName)
                    .addToBackStack(GuestFragment::class.java.simpleName)
                    .commit()
        } else {
            val billingFragment = PaymentAddress()
            supportFragmentManager.beginTransaction()
                    .add(R.id.checkout_container, billingFragment, PaymentAddress::class.java.simpleName)
                    .addToBackStack(PaymentAddress::class.java.simpleName)
                    .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newAddress && resultCode == Activity.RESULT_OK) {
            this.finish()
            startActivity(intent)
        }
        if (requestCode == newAddressReturn) {
            this.finish()
            startActivity(intent)
        }
        if (resultCode == Check) {
            val intent = intent
            finish()
            startActivity(intent)
        }

        if (requestCode == 1010) {
            super.onActivityResult(requestCode, resultCode, data)
            val shared_prefs = getSharedPreferences("myapp_shared", Context.MODE_PRIVATE)
            val pt_response_code = shared_prefs.getString("pt_response_code", "")
            val pt_transaction_id = data!!.getStringExtra(PaymentParams.TRANSACTION_ID)
            payTabCallback = object : Callback<PayTabsResponse> {
                override fun onResponse(call: Call<PayTabsResponse>, response: Response<PayTabsResponse>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.getResponse_code().equals("100")) {
                        RetrofitCallback.confirmOrderCall(this@CheckoutActivity, "1", RetrofitCustomCallback(confirmOrderCallback, this@CheckoutActivity))
                    } else {
                        RetrofitCallback.confirmOrderCall(this@CheckoutActivity, "0", RetrofitCustomCallback(confirmOrderCallback, this@CheckoutActivity))
                    }
                }

                override fun onFailure(call: Call<PayTabsResponse>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }

            confirmOrderCallback = object : Callback<ConfirmOrder> {
                override fun onResponse(call: Call<ConfirmOrder>, response: Response<ConfirmOrder>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.error != 1) {
                        val i = Intent(this@CheckoutActivity, OrderPlaceActivity::class.java)
                        i.putExtra("heading", response.body()!!.success!!.headingTitle)
                        i.putExtra("message", response.body()!!.success!!.textMessage)
                        startActivityForResult(i, Check)
                    } else {
                        Toast.makeText(this@CheckoutActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ConfirmOrder>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }

            SweetAlertBox().showProgressDialog(this)
            RetrofitCallback.verifyTransactionCall(this, AppSharedPreference.getPayTabsData(this, "merchantId"),
                    AppSharedPreference.getPayTabsData(this, "sercetKey"),
                    data!!.getStringExtra(PaymentParams.TRANSACTION_ID), data!!.getStringExtra(PaymentParams.ORDER_ID),
                    payTabCallback as Callback<PayTabsResponse>)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }
}
