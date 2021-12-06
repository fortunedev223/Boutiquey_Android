package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.DashBoard
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.adapterModel.LoginAdapterModel
import webkul.opencart.mobikul.LoginActivity
import webkul.opencart.mobikul.model.LoginModel.LoginModel
import webkul.opencart.mobikul.R


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class LoginHandler(private val mcontext: Context) {
    private val addtoWishlistCallback: Callback<AddtoWishlist>

    private var loginModelCallback: Callback<LoginModel>? = null

    init {
        addtoWishlistCallback = object : Callback<AddtoWishlist> {
            override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                MakeToast().shortToast(mcontext, response.body()!!.message)
                LoginActivity.ADDTOWISHLIST = false
            }

            override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

            }
        }
    }

    fun onClickLogin(loginData: LoginAdapterModel) {
        SweetAlertBox().showProgressDialog(mcontext)
        loginModelCallback = object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response.body()!!.error == 1) {
                    val snackbar = Snackbar.make((mcontext as LoginActivity).currentFocus!!, response.body()!!.message, Snackbar.LENGTH_LONG)
                    val layout = snackbar.view as Snackbar.SnackbarLayout
                    snackbar.show()
                } else {
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, response.body()!!.cartTotal!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_EMAIL, response.body()!!.email!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_ID, response.body()!!.customerId!!)
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CUSTOMER_NAME, response.body()!!.firstname!!)
                    AppSharedPreference.editBooleanSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_IS_LOGGED_IN, true)
                    MakeToast().shortToast(mcontext, mcontext.getString(R.string.welcome) + " " + response.body()!!.firstname + "  " + response.body()!!.lastname)
                    if (LoginActivity.ADDTOWISHLIST) {
                        val productId = (mcontext as LoginActivity).intent.extras!!.getString("productId")
                        RetrofitCallback.addToWishlistCall(mcontext, productId!!, RetrofitCustomCallback(addtoWishlistCallback, mcontext))
                    }
                    if ((mcontext as webkul.opencart.mobikul.LoginActivity).intent.hasExtra("redirect")) {
                        val intent_name = Intent()
                        intent_name.setClass(mcontext, webkul.opencart.mobikul.Cart::class.java)
                        mcontext.finish()
                        mcontext.startActivity(intent_name)
                    } else {
                        val intent_name = Intent()
                        mcontext.finish()
                        intent_name.setClass(mcontext, DashBoard::class.java)
                        mcontext.startActivity(intent_name)
                    }

                }

            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {

            }
        }
        RetrofitCallback.userLoginCall(mcontext, loginData.username, loginData.password, RetrofitCustomCallback(loginModelCallback, mcontext))
    }
}


