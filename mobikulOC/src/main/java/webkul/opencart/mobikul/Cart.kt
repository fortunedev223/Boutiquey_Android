package webkul.opencart.mobikul

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView

import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth

import org.json.JSONObject

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.CartAdapter
import webkul.opencart.mobikul.adapterModel.CartAdapterModel
import webkul.opencart.mobikul.adapterModel.CartTotalAdapterModel
import webkul.opencart.mobikul.handlers.CartHandler
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.SocailLoginModel.SocailLogin
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.databinding.ActivityCartBinding
import webkul.opencart.mobikul.databinding.ItemCartBinding
import webkul.opencart.mobikul.databinding.ItemCartTotalBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class Cart : BaseActivity() {
    internal var screen_width: Int = 0
    var isInternetAvailableCart: Boolean = false
    var child: ItemCartBinding? = null
    private var mMobikulApplicationCart: MobikulApplication? = null
    private var actionBar: ActionBar? = null
    internal lateinit var toolbarCart: Toolbar
    lateinit var binding: ActivityCartBinding
    internal lateinit var dataholder: CartAdapterModel
    internal lateinit var data: ArrayList<CartAdapterModel>
    lateinit var cartadapter: CartAdapter
    internal lateinit var recyclerview: RecyclerView
    private var cartCallback: Callback<ViewCart>? = null
    private var title: TextView? = null
    private val CART_RESULT = 10
    private var mCallbackManager: CallbackManager? = null
    private var socailLoginCallback: retrofit2.Callback<SocailLogin>? = null
    private var rewardCallback: Callback<BaseModel>? = null
    var handler: CartHandler? = null

    fun isOnlineCart():Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailableCart = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
        return isInternetAvailableCart
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnline()
        if (!isOnlineCart()) {
            showDialog(this)
        } else {
            mCallbackManager = CallbackManager.Factory.create()
            binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
            hideSoftKeyboard(binding.root)
            mMobikulApplicationCart = application as MobikulApplication
            val extras = intent.extras
            if (extras != null) {
                if (intent.extras!!.containsKey("calledByCart")) {
                    calledByCart = true
                }
            }
            toolbarCart = binding.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbarCart)
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            title = binding.toolbar!!.findViewById<View>(R.id.title) as TextView
            title!!.text = getString(R.string.cart)
            actionBar = supportActionBar
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            screen_width = size.x
            rewardCallback = object : Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    binding.rewardEdittxt.setText("")
                    MakeToast().shortToast(this@Cart, response.body()!!.message)
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.error == 0) {
                        val intent = intent
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    binding.rewardEdittxt.setText("")
                }
            }
            binding.scrollViewMainContainer.visibility = View.GONE
            binding.rewardButton.setOnClickListener {
                val reward = binding.rewardEdittxt.text.toString()
                if (reward != null) {
                    SweetAlertBox().showProgressDialog(this@Cart)
                    RetrofitCallback.getRewardPointCall(this@Cart, reward, RetrofitCustomCallback(rewardCallback, this@Cart))
                }
            }
            socailLoginCallback = object : Callback<SocailLogin> {
                override fun onResponse(call: Call<SocailLogin>, response: Response<SocailLogin>) {
                    if (SweetAlertBox.sweetAlertDialog != null) {
                        SweetAlertBox.dissmissSweetAlertBox()
                    }
                    if (response.body()?.error == 1) {
                        MakeToast().shortToast(this@Cart, response.body()!!.message)
                    } else {
                        AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, response.body()?.cartTotal)
                        AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_EMAIL, response.body()?.email)
                        AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_ID, response.body()?.customerId)
                        AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_NAME, response.body()?.firstname)
                        AppSharedPreference.editBooleanSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_IS_LOGGED_IN, true)
                    }
                }

                override fun onFailure(call: Call<SocailLogin>, t: Throwable) {

                }
            }
            cartCallback = object : Callback<ViewCart> {
                override fun onResponse(call: Call<ViewCart>, response: Response<ViewCart>) {
                    setCartData(response.body())
                }

                override fun onFailure(call: Call<ViewCart>, t: Throwable) {

                }
            }
            RetrofitCallback.viewCartCall(this@Cart, screen_width.toString(), RetrofitCustomCallback(cartCallback, this@Cart))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCartData(cartData: ViewCart?) {
        recyclerview = binding.cartRecycler
        recyclerview.layoutManager = LinearLayoutManager(this@Cart)
        recyclerview.isNestedScrollingEnabled = false
        data = ArrayList()
        val cartbinding = ItemCartBinding.inflate(layoutInflater)
        binding.proceedToCheckout2.visibility = View.VISIBLE
        val errorTv = binding.errorTv
        val emptyCart = binding.emptyCart
        errorTv.textSize = 14f
        GUEST_STATUS=cartData?.cart?.guestStatus
        if (cartData?.cart != null) {
            if (cartData?.cart?.totalProducts != null) {
                AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                        Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS,
                        cartData?.cart?.totalProducts.toString())
            } else {
                AppSharedPreference.addCartItems(this@Cart, cartData?.cart?.cartCount.toString())
            }
        }
        if (cartData?.cart?.voucherStatus != null && cartData?.cart?.voucherStatus == 1) {
            binding.voucherContainer?.visibility = View.VISIBLE
        }

        if (cartData?.cart?.couponStatus != null && cartData?.cart?.couponStatus == 1) {
            binding.couponContainer?.visibility = View.VISIBLE
        }

        DOWNLOADABLE_PRODUCT_STATUS = cartData?.cart?.downloadProduct
        if (cartData?.reward != null) {
            if (cartData?.reward!!.reward != null) {
                if (cartData?.reward!!.reward!!.length >= 2) {
                    binding.rewardCard.visibility = View.VISIBLE
                }
            }
        }

        if (cartData?.cart?.errorWarning != null) {
            if (cartData.cart?.errorWarning?.length!! > 3) {
                binding.proceedToCheckout2.isClickable = false
                binding.proceedToCheckout2.setBackgroundColor(ContextCompat.getColor(this@Cart, R.color.light_gray))
                binding.outOfStockError.visibility = View.VISIBLE
                binding.outOfStockError.text = Html.fromHtml(cartData.cart?.errorWarning!!).toString()
            } else {
                binding.proceedToCheckout2.isClickable = true
                binding.proceedToCheckout2.setBackgroundColor(ContextCompat.getColor(this@Cart, R.color.accent_color))
                binding.outOfStockError.visibility = View.GONE
            }
        }

        if (cartData?.cart?.products != null) {
            if (cartData.cart?.products?.isNotEmpty()!!) {
                binding.scrollViewMainContainer.visibility = View.VISIBLE
                for (i in 0 until cartData.cart!!.products!!.size) {
                    dataholder = CartAdapterModel(
                            cartData.cart?.products!![i].thumb,
                            cartData.cart?.products!![i].name!!,
                            cartData.cart?.products!![i].price,
                            cartData.cart?.products!![i].productId,
                            cartData.cart?.products!![i].model,
                            cartData.cart?.products!![i].key,
                            cartData.cart?.products!![i].total,
                            cartData.cart?.products!![i].total,
                            cartData.cart?.products!![i].quantity,
                            cartData.cart?.products!![i].stock!!,
                            cartData.cart?.products!![i].option,
                            cartData.cart?.products!![i].reward,
                            cartData.cart?.products!![i].dominantColor!!)
                    data.add(dataholder)
                    cartadapter = CartAdapter(this@Cart, data, cartData)
                    val layoutManager = LinearLayoutManager(this@Cart)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    recyclerview.layoutManager = layoutManager
                    cartadapter.notifyDataSetChanged()
                    recyclerview.adapter = cartadapter
                    recyclerview.itemAnimator = DefaultItemAnimator()
                }
                cartbinding.data = dataholder
                handler = CartHandler(this@Cart, cartData, cartadapter)
                handler?.getCallback(mCallbackManager!!)
                binding.handler = handler
                val linearLaoyutCartTotals = binding.linearLaoyutCartTotals
                for (i in 0 until cartData.cart?.totals!!.size) {
                    if (i == cartData.cart?.totals!!.size - 1) {
                        val child1 = ItemCartTotalBinding.inflate(layoutInflater) //child.xml
                        val totalPriceData = CartTotalAdapterModel(
                                cartData.cart?.totals!![i].title,
                                cartData.cart?.totals!![i].text)
                        child1.data = totalPriceData
                        child1.tv1.textSize = 16f
                        child1.tv2.textSize = 16f
                        child1.tv1.setTextColor(ContextCompat.getColor(this@Cart, R.color.black))
                        child1.tv2.setTextColor(ContextCompat.getColor(this@Cart, R.color.black))
                        val view = View(this@Cart)
                        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2)
                        view.layoutParams = params
                        view.setBackgroundColor(Color.parseColor("#CCCCCC"))
                        linearLaoyutCartTotals.addView(view)
                        linearLaoyutCartTotals.addView(child1.root)
                    } else {
                        val child1 = ItemCartTotalBinding.inflate(layoutInflater) //child.xml
                        val totalPriceData = CartTotalAdapterModel(
                                cartData.cart?.totals!![i].title,
                                cartData.cart?.totals!![i].text)
                        child1.data = totalPriceData
                        linearLaoyutCartTotals.addView(child1.root)
                    }
                }
                val item_on_top = binding.itemsOnTop
                item_on_top.text = cartData.cart?.products?.size!!.toString() + "  " + getString(R.string.items)
                val totalAmountOnTop = binding.totalAmountOnTop
                if (cartData.cart?.totals?.size!! != 0) {
                    totalAmountOnTop.text = getString(R.string.total_amount) +
                            "  " + cartData.cart?.totals!![cartData.cart!!.totals!!.size - 1].text
                }
            }
        } else {
            emptyCart.text = resources.getString(R.string.empty_cart)
            binding.svHeader.visibility = View.VISIBLE
            binding.scrollViewMainContainer.visibility = View.GONE
            binding.proceedToCheckout2.visibility = View.GONE
            AppSharedPreference.editSharedPreference(this@Cart, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, "0")
            val linearLaoyutDiscount = binding.linearLaoyutDiscountTop
            linearLaoyutDiscount.removeAllViews()
            linearLaoyutDiscount.visibility = View.GONE
        }
        binding.mainProgressBar.visibility = View.GONE
    }

    fun updateCart() {
        binding.mainProgressBar.visibility = View.VISIBLE
        binding.scrollViewMainContainer.visibility = View.GONE
        binding.linearLaoyutCartTotals.removeAllViews()
        RetrofitCallback.viewCartCall(this@Cart, Utils.getScreenWidth(), RetrofitCustomCallback(cartCallback, this@Cart))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CART_RESULT && resultCode == Activity.RESULT_OK) {
            MakeToast().shortToast(this, "Return Cart Call")
            startActivity(Intent(this, Cart::class.java))
        }
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.d("Cart", "onActivityResult: :--------->" + result.status + result.signInAccount)
            if (result.isSuccess) {
                val acct = result.signInAccount
                Log.d("Cart", "onActivityResult: ----->" + acct!!.displayName!!)
                Log.d("Cart", "onActivityResult: ----->" + acct.email!!)
                Log.d("Cart", "onActivityResult: ----->" + acct.id!!)
                // Google Sign In was successful, authenticate with Firebase
                SweetAlertBox().showProgressDialog(this@Cart)
                RetrofitCallback.addSocailLogin(this@Cart,
                        acct.displayName!!,
                        acct.displayName!!,
                        acct.email!!,
                        acct.id!!,
                        RetrofitCustomCallback(socailLoginCallback, this@Cart))
            } else {
                // Google Sign In failed
                Log.e("Cart", "Google Sign In failed.")
            }
        }
    }

    fun onClickContinueShopBtn(v: View) {
        val intent = Intent(this@Cart, MainActivity::class.java)
        intent.putExtra(Constant.CART_TO_HOMEPAGE, 1)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.findItem(R.id.action_cart).isVisible = false
        menu.findItem(R.id.action_bell).isVisible = false
        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
        return true
    }

    fun openSellerProfile(v: View) {
        if (mMobikulApplicationCart!!.viewSellerProfile() != null) {
            val tag = v.tag.toString().split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val openSellerProfile = Intent(this@Cart, mMobikulApplicationCart!!.viewSellerProfile())
            openSellerProfile.putExtra("profileUrl", tag[0])
            openSellerProfile.putExtra("shopName", tag[1])
            startActivity(openSellerProfile)
        }
    }

    companion object {
        private val COUPON_TYPE = 0
        private val VOUCHER_TYPE = 1
        private val REWARD_TYPE = 2
        internal var calledByCart: Boolean = false
        internal var canProceedToCheckout: Boolean? = true
        internal var itemId: Int = 0
        internal var viewToMoveToWishList: View? = null
        internal lateinit var couponCode: String
        var mainObject: JSONObject? = null
        var emptyCartConnobject: JSONObject? = null
        internal var cartObject = Cart()
        private val RC_SIGN_IN = 9001
        var DOWNLOADABLE_PRODUCT_STATUS: Int? = 0
        var GUEST_STATUS: Boolean? = false
    }
}