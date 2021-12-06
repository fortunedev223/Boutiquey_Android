package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.GetWishlistAdapterModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.MyWishlistActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.Utils
import webkul.opencart.mobikul.ViewProductSimple

import com.facebook.login.widget.ProfilePictureView.TAG
import webkul.opencart.mobikul.databinding.ItemMyWishlistBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class GetWishlistHandler(private val mcontext: Context) {
    private var baseModelCallback: Callback<BaseModel>? = null
    private var addToCartModelCallback: Callback<AddToCartModel>? = null
    private var wishlistBinding: ItemMyWishlistBinding? = null
    private var count: Int = 1


    fun setBinding(wishlistBinding: ItemMyWishlistBinding) {
        this.wishlistBinding = wishlistBinding
    }

    fun onClickAddtoCart(data: GetWishlistAdapterModel) {

        addToCartModelCallback = object : Callback<AddToCartModel> {
            override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                MakeToast().shortToast(mcontext, response.body()!!.message)
                SweetAlertBox.dissmissSweetAlertBox()
                val total = response.body()!!.total!!
                Log.d(TAG, "TotalITems-------> $total")
                AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                val icon = (mcontext as MyWishlistActivity).itemCart!!.icon as LayerDrawable
                Log.d(TAG, "CartCount-----> " + AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                val scale = ScaleAnimation(0f, 1f, 0f, 1f, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f)
                scale.duration = 700
                scale.interpolator = OvershootInterpolator()
                mcontext.wishlistBinding!!.toolbar!!.startAnimation(scale)
            }

            override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {

            }
        }
        if (data.isHasOption) {
            val intent = Intent(mcontext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", data.productId)
            intent.putExtra("nameOfProduct", data.productName)
            mcontext.startActivity(intent)
        } else {
            println(" Count == " + count.toString())
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.addToCartWithoutOptionCall(mcontext, data.productId!!,
                    count.toString(), RetrofitCustomCallback(addToCartModelCallback, mcontext))
        }
    }

    fun onClickProduct(model: GetWishlistAdapterModel) {
        val intent = Intent(mcontext, ViewProductSimple::class.java)
        intent.putExtra("idOfProduct", model.productId)
        intent.putExtra("nameOfProduct", model.productName)
        mcontext.startActivity(intent)
    }


    fun onClickRemoveFromWishlist(data: GetWishlistAdapterModel) {
        SweetAlertBox().showAreYouSurePopUp(mcontext, mcontext.getString(R.string.delete), mcontext.getString(R.string.are_you_sure))
        SweetAlertBox.sweetAlertDialog!!.setConfirmClickListener { sweetAlertDialog ->
            sweetAlertDialog.dismissWithAnimation()
            Handler().postDelayed({
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.removeFromWishlist(mcontext, data.productId!!, RetrofitCustomCallback(baseModelCallback, mcontext))
            }, 200)
        }
        SweetAlertBox.sweetAlertDialog!!.setOnCancelListener { }
        baseModelCallback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                MakeToast().shortToast(mcontext, response.body()!!.message)
                (mcontext as MyWishlistActivity).updateWishlist()
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

            }
        }
    }
}
