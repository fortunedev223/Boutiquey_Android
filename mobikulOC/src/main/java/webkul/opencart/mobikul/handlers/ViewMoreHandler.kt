package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.facebook.login.widget.ProfilePictureView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.activity.ViewMoreActivity
import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.HomeBrands
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import java.util.ArrayList

class ViewMoreHandler(var mcontext: Context) : HomeBrands {
    private var addToCartModelCallback: Callback<AddToCartModel>? = null
    private var wishlistCallback: Callback<AddtoWishlist>? = null
    override fun getBrands(carousalAdapterModels: ArrayList<CarousalAdapterModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun onClickImage(view: View, data: HomePageAdapteModel) {
        Log.d("Image", "Handler-----> " + data.productId + data.product)
        val intent = Intent(mcontext, ViewProductSimple::class.java)
        try {
            Log.d("Product Data", "onClickImage: $data")
            intent.putExtra("idOfProduct", data.productId)
            intent.putExtra("nameOfProduct", data.product!!.toString())
        } catch (e: Throwable) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        mcontext.startActivity(intent)
        (mcontext as ViewMoreActivity).overridePendingTransition(R.anim.reverse_fadein, R.anim.nothing)
    }

    fun onClickAddToWishlist(view: View, adapteModel: HomePageAdapteModel) {
        val shared = mcontext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            wishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    MakeToast().shortToast(mcontext, response.body()!!.message)
                    if (response.body()!!.error == 0) {
                        view as ImageView
                        if (response.body()!!.status!!) {
                            view.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_selected))
                        } else {
                            view.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_v3_unselected))
                        }
                    }
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

                }
            }
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.addToWishlistCall(mcontext, adapteModel.productId.toString(), RetrofitCustomCallback(wishlistCallback, mcontext))
        } else {
            SweetAlertBox().showWarningPopUp(mcontext, "",
                    mcontext.resources.getString(R.string.wishlist_msg),
                    adapteModel.productId.toString())
        }

    }

    fun onClickAddToCart(view: View, model: HomePageAdapteModel) {
        println("in wish list data === ")
        if (model.isHasOption) {
            val intent = Intent(mcontext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", model.productId)
            intent.putExtra("nameOfProduct", model.product)
            mcontext.startActivity(intent)
        } else {
            var mcontext: BaseActivity = mcontext as BaseActivity
            if (mcontext.isInternetAvailable) {
                addToCartModelCallback = object : Callback<AddToCartModel> {
                    override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                        val total = response.body()!!.total!!
                        Log.d(ProfilePictureView.TAG, "TotalITems-------> $total")
                        MakeToast().shortToast(mcontext, response.body()!!.message)
                        AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                        if (mcontext is ViewMoreActivity) {
                            if (mcontext.itemCart != null) {
                                val icon = mcontext.itemCart!!.icon as LayerDrawable
                                Log.d(ProfilePictureView.TAG, "CartCount-----> " + AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                                Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                            }
                        }
                        SweetAlertBox.dissmissSweetAlertBox()


                    }

                    override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                    }
                }
                SweetAlertBox().showProgressDialog(mcontext)
                val callback: Callback<BaseModel>? = null
                RetrofitCallback.addToCartWithoutOptionCall(mcontext, model.productId!!, "1", RetrofitCustomCallback(addToCartModelCallback, mcontext))
            } else {
                var jsonObject = JSONObject()
                AppDataBaseController.setAddCartData(mcontext, AddCartTable(0, model.productId!!, "1", jsonObject.toString()))
                MakeToast.instance.shortToast(mcontext, "You are offline.Your Product will add into cart when inernet is available.")
            }
        }
    }

}
