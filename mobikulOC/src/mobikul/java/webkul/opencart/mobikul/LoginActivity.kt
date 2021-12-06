package webkul.opencart.mobikul

import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.hardware.fingerprint.FingerprintManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast

import com.facebook.CallbackManager
import kotlinx.android.synthetic.mobikul.activity_login.*

import org.json.JSONException
import org.json.JSONObject

import java.security.SecureRandom
import java.util.regex.Pattern

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.DashBoard
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.adapterModel.LoginAdapterModel
import webkul.opencart.mobikul.handlers.LoginHandler
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.LoginModel.LoginModel
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    var editorLoginActivity: Editor? = null
    var configSharedLoginActivity: SharedPreferences? = null
    lateinit var sharedLoginActivity: SharedPreferences
    protected lateinit var responseObjectLoginActivity: JSONObject
    protected var itemCartLoginActivity: MenuItem? = null
    var menuLoginActivity: Menu? = null
    var mMobikulApplicationLoginActivity: MobikulApplication? = null
    var responseLoginActivity: Any? = null
    internal var progress: ProgressDialog? = null
    internal var Authenticated = false
    internal var NORMAL_REQUEST = -1
    internal var CHANGE_REQUEST = 1
    private var progressDialog: ProgressDialog? = null
    private var isInternetAvailableLoginActivity: Boolean = false
    private var mProgressDialog: ProgressDialog? = null
    private var passwordField: TextInputEditText? = null
    private var userNameField: TextInputEditText? = null
    private var isPasswordVisible: Boolean = false
    private val personName: String? = null
    private val personEmail: String? = null
    private val personPhoto: Uri? = null
    private var callbackManager: CallbackManager? = null
    private var username: String? = null
    private var password: String? = null
    private var loginBinding: ActivityLoginBinding? = null
    private var passWord: TextInputLayout? = null
    private var title: TextView? = null
    private var addtoWishlistCallback: Callback<AddtoWishlist>? = null
    private var loginModelCallback: Callback<LoginModel>? = null
    private var forgotPasswordCallback: Callback<BaseModel>? = null
    var toolbar: Toolbar? = null
    internal var onCancelDialog: DialogInterface = object : DialogInterface {
        override fun cancel() {
            Login()
        }

        override fun dismiss() {
            Login()
        }
    }
    private var fingerPrintShared: SharedPreferences? = null

    override fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailableLoginActivity = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onBackPressed() {
        hideKeyword(this)
        super.onBackPressed()
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnline()
        if (!isInternetAvailableLoginActivity) {
            showDialog(this)
        } else {
            FingerprintSupported()
            sharedLoginActivity = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            fingerPrintShared = getSharedPreferences("configureView", Context.MODE_PRIVATE)
            fingerprint_enabled = fingerPrintShared!!.getBoolean("FingetprintEnabled", false)
            val isLoggedIn = sharedLoginActivity.getBoolean("isLoggedIn", false)
            if (isLoggedIn) {
                val intent = Intent(this, DashBoard::class.java)
                this.startActivity(intent)
                finish()
            }

            if (intent.hasExtra("productId")) {
                ADDTOWISHLIST = true
            }
            addtoWishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    MakeToast().shortToast(this@LoginActivity, response.body()!!.message)
                    ADDTOWISHLIST = false
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

                }
            }
            loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
            loginBinding!!.data = LoginAdapterModel(this@LoginActivity, loginBinding!!)
            loginBinding!!.handler = LoginHandler(this@LoginActivity)
