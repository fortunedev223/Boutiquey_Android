package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.ImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.SearchDialogActivity
import webkul.opencart.mobikul.CategoryActivity
import webkul.opencart.mobikul.model.GetHomePage.Product
import webkul.opencart.mobikul.model.RecentSearchModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.roomdatabase.RecentSearchTable
import webkul.opencart.mobikul.ViewProductSimple
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
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
class RecentSearchHandler(mcontext: Context) {
    private var mContext: Context? = mcontext

    fun onClickViewedProd(view: View, dataholder: RecentSearchModel) {
        try {
            val intent = Intent(mContext, CategoryActivity::class.java)
            intent.putExtra("search", dataholder.prouductName)
            mContext!!.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun onClickSearchProd(view: View, dataholder: RecentSearchTable) {
        try {
            val intent = Intent(mContext, CategoryActivity::class.java)
            intent.putExtra("search", dataholder.word)
            mContext!!.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun onClickAddToWishlist(view: View, adapteModel: Product) {
        val shared = mContext?.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getBoolean("isLoggedIn", false)
        if (isLoggedIn!!) {
            val wishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    MakeToast().shortToast(mContext!!, response.body()!!.message)
                    if (response.body()!!.error == 0) {
                        view as ImageView
                        if (response.body()!!.status != null && response.body()!!.status == true) {
                            view.setImageDrawable(AppCompatResources.getDrawable(mContext!!, R.drawable.wishlist_selected))
                        } else {
                            view.setImageDrawable(AppCompatResources.getDrawable(mContext!!, R.drawable.wishlist_v3_unselected))
                        }
                    } else {

                    }
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            SweetAlertBox().showProgressDialog(mContext!!)
            RetrofitCallback.addToWishlistCall(mContext!!, adapteModel.productId.toString(), RetrofitCustomCallback(wishlistCallback, mContext))
        } else {
            SweetAlertBox().showWarningPopUp(mContext!!, "", mContext!!.resources.getString(R.string.wishlist_msg),
                    adapteModel.productId.toString())
        }
    }

    fun onClickViewedLatestProd(view: View, dataholder: Product) {
        val intent = Intent(mContext, ViewProductSimple::class.java)
        try {
            intent.putExtra("idOfProduct", dataholder.productId)
            println("nameofProduct === " + dataholder.name)
            intent.putExtra("nameOfProduct", dataholder.name)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        mContext!!.startActivity(intent)
        (mContext as SearchDialogActivity).overridePendingTransition(R.anim.reverse_fadein, R.anim.nothing)
    }

}