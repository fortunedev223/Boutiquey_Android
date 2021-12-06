package webkul.opencart.mobikul

import android.app.Activity
import android.app.SearchManager
import android.content.*
import android.content.SharedPreferences.Editor
import android.content.res.Configuration
import android.database.MatrixCursor
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.speech.RecognizerIntent
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.StandardExceptionParser
import com.google.android.gms.analytics.Tracker
import org.json.JSONException
import org.json.JSONObject
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.MyOrders
import webkul.opencart.mobikul.activity.SearchDialogActivity
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.CustomerLogoutModel.CustomerLogout
import webkul.opencart.mobikul.model.SellerDashboardModel.Dashbord
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.roomdatabase.AppDataBase
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.credentials.AppCredentials
import java.io.IOException
import java.util.*


abstract class BaseActivity : AppCompatActivity() {
    var itemBell: MenuItem? = null
    var itemCart: MenuItem? = null
    lateinit var menuIntent: Intent
    var menu: Menu? = null
    lateinit var mMobikulApplication: MobikulApplication
    var isInternetAvailable: Boolean = false
    var response: Any? = null
    lateinit var responseObject: JSONObject
    lateinit var configShared: SharedPreferences
    lateinit var shared: SharedPreferences
    var jo = JSONObject()
    lateinit var unreadNotifications: HashSet<String>
    //    lateinit var searchView: SearchView
    var toolbarLoginActivity: Toolbar? = null
    internal lateinit var notificationShared: SharedPreferences
    internal lateinit var soapUserName: String
    internal lateinit var soapPassword: String
    internal var editor: Editor? = null
    internal lateinit var suggestions: MutableList<Suggestion>
    private var suggestionAdapter: SimpleCursorAdapter? = null
    private var logoutCallback: Callback<CustomerLogout>? = null
    private lateinit var builder: AlertDialog.Builder
    private var cartModelCallback: Callback<AddToCartModel>? = null
    private var counter: Int? = 0
    private var total: Int? = -1
    private var list: List<AddCartTable>? = null
    private var appStatus: Boolean? = false
    var connectionErrorDialog: AlertDialog? = null
    private lateinit var syncBuilder: AlertDialog.Builder

