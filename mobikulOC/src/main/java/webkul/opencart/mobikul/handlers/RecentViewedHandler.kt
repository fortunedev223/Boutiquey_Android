package webkul.opencart.mobikul.handlers

import android.content.Context
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.ImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox


class RecentViewedHandler(val mContext: Context) {

    fun onClickAddToWishlist(view: View, adapteModel: HomePageAdapteModel) {
        val shared = mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
          val  wishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.error == 0) {
                        view as ImageView
                        MakeToast().shortToast(mContext, response.body()!!.message)
                        if (response.body()!!.status != null && response.body()!!.status == true) {
                            view.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.wishlist_selected))
                        } else {
                            view.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.wishlist_v3_unselected))
                        }
                    }
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.addToWishlistCall(mContext, adapteModel.productId.toString(), RetrofitCustomCallback(wishlistCallback, mContext))
        } else {
            SweetAlertBox().showWarningPopUp(mContext, "", mContext.resources.getString(R.string.wishlist_msg),
                    adapteModel.productId.toString())
        }
    }

}
