package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent

import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.v7.content.res.AppCompatResources
import android.text.Html
import android.view.LayoutInflater
import android.view.View

import com.facebook.CallbackManager

import org.json.JSONException
import org.json.JSONObject

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.CartAdapter
import webkul.opencart.mobikul.adapterModel.CartAdapterModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.GetCallBack
import webkul.opencart.mobikul.LoginActivity
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.model.EmptyCartModel.EmptyCart
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.Cart
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.RemoveFromCart.RemoveFromCart
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.Utils
import webkul.opencart.mobikul.ViewProductSimple
import webkul.opencart.mobikul.databinding.ProceedToCheckoutDialogBinding

import android.content.Context.MODE_PRIVATE
import android.support.v7.app.AlertDialog
import android.widget.TextView


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CartHandler(private val mContext: Context, private val viewCart: ViewCart, private val cartAdapter: CartAdapter) : GetCallBack {
    private var mCallbackManager: CallbackManager? = null

    fun onClickEmptyCart(view: View) {
        val emptyCartCallback = object : Callback<EmptyCart> {
            override fun onResponse(call: Call<EmptyCart>, response: Response<EmptyCart>) {
                if (SweetAlertBox.sweetAlertDialog != null) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
                MakeToast().shortToast(mContext, response.body()!!.message)
                val intent = (mContext as Cart).intent
                mContext.finish()
                mContext.startActivity(intent)
            }

            override fun onFailure(call: Call<EmptyCart>, t: Throwable) {

            }
        }
        SweetAlertBox().showAreYouSurePopUp(mContext, mContext.getString(R.string.delete), mContext.getString(R.string.are_you_sure))
        SweetAlertBox.sweetAlertDialog!!.setConfirmClickListener {
            if (SweetAlertBox.sweetAlertDialog != null) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.getEmptyCartCall(mContext, RetrofitCustomCallback(emptyCartCallback, mContext))
        }
    }

    fun onClickAddToWishlist(view: View, detail: CartAdapterModel) {
        val shared = mContext.getSharedPreferences("customerData", Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val addtoWishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    view as TextView
                    MakeToast().shortToast(mContext, response.body()!!.message)
                    if (response.body()!!.status != null && response.body()!!.status == true) {
                        view.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.wishlist_selected),
                                null, null, null)
                    } else {
                        view.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.wishlishv3_product_page),
                                null, null, null)
                    }
                }

                override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

                }
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.addToWishlistCall(mContext, detail.productId.toString(), RetrofitCustomCallback(addtoWishlistCallback, mContext))
        } else {
            val alert = AlertDialog.Builder(mContext)
            alert.setNegativeButton(mContext.resources.getString(android.R.string.ok)) { dialog, which ->
                dialog.dismiss()
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }
            alert.setMessage(Html.fromHtml(mContext.resources.getString(R.string.wishlist_msg))).show()
        }
    }

    fun onClickProduct(view: View, data: CartAdapterModel) {
        val intent = Intent(mContext, ViewProductSimple::class.java)
        intent.putExtra("idOfProduct", data.productId)
        intent.putExtra("nameOfProduct", data.productTitle)
        mContext.startActivity(intent)
    }

    fun onClickRemoveItem(v: View, data: CartAdapterModel) {
        if (!data.productKey!!.equals("", ignoreCase = true)) {
            SweetAlertBox().showAreYouSurePopUp(mContext, mContext.getString(R.string.delete), mContext.getString(R.string.are_you_sure))
            SweetAlertBox.sweetAlertDialog!!.setConfirmClickListener { sweetAlertDialog ->
                sweetAlertDialog.dismissWithAnimation()
                val removeFromCartCallback = object : Callback<RemoveFromCart> {
                    override fun onResponse(call: Call<RemoveFromCart>, response: Response<RemoveFromCart>) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        MakeToast().shortToast(mContext, response.body()!!.message)
                        val total = response.body()!!.total!!
                        AppSharedPreference.editSharedPreference(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                                Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                        val icon = (mContext as Cart).itemCart!!.icon as LayerDrawable
                        Utils.setBadgeCount(mContext, icon, AppSharedPreference.getCartItems(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                        (mContext as Cart).updateCart()
                    }

                    override fun onFailure(call: Call<RemoveFromCart>, t: Throwable) {

                    }
                }
                val jo = JSONObject()
                try {
                    jo.put("key", data.productKey)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                Handler().postDelayed({
                    SweetAlertBox().showProgressDialog(mContext)
                    RetrofitCallback.removeFromCartCall(mContext, data.productKey!!, RetrofitCustomCallback(removeFromCartCallback, mContext))
                }, 200)
            }
        }
    }

    fun onClickSubtractQty(view: View, data: CartAdapterModel) {
        val current = view.tag as Int
        val current_qty = cartAdapter.getData(current).quantity
        var qty = Integer.parseInt(current_qty)
        qty--
        cartAdapter.getData(current).quantity = qty.toString()
        cartAdapter.notifyDataSetChanged()
    }

    fun onClickAddQty(view: View, data: CartAdapterModel) {
        val current = view.tag as Int
        val current_qty = cartAdapter.getData(current).quantity
        var qty = Integer.parseInt(current_qty)
        qty++
        cartAdapter.getData(current).quantity = qty.toString()
        cartAdapter.notifyDataSetChanged()
    }

    fun onclickCouponCode(v: View) {
        val couponCode = (mContext as Cart).binding.couponEdittxt.text.toString()
        val applyCoupan = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                MakeToast().shortToast(mContext, response.body()!!.message)
                SweetAlertBox.dissmissSweetAlertBox()
                val intent = (mContext as Cart).intent
                mContext.finish()
                mContext.startActivity(intent)
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

            }
        }
        mContext.binding.voucherEdittxt.setText("")
        if (couponCode != "") {
            val jo = JSONObject()
            try {
                jo.put("coupon", couponCode)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.applyCoupanCode(mContext, couponCode, RetrofitCustomCallback(applyCoupan, mContext))
        }
    }

    fun onClickVoucherCode(v: View) {
        val voucherCode = (mContext as Cart).binding.voucherEdittxt.text.toString()
        mContext.binding.voucherEdittxt.setText("")
        if (voucherCode != "") {
            val jo = JSONObject()
            try {
                jo.put("voucher", voucherCode)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val applyVoucher = object : Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    MakeToast().shortToast(mContext, response.body()!!.message)
                    SweetAlertBox.dissmissSweetAlertBox()
                    val intent = (mContext as Cart).intent
                    mContext.finish()
                    mContext.startActivity(intent)
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {

                }
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.applyVoucherCode(mContext, voucherCode.toString(), RetrofitCustomCallback(applyVoucher, mContext))
        }
    }

    fun onClickUpdateCart() {
        val updateCallback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                (mContext as Cart).updateCart()
                MakeToast().shortToast(mContext, response.body()!!.message)
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
            }
        }
        val qtyObj = JSONObject()
        for (i in 0 until viewCart.cart?.products?.size!!) {
            try {
                qtyObj.put(cartAdapter.cartData[i].productKey, cartAdapter.cartData[i].quantity)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        mContext as Cart
        if (!mContext.isOnlineCart()) {
            mContext.showDialog(mContext)
        } else {
            RetrofitCallback.updateCartCall(mContext, qtyObj, RetrofitCustomCallback(updateCallback, mContext))
            SweetAlertBox().showProgressDialog(mContext)
        }
    }


    fun onClickContinueShop(view: View) {
        val intent = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        (mContext as Cart).finish()
        mContext.startActivity(intent)
    }

    fun onClickProceedToCheckout(view: View) {
        val shared = mContext.getSharedPreferences("customerData", MODE_PRIVATE)
        if (!shared.getBoolean("isLoggedIn", false)) {
            val dialogBinding = ProceedToCheckoutDialogBinding.inflate(LayoutInflater.from(mContext))
            val sheetDialog = BottomSheetDialog(mContext)
            sheetDialog.setContentView(dialogBinding.root)
            dialogBinding.facebook.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.facebook), null, null, null)
            dialogBinding.google.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.googleplus), null, null, null)
            if (Cart.GUEST_STATUS!! && Cart.DOWNLOADABLE_PRODUCT_STATUS == 0) {
                dialogBinding.guestBtn.visibility = View.VISIBLE
            } else {
                dialogBinding.guestBtn.visibility = View.GONE
            }
            sheetDialog.show()
            val handler = CheckoutBottomSheetHandler(mContext, sheetDialog)
            handler.getCallback(mCallbackManager!!)
            dialogBinding.handler = handler
        } else {
            mContext as Cart
            if (!mContext.isOnlineCart()) {
                mContext.showDialog(mContext)
            } else {
                mContext.startActivity(Intent(mContext, CheckoutActivity::class.java))
            }
        }
    }

    override fun getCallback(mCallbackManager: CallbackManager) {
        this.mCallbackManager = mCallbackManager
    }
}