    protected val screenWidth: Int
        get() {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.x
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configShared = getSharedPreferences("configureView", Context.MODE_PRIVATE)
        NAMESPACE = AppCredentials.NAMESPACE
        URL = AppCredentials.URL
        soapUserName = AppCredentials.SOAP_USER_NAME
        soapPassword = AppCredentials.SOAP_PASSWORD
        shared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val themeShared = getSharedPreferences("theme1", Context.MODE_PRIVATE)
        this.setTheme(R.style.AppTheme1)
        Log.d("session_id", shared.getString("SESSION_ID", "Session_Not_Loggin")!! + "")
        mMobikulApplication = application as MobikulApplication
        if (mTracker == null)
            mTracker = mMobikulApplication.defaultTracker
        mTracker!!.send(HitBuilders.ExceptionBuilder().build())
        val name = this.javaClass.name
        mTracker!!.setScreenName("" + name)
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
        ctx = this@BaseActivity

        logoutCallback = object : Callback<CustomerLogout> {
            override fun onResponse(call: Call<CustomerLogout>, response: Response<CustomerLogout>) {
                MakeToast().shortToast(this@BaseActivity, resources.getString(R.string.logout_msg))
                if (SweetAlertBox.sweetAlertDialog != null) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
                val shared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                shared.edit().clear().apply()
                val intent = Intent(this@BaseActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            }

            override fun onFailure(call: Call<CustomerLogout>, t: Throwable) {

            }
        }
    }

    fun hideKeyword(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var v = activity.currentFocus
        if (v == null) {
            v = View(activity)
        }
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun trackException(e: Exception?, ctx: Context) {
        if (e != null) {
            mTracker?.send(HitBuilders.ExceptionBuilder()
                    ?.setDescription(
                            StandardExceptionParser(ctx, null)
                                    .getDescription(Thread.currentThread().stackTrace.toString(), e))
                    ?.setFatal(false)
                    ?.build()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyword(this)
    }

    fun hideSoftKeyboard(view: View?) {
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    override fun onResume() {
        if (itemCart != null) {
            val customerDataShared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val icon = itemCart!!.icon as LayerDrawable
            // Update LayerDrawable's BadgeDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"))
        }
        super.onResume()
    }

    override fun onBackPressed() {
        if (connectionErrorDialog != null && connectionErrorDialog?.isShowing!!) {
            hideDialog()
            onBackPressed()
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == android.R.id.home) {
            hideKeyword(ctx as Activity)
            onBackPressed()
            return true
        }

        if (id == R.id.search) {
            var intent = Intent(this@BaseActivity, SearchDialogActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        if (id == R.id.marketPlace) {
            if (mMobikulApplication.viewMarketPlaceHome() != null) {
                val intent = Intent(this, mMobikulApplication.viewMarketPlaceHome())
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
            }
            return true
        }

        if (id == R.id.sellerDashboard) {
            if (mMobikulApplication.viewSellerDashBoard() != null) {
                val intent = Intent(this, mMobikulApplication.viewSellerDashBoard())
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
            }
            return true
        }

        if (id == R.id.sellerOrder) {
            if (mMobikulApplication.viewSellerOrderHistory() != null) {
                val intent = Intent(this, mMobikulApplication.viewSellerOrderHistory())
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
            }
            return true
        }

        val shared = getSharedPreferences("customerData", Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        val isSeller = shared.getString("isSeller", "")

        if (id == R.id.action_settings) {
            if (isLoggedIn) {
                startActivity(Intent(this, MyWishlistActivity::class.java))
            } else {
                SweetAlertBox().showWarningWishlistPopUp(this, "", resources.getString(R.string.guest_wishlist_msg))
            }
            val loginMenuItem = menu!!.findItem(R.id.login)
            val signupMenuItem = menu!!.findItem(R.id.signup)
            if (isLoggedIn) {
                loginMenuItem.title = resources.getString(R.string.logout_title)
                signupMenuItem.isVisible = false
                if (isSeller!!.equals("1", ignoreCase = true)) {
                    menu!!.findItem(R.id.sellerDashboard).isVisible = true
                    menu!!.findItem(R.id.sellerOrder).isVisible = true
                }
            } else {
                loginMenuItem.title = resources.getString(R.string.login_title)
                signupMenuItem.isVisible = true
                menu!!.findItem(R.id.sellerDashboard).isVisible = false
                menu!!.findItem(R.id.sellerOrder).isVisible = false
            }
        }

        if (!isLoggedIn) {
            if (id != R.id.signup && id != R.id.action_settings && id != R.id.action_bell && id != R.id.search)
                id = R.id.login
        }

        if (id == R.id.login && item.title == resources.getString(R.string.logout_title)) {
            SweetAlertBox().showProgressDialog(this)
            logoutCallback?.let { RetrofitCallback.customerLogoutCall(this, it) }
            return true
        } else if (id == R.id.login) {
            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
                return true
            } else {
                val intent = Intent(this, Dashbord::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
                return true
            }
        } else if (id == R.id.signup) {
            if (!isLoggedIn) {
                val intent = Intent(this, CreateAccountActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
                return true
            } else {
                val intent = Intent(this, Dashbord::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
                return true
            }
        } else if (id == R.id.dashboard) {
            val intent = Intent(this, Dashbord::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            this.startActivity(intent)
            return true
        } else if (id == R.id.accountinfo) {
            val intent = Intent(this, AccountinfoActivity::class.java)
            intent.putExtra("changeAccountInfo", "1")
            this.startActivity(intent)
            return true
        } else if (id == R.id.addrbook) {
            val intent = Intent(this, AddrBookActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            this.startActivity(intent)
            return true
        } else if (id == R.id.myorders) {
            val intent = Intent(this, MyOrders::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            this.startActivity(intent)
            return true
        } else if (id == R.id.mywishlist) {
            if (isLoggedIn) {
                val intent = Intent(this, MyWishlistActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                this.startActivity(intent)
                return true
            } else {
                SweetAlertBox().showWarningWishlistPopUp(this, "", resources.getString(R.string.guest_wishlist_msg))
            }
        } else if (id == R.id.mydownloads) {
            val intent = Intent(this, MyDownloadsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            this.startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun setLocale() {
        val code = if (MainActivity.homeDataModel?.languages?.code != null) {
            if (MainActivity.homeDataModel?.languages?.code?.contains("ar")!!) "ar" else MainActivity.homeDataModel?.languages?.code
        } else {
            AppSharedPreference.getLanguageCode(this@BaseActivity)
        }
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        if (Build.VERSION.SDK_INT >= 25) {
            val configuration = resources.configuration
            getApplicationContext().createConfigurationContext(configuration);
            createConfigurationContext(configuration);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menuMainActivity; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        this.menu = menu
        menu.findItem(R.id.action_bell).isVisible = false
        val searchItem = menu.findItem(R.id.search)
        menu.findItem(R.id.myproreview).isVisible = false
        if (mMobikulApplication.viewMarketPlaceHome() != null) {
            menu.findItem(R.id.marketPlace).isVisible = true
        }
        itemCart = menu.findItem(R.id.action_cart)
        val shared = getSharedPreferences("customerData", Context.MODE_PRIVATE)
        val icon2 = itemCart!!.icon as LayerDrawable
        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon2, shared.getString("cartItems", "0"))
        itemCart!!.setOnMenuItemClickListener {
            val menuIntent = Intent(this@BaseActivity, Cart::class.java)
            startActivity(menuIntent)
            true
        }

        itemBell = menu.findItem(R.id.action_bell)
        notificationShared = getSharedPreferences("com.webkul.mobikul.notification", Context.MODE_MULTI_PROCESS)
        val icon = itemBell!!.icon as LayerDrawable
        unreadNotifications = notificationShared.getStringSet("unreadNotifications", HashSet()) as HashSet<String>
        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, unreadNotifications.size.toString() + "")

        itemBell!!.setOnMenuItemClickListener {
            menuIntent = Intent(this@BaseActivity, NotificationActivity::class.java)
            startActivity(menuIntent)
            true
        }

        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        val isSeller = shared.getString("isSeller", "")
        val loginMenuItem = menu.findItem(R.id.login)
        val signupMenuItem = menu.findItem(R.id.signup)
        if (isLoggedIn) {
            loginMenuItem.title = resources.getString(R.string.logout_title)
            signupMenuItem.isVisible = false
            if (isSeller!!.equals("1", ignoreCase = true)) {
                menu.findItem(R.id.sellerDashboard).isVisible = true
                menu.findItem(R.id.sellerOrder).isVisible = true
            }
        }

        MenuItemCompat.setOnActionExpandListener(searchItem,
                object : MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        itemBell!!.isVisible = true
                        itemCart!!.isVisible = true
                        return true // Return true to collapse action view
                    }

                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        itemBell!!.isVisible = false
                        itemCart!!.isVisible = false
                        // Do something when expanded
                        return true // Return true to expand action view
                    }
                })

        return super.onCreateOptionsMenu(menu)
    }

    open fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    fun showDialog(mContext: Context) {
        builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(resources.getString(R.string.intenet_unavailable))
                .setPositiveButton(resources.getString(R.string.retry), DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> if (Build.VERSION.SDK_INT >= 11) {
                            if (mContext is MainActivity) {
                                finish()
                                startActivity(intent)
                                this.overridePendingTransition(0, 0)
                            } else {
                                recreate()
                            }
                        } else {
                            if (mContext is MainActivity) {
                                finish()
                                startActivity(intent)
                                this.overridePendingTransition(0, 0)
                            } else {
                                finish()
                                startActivity(intent)
                                this.overridePendingTransition(0, 0)
                            }
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    if (this is LoginActivity && this.intent.hasExtra("fromHome")) {
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else if (!(mContext is MainActivity)) {
                        finish()
                    }
//                    onBackPressed()
                })
                .setCancelable(false)
        connectionErrorDialog = builder.create()
        connectionErrorDialog?.show()
    }

    fun hideDialog() {
        connectionErrorDialog?.hide()
        recreate()
    }

    override fun onUserInteraction() {
        if (itemBell != null) {
            notificationShared = getSharedPreferences("com.webkul.mobikul.notification", Context.MODE_MULTI_PROCESS)
            val icon = itemBell!!.icon as LayerDrawable
            val unreadNotifications = notificationShared.getStringSet("unreadNotifications", HashSet()) as HashSet<String>
            Utils.setBadgeCount(this, icon, unreadNotifications.size.toString() + "")
        }
    }

    companion object {
        lateinit var ctx: Context
        internal lateinit var NAMESPACE: String
        internal lateinit var URL: String
        private var mTracker: Tracker? = null
    }

    private fun callAddCartApi() {
        if (list?.size!! > 0) {
            total = list!!.size - 1
            if (counter!! <= total!!) {
                RetrofitCallback.addToCartCall(this, list!!.get(counter!!).productId,
                        list!!.get(counter!!).qty, JSONObject(list!!.get(counter!!).jsonObject)
                        , RetrofitCustomCallback(cartModelCallback, this))
            } else {
                counter = 0
            }
        }
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
            isInternetAvailable = isConnected
            if (isConnected) {
                cartModelCallback = object : Callback<AddToCartModel> {
                    override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                        AppDataBaseController.deleteAddToCartItem(this@BaseActivity, list!!.get(counter!!).productId)
                        if (response.body()!!.error == 0) {
                            val total = response.body()!!.total!!
                            MakeToast().shortToast(this@BaseActivity, response.body()!!.message)
                            AppSharedPreference.editSharedPreference(this@BaseActivity,
                                    Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                    Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                            counter = counter!! + 1
                            callAddCartApi()
                            if (itemCart != null) {
                                val icon = itemCart!!.icon as LayerDrawable
                                Utils.setBadgeCount(this@BaseActivity, icon,
                                        AppSharedPreference.getCartItems(this@BaseActivity,
                                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                            }
                            if (contxt is MainActivity) {
                                val icon = (contxt as MainActivity).homeCartIcon!!.icon as LayerDrawable
                                Utils.setBadgeCount(this@BaseActivity, icon,
                                        AppSharedPreference.getCartItems(this@BaseActivity,
                                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                            }
                        }
                    }

                    override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                        Log.d("Addtocart", "onFailure:-------> " + t.toString())
                    }
                }

                if (isConnected) {
                    runOnUiThread(Runnable {
                        list = AppDataBase.getAppDataBaseInstance(this@BaseActivity).getAddToCartDao().getAddToCart()
                        if (list!!.size > 0) {
                            if (AppSharedPreference.getSyncStatusData(this@BaseActivity)!!) {
                                syncBuilder = AlertDialog.Builder(this@BaseActivity, R.style.AlertDialogTheme)
                                        .setMessage(getString(R.string.sync_string))
                                        .setPositiveButton(resources.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                                            when (which) {
                                                DialogInterface.BUTTON_POSITIVE -> callAddCartApi()
                                            }
                                        })
                                        .setNegativeButton(resources.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                                            AppSharedPreference.putSyncStatusData(this@BaseActivity, false)
                                            dialog.dismiss()
                                        })
                                        .setCancelable(false)
                                connectionErrorDialog = syncBuilder.create()
                                connectionErrorDialog?.show()
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadCastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadCastReceiver)
    }
}
