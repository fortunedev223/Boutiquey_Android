package webkul.opencart.mobikul

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import com.google.gson.GsonBuilder
import cn.pedant.SweetAlert.SweetAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.helper.*
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.credentials.AppCredentials
import webkul.opencart.mobikul.databinding.ActivitySplashscreenBinding
import webkul.opencart.mobikul.offline.database.DataBaseHandler
import webkul.opencart.mobikul.FileAdapter.TAG
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import java.util.*

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class SplashScreen : Activity() {
    internal lateinit var configShared: SharedPreferences
    private var isInternetAvailable: Boolean? = false
    internal lateinit var editor: Editor
    private var screen_width: Int = 0
    private var mOfflineDataBaseHandler: DataBaseHandler? = null
    private var homeDataModelCallback: Callback<HomeDataModel>? = null
    private var apiLoginModelCallback: Callback<ApiLoginModel>? = null
    private var dm: DisplayMetrics? = null
    private var densityDpi: Float = 0.toFloat()
    private var latestVersion = "1.0"
    private var currentVersion = "1.0"
    private var sweetAlert: SweetAlertDialog? = null
    private var homeModel: Response<HomeDataModel>? = null
    private var splashscreenBinding: ActivitySplashscreenBinding? = null
    private var baseModelCallback: Callback<BaseModel>? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionObj = Utils(this@SplashScreen)
        configShared = getSharedPreferences("configureView", Context.MODE_PRIVATE)
        editor = configShared.edit()
        editor.putBoolean("isMainCreated", false)
        editor.apply()
        mOfflineDataBaseHandler = DataBaseHandler(this)
        dm = resources.displayMetrics
        densityDpi = dm?.density!!
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        isOnline()
        splashscreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splashscreen)
        if ((!isInternetAvailable!!)) {
            offlineDatabase()
        }
        if (intent.hasExtra("type")) {
            setNotification(intent)
        } else if (BuildConfig.isDemo) {
            if (!AppSharedPreference.getStoreTime(this).equals("")) {
                val time = AppSharedPreference.getStoreTime(this)!!.toLong()
                val currentTime = Date().time
                if (AppSharedPreference.getStoreCode(this).equals("") || (currentTime - time) > 10800000)
                    setupLanguageLayout()
                else {
                    openMainActivity()
                }
            } else if (AppSharedPreference.getStoreTime(this).equals("")) {
                setupLanguageLayout()
            } else {
                openMainActivity()
            }
        } else {
            openMainActivity()
        }
    }

    private fun setNotification(intent: Intent) {
        val notificationType = intent.extras!!.get("type") as String
        Log.d("AfterSplash1", "setNotification: :------->" + intent.extras!!.get("type")!!)
        Log.d("AfterSplash1", "setNotification: :------->" + intent.extras!!.get("id")!!)
        Log.d("AfterSplash1", "setNotification: :------->" + intent.extras!!.get("title")!!)
        val intent1: Intent
        if (notificationType == "product") {
            println("notification----class SPLASHSCREEN")
            intent1 = Intent(this, ViewProductSimple::class.java)
            intent1.putExtra("idOfProduct", intent.extras!!.get("id") as String)
            intent1.putExtra("nameOfProduct", intent.extras!!.get("title") as String)
            intent1.putExtra("isNotification", intent.extras!!.get("title") as String)
            this.finish()
            startActivity(intent1)
        } else if (notificationType == "category") {
            intent1 = Intent(this, CategoryActivity::class.java)
            intent1.putExtra("ID", intent.extras!!.get("id") as String)
            intent1.putExtra("CATEGORY_NAME", intent.extras!!.get("title") as String)
            intent1.putExtra("type", "category")
            intent1.putExtra("isNotification", intent.extras!!.get("title") as String)
            intent1.putExtra("isNotification", intent.extras!!.get("title") as String)
            this.finish()
            startActivity(intent1)
        } else if (notificationType.equals("Custom", ignoreCase = true)) {
            intent1 = Intent(this, CategoryActivity::class.java)
            intent1.putExtra("type", "custom")
            intent1.putExtra("id", intent.extras!!.get("id") as String)
            intent1.putExtra("isNotification", intent.extras!!.get("title") as String)
            this.finish()
            startActivity(intent1)
        }
    }

    private fun openMainActivity() {
        splashscreenBinding?.llLangauge?.visibility = View.GONE
        splashscreenBinding?.progressBar2?.visibility = View.VISIBLE
        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        editor.putString("websiteId", AppCredentials.WEBSITE_ID)
        editor.putString("NAMESPACE", AppCredentials.NAMESPACE)
        editor.putString("URL", AppCredentials.URL)
        editor.putString("soapUserName", AppCredentials.SOAP_USER_NAME)
        editor.putString("soapPassword", AppCredentials.SOAP_PASSWORD)
        editor.apply()
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        screen_width = size.x
        try {
            latestVersion = VersionChecker(
                    object : VersionChecker.GetVersionCode {
                        override fun getVersionCode(version: String) {
                            latestVersion = version
                            Log.d("AppLiveVersion", "=======>" + latestVersion)
                            callHome()
                        }
                    },
                    this).execute().get()
        } catch (e: Exception) {
            Log.d("VersionChecker", "run: :->$e")
            e.printStackTrace()
            latestVersion = "1.0"
            callHome()
        }
    }

    fun callHome() {
        try {
            val pInfo = this@SplashScreen.packageManager.getPackageInfo(packageName, 0)
            currentVersion = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        initilizingData(this@SplashScreen)
    }

    private fun initilizingData(splashScreen: SplashScreen) {
        homeDataModelCallback = object : Callback<HomeDataModel> {
            override fun onResponse(call: Call<HomeDataModel>, response: Response<HomeDataModel>) {
                homeModel = response
                if (response.body()?.fault != 1) {
                    AppSharedPreference.setLanguageCode(this@SplashScreen, homeModel?.body()?.languages?.code)
                    checkForVerstion(response)
                } else {
                    apiLoginModelCallback?.let { RetrofitCallback.apiLoginCall(splashScreen, it) }
                }
            }

            override fun onFailure(call: Call<HomeDataModel>, t: Throwable) {
                t.printStackTrace()
                NetworkIssue.getNetworkIssue(t.message!!, splashScreen)
                Log.d(TAG, "onFailure: ------->" + t.toString())
            }
        }
        apiLoginModelCallback = object : Callback<ApiLoginModel> {
            override fun onResponse(call: Call<ApiLoginModel>, response: Response<ApiLoginModel>) {
                if (response.body()!!.fault == 0 && response.body()?.error != 1) {
                    Log.d(TAG, "Fault " + response.body()!!.fault)
                    AppSharedPreference.editSharedPreference(this@SplashScreen,
                            Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN, response.body()!!.wkToken.toString())
                    AppSharedPreference.setStoreCode(this@SplashScreen, response.body()?.language!!)
                    AppSharedPreference.setCurrencyCode(this@SplashScreen, response.body()?.currency!!)
                    initilizingData(splashScreen)
                } else {
                    initilizingData(splashScreen)
                }
            }

            override fun onFailure(call: Call<ApiLoginModel>, t: Throwable) {
                NetworkIssue.getNetworkIssue(t.message!!, splashScreen)
            }
        }
        setUpDesign()
    }

    private fun setUpDesign() {
        Log.d("SetupDesign", "======>" + versionCompare(latestVersion, currentVersion))
        if (versionCompare(latestVersion, currentVersion) >= 1) {
            sweetAlert = SweetAlertDialog(this@SplashScreen, SweetAlertDialog.WARNING_TYPE)
            sweetAlert?.setTitleText(getString(R.string.warning))
                    ?.setContentText(resources.getString(R.string.new_version_available))
                    ?.setConfirmText(resources.getString(R.string.update))
                    ?.setConfirmClickListener {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(Constant.getApplicationVersionCheckerUrl(this@SplashScreen))
                        startActivity(i)
                    }
                    ?.show()
            sweetAlert?.setCancelable(false)
        } else {
            homeDataModelCallback?.let {
                RetrofitCallback.getHomePageCall(this@SplashScreen, "",
                        it, screen_width.toString(), densityDpi.toString())
            }
        }
    }


    private fun setupLanguageLayout() {
        splashscreenBinding?.llLangauge?.visibility = View.VISIBLE
        splashscreenBinding?.backgroundImage?.alpha = .5f
        splashscreenBinding?.setIsLoading(false)
        splashscreenBinding?.englishRb?.setChecked(true)
        splashscreenBinding?.continueBtn?.setOnClickListener(View.OnClickListener {
            splashscreenBinding?.llLangauge?.visibility = View.GONE
            splashscreenBinding?.progressBar2?.visibility = View.VISIBLE
            baseModelCallback = object : Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    if (response.body()!!.error == 0) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        openMainActivity()
                    }
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {

                }
            }
            if (splashscreenBinding?.englishRb?.isChecked()!!) {
                saveTime("en")
                RetrofitCallback.languageCall(this@SplashScreen, "en-gb", RetrofitCustomCallback(baseModelCallback, this))
            } else if (splashscreenBinding!!.arabicRb.isChecked()) {
                saveTime("ar")
                RetrofitCallback.languageCall(this@SplashScreen, "ar", RetrofitCustomCallback(baseModelCallback, this))
            } else {
                MakeToast.instance.longToast(this@SplashScreen, "Please Choose Language.")
            }
        })
    }

    private fun saveTime(code: String) {
        AppSharedPreference.setStoreCode(this@SplashScreen, code)
        splashscreenBinding?.llLangauge?.visibility = View.GONE
        splashscreenBinding?.backgroundImage?.alpha = 1.0f
        AppSharedPreference.setStoreTime(this@SplashScreen, Date().time.toString())
    }

    private fun checkForVerstion(response: Response<HomeDataModel>) {
        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        val homeData = GsonBuilder().create().toJson(response.body())
        mOfflineDataBaseHandler!!.updateIntoOfflineDB("getHomepage", homeData, null)
        intent.putExtra("data", homeData)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (isInternetAvailable!!) {
            Log.d("SplashScreenVersion", "======>" + versionCompare(latestVersion, currentVersion))
            if (currentVersion != "1.0" && versionCompare(latestVersion, currentVersion) >= 1) {
                if (sweetAlert != null) {
                    sweetAlert?.dismissWithAnimation()
                }
                sweetAlert = SweetAlertDialog(this@SplashScreen, SweetAlertDialog.WARNING_TYPE)
                if (!isDestroyed) {
                    sweetAlert?.setTitleText(getString(R.string.warning))
                            ?.setContentText(resources.getString(R.string.new_version_available))
                            ?.setConfirmText(resources.getString(R.string.update))
                            ?.setConfirmClickListener { sDialog ->
                                sDialog.dismissWithAnimation()
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse(Constant.getApplicationVersionCheckerUrl(this@SplashScreen))
                                startActivity(i)
                            }
                            ?.show()
                    sweetAlert?.setCancelable(false)
                }
            }
        }
    }

    private fun offlineDatabase() {
        val databaseCursor = mOfflineDataBaseHandler!!.selectFromOfflineDB("getHomepage", null)
        if (databaseCursor != null) {
            Log.d("SplashScreen", "Number of Records found: " + databaseCursor.count)
            if (databaseCursor.count != 0) {
                databaseCursor.moveToFirst()
                val responseData = databaseCursor.getString(0)
                Log.d("SplashScreen", "Data from Database Query: Method Name : " + databaseCursor.getString(1) + ", Data : " + databaseCursor.getString(0))
                databaseCursor.close()
                setContentView(R.layout.activity_splashscreen)
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                }
                editor.putString("websiteId", AppCredentials.WEBSITE_ID)
                editor.putString("NAMESPACE", AppCredentials.NAMESPACE)
                editor.putString("URL", AppCredentials.URL)
                editor.putString("soapUserName", AppCredentials.SOAP_USER_NAME)
                editor.putString("soapPassword", AppCredentials.SOAP_PASSWORD)
                editor.apply()
                val display = windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                screen_width = size.x
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                intent.putExtra("data", responseData)
                startActivity(intent)
                finish()
            } else {
                val noInternet = SweetAlertDialog(this@SplashScreen, SweetAlertDialog.WARNING_TYPE)
                noInternet.setTitleText(getString(R.string.warning))
                        .setContentText(resources.getString(R.string.intenet_unavailable))
                        .setConfirmText(resources.getString(R.string.retry))
                        .setConfirmClickListener {
                            finish()
                            startActivity(intent)
                            noInternet.dismissWithAnimation()
                        }
                        .show()
            }
        } else {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> if (Build.VERSION.SDK_INT >= 11) {
                        recreate()
                    } else {
                        finish()
                        startActivity(intent)
                    }
                }
            }
            val builder = AlertDialog.Builder(this@SplashScreen, R.style.AlertDialogTheme)
            builder.setMessage(resources.getString(R.string.intenet_unavailable))
                    .setPositiveButton(resources.getString(R.string.retry),
                            dialogClickListener).setCancelable(false)
                    .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RC_UPDATE_APP_FROM_PLAYSTORE) {
            val intent = intent
            finish()
            startActivity(intent)
        } else {
            if (versionCompare(latestVersion, currentVersion) >= 1) {
                val sweetAlert = SweetAlertDialog(this@SplashScreen, SweetAlertDialog.WARNING_TYPE)
                sweetAlert.setTitleText(getString(R.string.warning))
                        .setContentText(resources.getString(R.string.new_version_available))
                        .setConfirmText(resources.getString(R.string.update))
                        .setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(Constant.getApplicationVersionCheckerUrl(this@SplashScreen))
                            startActivityForResult(i, RC_UPDATE_APP_FROM_PLAYSTORE)
                        }
                        .showCancelButton(true)
                        .show()
                sweetAlert.setCancelable(false)
            }
        }

    }

    fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    companion object {
        private val RC_UPDATE_APP_FROM_PLAYSTORE = 100
        lateinit var sessionObj: Utils
        fun versionCompare(str1: String, str2: String): Int {
            Log.d("VersionCompare", "======>" + str1 + "======>" + str2)
            if (str1 == str2) {
                return -1
            } else {
                val vals1 = str1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val vals2 = str2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var i = 0
                while (i < vals1.size && i < vals2.size && vals1[i] == vals2[i]) {
                    i++
                }
                if (i < vals1.size && i < vals2.size) {
                    val diff = Integer.valueOf(vals1[i])!!.compareTo(Integer.valueOf(vals2[i]))
                    return Integer.signum(diff)
                }
                return Integer.signum(vals1.size - vals2.size)
            }
        }
    }
}