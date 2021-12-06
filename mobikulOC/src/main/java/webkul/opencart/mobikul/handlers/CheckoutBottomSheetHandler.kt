package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomSheetDialog
import android.util.Log
import android.view.View
import android.widget.Toast

import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import retrofit2.Call
import retrofit2.Response
import webkul.opencart.mobikul.Cart
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.CreateAccountActivity
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.GetCallBack
import webkul.opencart.mobikul.LoginActivity
import webkul.opencart.mobikul.model.SocailLoginModel.SocailLogin
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CheckoutBottomSheetHandler(private val mcontext: Context, private val sheetDialog: BottomSheetDialog) : GoogleApiClient.OnConnectionFailedListener, GetCallBack {
    private val CART_RESULT = 10
    private var mCallbackManager: CallbackManager? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private val mAuth: FirebaseAuth
    private val socailLoginCallback: retrofit2.Callback<SocailLogin>

    init {
        mAuth = FirebaseAuth.getInstance()
        socailLoginCallback = object : retrofit2.Callback<SocailLogin> {
            override fun onResponse(call: Call<SocailLogin>, response: Response<SocailLogin>) {
                if (SweetAlertBox.sweetAlertDialog != null) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
                if (response.body()!!.error == 1) {
                    MakeToast().shortToast(mcontext, response.body()!!.message)
                } else {
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, response.body()!!.cartTotal!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_EMAIL, response.body()!!.email!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_ID, response.body()!!.customerId!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_NAME, response.body()!!.firstname!!)
                    AppSharedPreference.editBooleanSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_IS_LOGGED_IN, true)

                }
            }

            override fun onFailure(call: Call<SocailLogin>, t: Throwable) {

            }
        }
        if (mGoogleApiClient == null) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(mcontext.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            mGoogleApiClient = GoogleApiClient.Builder(mcontext)
                    //                    .enableAutoManage(getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    //                        @Override
                    //                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    //
                    //                        }
                    //                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build()
        }
    }

    fun onClickLogin(v: View) {
        sheetDialog.dismiss()
        val intent = Intent(mcontext, LoginActivity::class.java)
        intent.putExtra("redirect", "cart")
        (mcontext as Cart).startActivity(intent)
    }

    fun onClickRegister(v: View) {
        sheetDialog.dismiss()
        val intent = Intent(mcontext, CreateAccountActivity::class.java)
        intent.putExtra("redirect", "cart")
        mcontext.startActivity(intent)
    }

    fun onClickGuest(v: View) {
        val intent = Intent(mcontext, CheckoutActivity::class.java)
        intent.putExtra("check", "guest")
        mcontext.startActivity(intent)
        sheetDialog.dismiss()
    }

    fun onClickGoogle() {
        sheetDialog.dismiss()
        signIn()
    }

    fun onClickFaceBook() {
        sheetDialog.dismiss()
        signInFaceBook()
    }

    private fun signInFaceBook() {
        LoginManager.getInstance().logOut()
        val loginButton = LoginButton(mcontext)
        loginButton.setReadPermissions("email", "public_profile", "user_birthday", "user_friends")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("Success", "facebook:onSuccess:$loginResult")
                firebaseAuthWithFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d("Cancel", "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("Exception", "facebook:onError", error)
            }
        })
        loginButton.performClick()
    }

    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        (mcontext as Cart).startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mcontext as Cart) { task ->
                    Log.d("Bottom", "signInWithCredential:onComplete:" + task.isSuccessful)
                    if (!task.isSuccessful) {
                        Log.w("Bottom", "signInWithCredential", task.exception)
                        Toast.makeText(mcontext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        val fName = task.result?.additionalUserInfo?.profile?.get("first_name") as String
                        val lName = task.result!!.additionalUserInfo.profile["last_name"] as String
                        val email = task.result!!.additionalUserInfo.profile["email"] as String
                        val id = task.result!!.additionalUserInfo.providerId
                        SweetAlertBox().showProgressDialog(mcontext)
                        RetrofitCallback.addSocailLogin(mcontext, fName, lName, email, id, RetrofitCustomCallback(socailLoginCallback, mcontext))
                    }
                }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("Google", "firebaseAuthWithGoogle:" + acct.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mcontext as Cart) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(mcontext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    override fun getCallback(mCallbackManager: CallbackManager) {
        this.mCallbackManager = mCallbackManager
    }

    companion object {
        private val RC_SIGN_IN = 9001
    }
}
