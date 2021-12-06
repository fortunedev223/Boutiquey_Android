package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.CurrencyAdapterModel
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.roomdatabase.AppDataBase
import webkul.opencart.mobikul.SplashScreen
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.ViewProductSimple



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CurrencyHandler(private val mcontext: Context) {
    private var baseModelCallback: Callback<BaseModel>? = null

    fun onClickCategory(view: View, dataholder: CurrencyAdapterModel) {
        baseModelCallback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                SweetAlertBox.dissmissSweetAlertBox()
                if (response.body()!!.error == 0) {
                    val intent = Intent(mcontext, SplashScreen::class.java)
                    if (mcontext is MainActivity) {
                        mcontext.finish()
                    } else if (mcontext is ViewProductSimple) {
                        mcontext.finish()
                    }
                    AppDataBase.getAppDataBaseInstance(mcontext).getDao().deleteDatafromTable()
                    mcontext.startActivity(intent)
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
        }
        SweetAlertBox().showProgressDialog(mcontext)
        RetrofitCallback.currencyCall(mcontext, dataholder.code!!, RetrofitCustomCallback(baseModelCallback, mcontext))

    }
}
