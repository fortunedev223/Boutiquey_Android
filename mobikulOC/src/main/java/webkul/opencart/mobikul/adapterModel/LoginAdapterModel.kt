package webkul.opencart.mobikul.adapterModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.support.v7.content.res.AppCompatResources

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.BR
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ActivityLoginBinding



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class LoginAdapterModel(private val mContext: Context, private val activityLoginBinding: ActivityLoginBinding) : BaseObservable() {
    @get:Bindable
    var username = mContext.getString(R.string.demo_username)
        set(username) {
            field = username
            notifyPropertyChanged(BR.username)
        }
    @get:Bindable
    var password = mContext.getString(R.string.demo_password)
        set(password) {
            field = password
            notifyPropertyChanged(BR.password)
        }
    private val callback: Callback<BaseModel>

    val validUsername: String
        @Bindable("username")
        get() {
            val err = ""
            if (username == "") {
                return ""
            } else if (Validation.isEmailValid(username)) {
                RetrofitCallback.checkEmail(mContext, this.username, RetrofitCustomCallback(callback, mContext))
                return "VALID EMAIL ADDRESS"
            } else {
                return "NOT VALID EMAIL ADDRESS"
            }
        }

    val validPassword: String
        @Bindable("password")
        get() {
            val err = ""
            return if (password.length == 0) {
                ""
            } else if (password.length >= 0 && password.length < 4) {
                "PASSWORD MUST HAVE AT LEAST 4 CHARACTERS"
            } else {
                "VALID PASSWORD"
            }
        }

    init {
        callback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                if (response.body()!!.error == 1) {
                    val emailCheck = AppCompatResources.getDrawable(mContext, R.drawable.email_check)
                    activityLoginBinding.etUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, emailCheck, null)
                } else {
                    val emailCheck = AppCompatResources.getDrawable(mContext, R.drawable.email_not_exit)
                    activityLoginBinding.etUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, emailCheck, null)
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

            }
        }
    }
}