//            val view = AppCompatResources.getDrawable(this, R.drawable.show)
//            passWord = loginBinding!!.passLayout11
//            passWord!!.passwordVisibilityToggleDrawable = view
            if (fingerprintsupported && fingerprint_enabled) {
                loginBinding!!.loginThroughFp.visibility = View.VISIBLE
                val fragment = FingerprintDemoFragment.newInstance(1, NORMAL_REQUEST)
                fragment.show(supportFragmentManager, "tag")
            }
            title = loginBinding!!.toolbar!!.findViewById(R.id.title)
            title!!.text = getString(R.string.login_action_title)
            callbackManager = CallbackManager.Factory.create()
            toolbar = loginBinding!!.toolbar!!.findViewById(R.id.toolbar)
            setSupportActionBar(toolbarLoginActivity)
            loginBinding?.login?.setOnClickListener { view -> loginPost(view) }
            toolbar!!.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun goToRegister(v: View) {
        val i = Intent(this, CreateAccountActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(i)
    }


    fun onClickFingerPrintButton(v: View) {
        val fragment = FingerprintDemoFragment.newInstance(1, NORMAL_REQUEST)
        fragment.show(supportFragmentManager, "tag")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    fun loginPost(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        userNameField = loginBinding!!.etUsername
        passwordField = loginBinding!!.etPassword
        username = userNameField!!.text.toString().trim { it <= ' ' }
        password = passwordField!!.text.toString().trim { it <= ' ' }
        var isFormValidated: Boolean? = true
        if (username!!.matches("".toRegex())) {
            userlayout11?.error = getString(R.string.email) + " " + resources.getString(R.string.is_require_text)
            isFormValidated = false
        } else if (!Validation.isEmailValid(username)) {
            userlayout11?.error = resources.getString(R.string.enter_valid_email)
            isFormValidated = false
        }

        if (password!!.matches("".toRegex())) {
            loginBinding?.passLayout11?.error = getString(R.string.password) + " " + resources.getString(R.string.is_require_text)
            isFormValidated = false
        } else if (password!!.length < 4) {
            loginBinding?.passLayout11?.error = getString(R.string.password) + " " + resources.getString(R.string.alert_password_length)
            isFormValidated = false
        }

        if (isFormValidated!!) {
            SweetAlertBox().showProgressDialog(this@LoginActivity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && fingerprintsupported) {
                if (fingerprint_enabled) {
                    if (fingerPrintShared!!.getString("TouchEmail", "") != username || fingerPrintShared!!.getString("TouchPassword", "") != password) {
                        val fingerprintDialog = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                                .setMessage("Do you want to replace previous credentials with these ones, for Fingerprint authentication?").setCancelable(false)
                                .setPositiveButton(android.R.string.ok) { dialog, which ->
                                    dialog.dismiss()
                                    fingerPrintShared!!.edit().remove("TouchEmail").remove("TouchPassword").apply()
                                    val fragment = FingerprintDemoFragment.newInstance(0, CHANGE_REQUEST)
                                    fragment.show(supportFragmentManager, "tag")
                                    fragment.onCancel(onCancelDialog)
                                }.setNegativeButton(android.R.string.cancel) { dialog, which ->
                                    dialog.dismiss()
                                    Login()
                                }.create()
                        fingerprintDialog.show()
                    } else {
                        Login()
                    }
                } else {
                    android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
                            .setMessage(getString(R.string.do_you_want_add_fingerprint))
                            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                                SweetAlertBox.sweetAlertDialog?.dismissWithAnimation()
                                val fragment = FingerprintDemoFragment.newInstance(0, NORMAL_REQUEST)
                                fragment.show(supportFragmentManager, "tag")
                                fragment.onCancel(onCancelDialog)
                            })
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.cancel), { dialog, which -> Login() })
                            .show()
                }
            } else {
                Login()
            }
        }
    }

    private fun Login() {
        val jo = JSONObject()
        try {
            jo.put("username", username)
            jo.put("password", password)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        loginModelCallback = object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response.body()!!.error == 0) {
                    customerLoginResponse(response.body()!!)
                } else {
                    SweetAlertBox.instance.showErrorPopUp(this@LoginActivity, "Error", response!!.body()!!.message)
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

            }
        }
        RetrofitCallback.userLoginCall(this, this!!.username!!, this!!.password!!, RetrofitCustomCallback(loginModelCallback, this))
    }

    fun customerLoginResponse(backresult: LoginModel) {
        try {
            Log.d(TAG, "editCustomerResponse: $backresult")
            if (SweetAlertBox.sweetAlertDialog != null) {
                SweetAlertBox.sweetAlertDialog!!.dismissWithAnimation()
            }
            if (backresult.status.equals("1", ignoreCase = true)) {
                val customerName = backresult.firstname + " " + backresult.lastname
                val customerEmail = backresult.email
                val customerId = backresult.customerId
                val pref = applicationContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putString("customerEmail", customerEmail)
                editor.putString("customerName", customerName)
                editor.putString("customerId", customerId)
                editor.putString("cartItems", if (backresult.cartTotal == "null" || backresult.cartTotal == null) "0" else backresult.cartTotal)
                editor.putString("isSeller", backresult.partner.toString())
                editor.apply()
                if (intent.hasExtra("redirect")) {
                    startActivity(Intent(this, CheckoutActivity::class.java))
                    this@LoginActivity.finish()
                    Log.d("LoginActivity", "------->")
                } else {
                    if (intent.hasExtra("splash")) {
                        this.finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        if (intent.hasExtra("fromHome")) {
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("updateHome", true)
                        startActivity(intent)
                        finish()
                    }
                    try {
                        Toast.makeText(applicationContext, "Welcome, " + backresult.firstname
                                + " " + backresult.lastname, Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                }

            } else {
                SweetAlertBox().showErrorPopUp(this@LoginActivity, "Error", responseObjectLoginActivity.getString("message"))
            }
            if (ADDTOWISHLIST) {
                val productId = intent.extras!!.getString("productId")
                RetrofitCallback.addToWishlistCall(this@LoginActivity, productId!!, RetrofitCustomCallback(addtoWishlistCallback, this@LoginActivity))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun openForgotPasswordDialog(v: View) {
        val dialogView = layoutInflater.inflate(R.layout.forgot_password_dialog_layout, null)
        val email = dialogView.findViewById<View>(R.id.email) as TextView
        val user_email = dialogView.findViewById<View>(R.id.user_email) as TextView
        email.text = Html.fromHtml(email.text.toString() + "<font color=\"#FF2107\">" + "*" + "</font>")

        val forgotPasswordDialog = android.support.v7.app.AlertDialog
                .Builder(this@LoginActivity, R.style.AlertDialogTheme)
                .setView(dialogView)
                .setPositiveButton(resources.getString(R.string.submit), null) //Set to null. We override the onclick
                .setNegativeButton(R.string.cancel, null)
                .create()

        forgotPasswordDialog.setOnShowListener {
            val b = forgotPasswordDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            b.setOnClickListener {
                if (!EMAIL_PATTERN.matcher(user_email.text.toString()).matches()) {
                    user_email.error = resources.getString(R.string.enter_valid_email)
                } else {
                    SweetAlertBox.instance.showProgressDialog(this)
                    val jo = JSONObject()
                    try {
                        jo.put("email", user_email.text.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    forgotPasswordCallback = object : Callback<BaseModel> {
                        override fun onResponse(call: Call<BaseModel>,
                                                response: Response<BaseModel>) {
                            SweetAlertBox.dissmissSweetAlertBox()
                            if (response.body()?.error == 0) {
                                forgotPasswordResponse(response.body()!!)
                            } else {
                                Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                            t.printStackTrace()
                            SweetAlertBox.dissmissSweetAlertBox()
                        }
                    }

                    RetrofitCallback.forgotPasswordCall(this,
                            user_email.text.toString(),
                            RetrofitCustomCallback(forgotPasswordCallback, this))
                    forgotPasswordDialog.dismiss()
                }
            }
        }

        forgotPasswordDialog.show()
    }

    fun forgotPasswordResponse(output: BaseModel) {
        try {
            MakeToast.instance.shortToast(this@LoginActivity, output.message)
            progressDialog?.dismiss()
            val alert = android.support.v7.app.AlertDialog.Builder(this@LoginActivity,
                    R.style.AlertDialogTheme)
                    .setTitle(resources.getString(R.string.message))
                    .setPositiveButton(resources.getString(R.string.dialog_ok)) { dialog, which -> dialog.dismiss() }
            alert.setMessage(output.message).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun FingerPrintResult(response: Boolean, requestCode: Int) {
        Authenticated = response
        if (Authenticated) {
            if (requestCode == NORMAL_REQUEST) {
                if (!fingerPrintShared!!.contains("TouchEmail")) {
                    if (userNameField != null) {
                        fingerPrintShared!!.edit().putString("TouchEmail", userNameField!!.text.toString().trim { it <= ' ' })
                                .putString("TouchPassword", passwordField!!.text.toString().trim { it <= ' ' }).apply()
                    }
                } else {
                    username = fingerPrintShared!!.getString("TouchEmail", "")
                    password = fingerPrintShared!!.getString("TouchPassword", "")
                }
            } else {
                if (userNameField != null) {
                    fingerPrintShared!!.edit().putString("TouchEmail", userNameField!!.text.toString().trim { it <= ' ' })
                            .putString("TouchPassword", passwordField!!.text.toString().trim { it <= ' ' }).apply()
                }
            }
            if (!this@LoginActivity.isFinishing) {
                if (SweetAlertBox.sweetAlertDialog != null) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            SweetAlertBox().showProgressDialog(this@LoginActivity)
            Login()
        } else {
            if (progress != null)
                progress!!.dismiss()
        }
    }

    fun FingerprintSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.USE_FINGERPRINT), 0)
            } else {
                gotPermission()
            }
        } else
            fingerprintsupported = false
    }

    @RequiresApi(23)
    fun gotPermission() {
        try {
            val fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
            fingerprintsupported = fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

    }

    @TargetApi(23)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            -1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotPermission()
            }
            else -> {
            }
        }
    }

    companion object {

        private val EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        private val TAG = "SignInActivity"
        private val RC_SIGN_IN = 9001
        private val RC_LINKEDIN_SIGN_IN = 9002
        private val host = "api.linkedin.com"
        private val topCardUrl = "https://" + host + "/v1/people/~:" +
                "(id,first-name,last-name,email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))"
        var fingerprint_enabled: Boolean = false
        var fingerprintsupported = false
        var ADDTOWISHLIST: Boolean = false

        fun generateRandomPassword(): String {
            val random = SecureRandom()
            val letters = "abcdefghjklmnopqrstuvwxyzABCDEFGHJKMNOPQRSTUVWXYZ1234567890"
            val numbers = "1234567890"
            val specialChars = "!@#$%^&*_=+-/"
            var pw = ""
            for (i in 0..7) {
                val index = (random.nextDouble() * letters.length).toInt()
                pw += letters.substring(index, index + 1)
            }
            val indexA = (random.nextDouble() * numbers.length).toInt()
            pw += numbers.substring(indexA, indexA + 1)
            val indexB = (random.nextDouble() * specialChars.length).toInt()
            pw += specialChars.substring(indexB, indexB + 1)
            return pw
        }
    }
}
