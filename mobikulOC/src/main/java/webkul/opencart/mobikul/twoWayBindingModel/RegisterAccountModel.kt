package webkul.opencart.mobikul.twoWayBindingModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.BR
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ActivityCreateAccountBinding

class RegisterAccountModel(private val mContext: Context, private val createAccountBinding: ActivityCreateAccountBinding) : BaseObservable() {

    @get:Bindable("firstName")
    var firstName = ""
        set(firstName) {
            field = firstName
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable("lastName")
    var lastName = ""
        set(lastName) {
            field = lastName
            notifyPropertyChanged(BR.lastName)
        }
    private var email = ""
    @get:Bindable("telephone")
    var telephone = ""
        set(telephone) {
            field = telephone
            notifyPropertyChanged(BR.telephone)
        }

    @get:Bindable("fax")
    var fax = ""
        set(fax) {
            field = fax
            notifyPropertyChanged(BR.fax)

        }
    @get:Bindable("company")
    var company = ""
        set(company) {
            field = company
            notifyPropertyChanged(BR.company)

        }
    @get:Bindable("address1")
    var address1 = ""
        set(address1) {
            field = address1
            notifyPropertyChanged(BR.address1)

        }
    @get:Bindable("address2")
    var address2 = ""
        set(address2) {
            field = address2
            notifyPropertyChanged(BR.address2)

        }
    @get:Bindable("city")
    var city = ""
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)

        }
    @get:Bindable("zip")
    var zip = ""
        set(zip) {
            field = zip
            notifyPropertyChanged(BR.zip)

        }
    @get:Bindable("coutryName")
    var coutryName = ""
        set(coutryName) {
            field = coutryName
            notifyPropertyChanged(BR.coutryName)

        }
    @get:Bindable("stateName")
    var stateName = ""
        set(stateName) {
            field = stateName
            notifyPropertyChanged(BR.stateName)

        }
    @get:Bindable("password")
    var password = ""
        set(password) {
            field = password
            notifyPropertyChanged(BR.password)
        }
    @get:Bindable("confirmPassword")
    var confirmPassword = ""
        set(confirmPassword) {
            field = confirmPassword
            notifyPropertyChanged(BR.confirmPassword)
        }
    private val callback: Callback<BaseModel>

    init {
        callback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                if (response.body()!!.error == 1) {
                    createAccountBinding.emailAddress.error = mContext.getString(R.string.email_already_exit)
                    createAccountBinding.emailAddress.tag = "1"
                } else {
                    createAccountBinding.emailAddress.error = null
                    createAccountBinding.emailAddress.tag = "0"
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

            }
        }
    }

    @Bindable("email")
    fun getEmail(): String {
        if (Validation.isEmailValid(email)) {
            RetrofitCallback.checkEmail(mContext, email, RetrofitCustomCallback(callback, mContext))
        }
        return email
    }

    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }
}
