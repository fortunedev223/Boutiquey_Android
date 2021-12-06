package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.SimiliarProductsAdapterModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.LoginActivity
import webkul.opencart.mobikul.Utils
import webkul.opencart.mobikul.ViewProductSimple

import android.content.Context.MODE_PRIVATE
import android.support.v7.content.res.AppCompatResources
import android.widget.ImageView
import com.facebook.login.widget.ProfilePictureView.TAG
import webkul.opencart.mobikul.R

class SimilarProductHandler(private val mcontext: Context) {
    private var addToCartModelCallback: Callback<AddToCartModel>? = null
    private var addtoWishlistCallback: Callback<AddtoWishlist>? = null

    fun onClickProduct(v: View, dataHolder: SimiliarProductsAdapterModel) {
        val intent = Intent(mcontext, ViewProductSimple::class.java)
        intent.putExtra("idOfProduct", dataHolder.productId)
        intent.putExtra("nameOfProduct", dataHolder.productName)
        mcontext.startActivity(intent)
    }

    fun onClickAddToWishlist(v: View, dataHolder: SimiliarProductsAdapterModel) {

        addtoWishlistCallback = object : Callback<AddtoWishlist> {
            override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                SweetAlertBox.dissmissSweetAlertBox()
                v as ImageView
                MakeToast().shortToast(mcontext, response.body()!!.message)
                if (response.body()!!.status != null && response.body()!!.status == true) {
                    v.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_selected))
                } else {
                    v.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_v3_unselected))
                }
            }

            override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

            }
        }

        val shared = mcontext.getSharedPreferences("customerData", MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.addToWishlistCall(mcontext, dataHolder.productId!!, RetrofitCustomCallback(addtoWishlistCallback, mcontext))
        } else {
            mcontext.startActivity(Intent(mcontext, LoginActivity::class.java))
        }

    }

    fun onClickAddToCart(v: View, dataHolder: SimiliarProductsAdapterModel) {
        addToCartModelCallback = object : Callback<AddToCartModel> {
            override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                MakeToast.instance.shortToast(mcontext, response.body()?.message)
                if (response.body()?.error != 1) {
                    val total = response.body()!!.total!!
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                            Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                    val icon = (mcontext as ViewProductSimple).itemCart!!.icon as LayerDrawable
                    Log.d(TAG, "CartCount-----> " + AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                    Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                } else {
                    MakeToast.instance.shortToast(mcontext, response.body()?.message)
                }
            }

            override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                t.printStackTrace()
            }
        }

        if (dataHolder.isHasOption) {
            val intent = Intent(mcontext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", dataHolder.productId)
            intent.putExtra("nameOfProduct", dataHolder.productName)
            mcontext.startActivity(intent)
        } else {
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.addToCartWithoutOptionCall(mcontext, dataHolder.productId!!, "1", RetrofitCustomCallback(addToCartModelCallback, mcontext))
        }

    }
}
