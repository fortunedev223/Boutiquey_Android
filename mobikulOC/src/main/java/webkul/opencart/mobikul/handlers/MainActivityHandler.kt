package webkul.opencart.mobikul.handlers

import android.app.Dialog
import android.content.Context
import android.content.Intent

import android.graphics.drawable.LayerDrawable
import android.provider.MediaStore
import android.view.View

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.HomeBrands
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.CustomerLogoutModel.CustomerLogout
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.analytics.MobikulApplication

import android.content.Context.MODE_PRIVATE
import android.support.v4.widget.DrawerLayout
import android.support.v7.content.res.AppCompatResources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import org.json.JSONObject
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.activity.*
import webkul.opencart.mobikul.adapter.MainAcitivityAdapter
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.helper.Type
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.databinding.GdprNotificationInfoLayoutBinding
import webkul.opencart.mobikul.databinding.MainProductSingleViewBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class MainActivityHandler(var mContext: Context) : HomeBrands {
    private var carousalAdapterModels: ArrayList<CarousalAdapterModel>? = null
    var mMobikulApplication: MobikulApplication
    var homeDataModel: HomeDataModel? = null
    private var more: Boolean = false
    private var type: Int? = null
    private var mDrawerLayout: DrawerLayout? = null
    var mainProductSingleViewBinding: MainProductSingleViewBinding? = null
    private var mainAcitivityAdapter: MainAcitivityAdapter? = null

    init {
        mMobikulApplication = (mContext as MainActivity).application as MobikulApplication
    }

    fun onClickViewAll(view: View) {
        val id = view.id
        if (id == R.id.browseByBrands_view_all) {
            if (carousalAdapterModels != null) {
                val intent = Intent(mContext, BrowerByBrands::class.java)
                intent.putParcelableArrayListExtra("data", carousalAdapterModels)
                mContext.startActivity(intent)
                (mContext as MainActivity).overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun onClickLatest() {
        val intent = Intent(mContext, ViewMoreActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("title", mContext.getString(R.string.latest_product_label))
        mContext.startActivity(intent)
        (mContext as MainActivity).overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }

    fun onClickFeatured() {
        val intent = Intent(mContext, ViewMoreActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("title", mContext.getString(R.string.featured_product_label))
        mContext.startActivity(intent)
        (mContext as MainActivity).overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }

    fun viewMore(view: View) {
        if (view.getTag() == -1) {
            val intent = Intent(mContext, ViewMoreActivity::class.java)
            intent.putExtra("type", type)
            if (Type.FEATURED == type)
            else
                intent.putExtra("title", mContext.getString(R.string.latest_product_label))
            mContext.startActivity(intent)
            (mContext as MainActivity).overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        }
    }

    fun onClickViewAll(id: String,title:String) {
        val intent = Intent(mContext, ViewMoreActivity::class.java)
        intent.putExtra("type", id)
        intent.putExtra("title", title)
        mContext.startActivity(intent)
    }

    fun onClickAddProduct(view: View) {
        mContext.startActivity(Intent(mContext, mMobikulApplication.addProduct()))
    }

    fun onClickProductList(view: View) {
        mContext.startActivity(Intent(mContext, mMobikulApplication.productList()))
    }

    fun setAdapter(mainAcitivityAdapter: MainAcitivityAdapter) {
        this.mainAcitivityAdapter = mainAcitivityAdapter
    }

    fun onClickImage(view: View, data: HomePageAdapteModel) {
        val intent = Intent(mContext, ViewProductSimple::class.java)
        try {
            intent.putExtra("idOfProduct", data.productId)
            intent.putExtra("nameOfProduct", data.product!!.toString())
        } catch (e: NumberFormatException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        mContext.startActivity(intent)
        (mContext as MainActivity).overridePendingTransition(R.anim.reverse_fadein, R.anim.nothing)
    }

    fun setDataBinding(mainProductSingleViewBinding: MainProductSingleViewBinding) {
        this.mainProductSingleViewBinding = mainProductSingleViewBinding
    }

    fun onClickAddToWishlist(view: View, adapteModel: HomePageAdapteModel) {
        val shared = mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val wishlistCallback = object : Callback<AddtoWishlist> {
                override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response.body()!!.error == 0) {
                        view as ImageView
                        MakeToast().shortToast(mContext, response.body()!!.message)
                        if (response.body()!!.status != null && response.body()!!.status == true) {
                            adapteModel.wishlist_status = true
                            println(" STATUS----------- " + adapteModel.wishlist_status)
                            view.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.wishlist_selected))
                        } else {
                            adapteModel.wishlist_status = false
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

    fun onClickAddToCart(view: View, model: HomePageAdapteModel) {
        if (model.isHasOption) {
            val intent = Intent(mContext, ViewProductSimple::class.java)
            intent.putExtra("idOfProduct", model.productId)
            intent.putExtra("nameOfProduct", model.product)
            mContext.startActivity(intent)
        } else {
            val mcontext: BaseActivity = mContext as BaseActivity
            if (mcontext.isInternetAvailable) {
                val addToCartModelCallback = object : Callback<AddToCartModel> {
                    override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        val total = response.body()!!.total!!
                        AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                        val icon = (mcontext as MainActivity).homeCartIcon!!.icon as LayerDrawable
                        Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                        MakeToast().shortToast(mcontext, response.body()!!.message)
                    }

                    override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                    }
                }
                SweetAlertBox().showProgressDialog(mcontext)
                val callback: Callback<BaseModel>? = null
                RetrofitCallback.addToCartWithoutOptionCall(mcontext, model.productId!!,
                        "1", RetrofitCustomCallback(addToCartModelCallback, mcontext))
            } else {
                var jsonObject = JSONObject()
                AppDataBaseController.setAddCartData(mcontext, AddCartTable(0, model.productId!!, "1", jsonObject.toString()))
                MakeToast.instance.shortToast(mcontext, "You are offline.Your Product will add into cart when inernet is available.")
            }
        }
    }

    fun onClickOrderReturn(view: View) {
        val intent = Intent(mContext, OrderReturnView::class.java)
        mContext.startActivity(intent)
    }

    fun onClickTransaction(view: View) {
        val intent = Intent(mContext, PointsAndTransactions::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("Transactions", "1")
        mContext.startActivity(intent)
    }

    fun onClickSellerProfile(view: View) {
        val intent = Intent(mContext, mMobikulApplication.viewMarketPlaceHome())
        mContext.startActivity(intent)
    }

    fun onClickNotificationInfo(view: View, homeDataModel: HomeDataModel) {
        mDrawerLayout!!.closeDrawers()
        val dialog = Dialog(mContext)
        val layoutBinding = GdprNotificationInfoLayoutBinding.inflate(LayoutInflater.from(mContext))
        dialog.setContentView(layoutBinding.root)
        layoutBinding.content.setText(homeDataModel.gdprDescription)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutBinding.close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun onClickSellerDashboard(view: View) {
        val intent = Intent(mContext, mMobikulApplication.viewSellerDashBoard())
        mContext.startActivity(intent)
    }

    fun onClickSellerOrder(view: View) {
        val intent = Intent(mContext, mMobikulApplication.viewSellerOrderHistory())
        mContext.startActivity(intent)
    }

    fun onClickReward(view: View) {
        val intent = Intent(mContext, PointsAndTransactions::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("Points", "1")
        mContext.startActivity(intent)
    }

    fun onClickDashBoard(view: View) {
        mContext.startActivity(Intent(mContext, DashBoard::class.java))
    }

    fun onClickEditAccountInformation(view: View) {
        val intent = Intent(mContext, AccountinfoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("changeAccountInfo", "1")
        mContext.startActivity(intent)
    }

    fun onClickChangePassword(view: View) {
        val intent = Intent(mContext, AccountinfoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra("changePassword", "1")
        mContext.startActivity(intent)
    }

    fun onClickDownloadProducts(view: View) {
        mContext.startActivity(Intent(mContext, MyDownloadsActivity::class.java))
    }

    fun onClickWishlist(view: View) {
        mContext.startActivity(Intent(mContext, MyWishlistActivity::class.java))
    }

    fun onClickAddressBook(view: View) {
        mContext.startActivity(Intent(mContext, AddrBookActivity::class.java))
    }

    fun onClickMyOrders(view: View) {
        mContext.startActivity(Intent(mContext, MyOrders::class.java))
    }

    fun onClickLogout(view: View) {
        val logoutCallback = object : Callback<CustomerLogout> {
            override fun onResponse(call: Call<CustomerLogout>, response: Response<CustomerLogout>) {
                AppDataBaseController.deleteAllRecentViewed(mContext)
                AppDataBaseController.deleteAllHomeData(mContext)
                AppDataBaseController.deleteAllResentSearchData(mContext)
                SweetAlertBox.dissmissSweetAlertBox()
                val shared = mContext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, MODE_PRIVATE)
                shared.edit().clear().apply()
                MakeToast().shortToast(mContext, mContext.getString(R.string.logout_msg))
                val intent = (mContext as MainActivity).intent
                (mContext as MainActivity).finish()
                intent.putExtra("updateHome", true)
                mContext.startActivity(intent)
                addToken()
            }

            override fun onFailure(call: Call<CustomerLogout>, t: Throwable) {

            }
        }
        SweetAlertBox().showProgressDialog(mContext)
        RetrofitCallback.customerLogoutCall(mContext, logoutCallback)
    }

    private fun addToken() {
        SweetAlertBox.instance.showProgressDialog(mContext)
        RetrofitCallback.apiLoginCall(mContext, object : Callback<ApiLoginModel> {
            override fun onFailure(call: Call<ApiLoginModel>?, t: Throwable?) {
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onResponse(call: Call<ApiLoginModel>?, response: Response<ApiLoginModel>?) {
                if (response?.body()?.fault != 1) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    AppSharedPreference.editSharedPreference(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME,
                            Constant.CUSTOMER_SHARED_PREFERENCE_KEY_WK_TOKEN, response?.body()?.wkToken.toString())
                }
            }
        })
    }

    fun onClickNotification(view: View) {
        mContext.startActivity(Intent(mContext, NotificationActivity::class.java))
    }

    fun onClickLogin(view: View) {
        mContext as MainActivity
        val intent = Intent(mContext, LoginActivity::class.java)
        intent.putExtra("fromHome", "")
        (mContext as MainActivity).startActivityForResult(intent, 1010)
    }

    fun onClickSignUp(view: View) {
        val intent = Intent(mContext, CreateAccountActivity::class.java)
        mContext.startActivity(intent)
    }

    fun onClickFacebook(v: View) {
        if (!(mContext as MainActivity).checkConnection()) {
            (mContext as MainActivity).showDialog(mContext)
        } else {
            (mContext as MainActivity).openFaceBookLogin(true)
        }
    }

    fun onClickGmail(v: View) {
        if (!(mContext as MainActivity).checkConnection()) {
            (mContext as MainActivity).showDialog(mContext)
        } else {
            (mContext as MainActivity).openGoogleLogin(true)
        }
    }

    fun onClickProfileChange(view: View) {
        val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("outputX", 256)
        intent.putExtra("outputY", 256)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("return-data", true)
        (mContext as MainActivity).startActivityForResult(intent, 1)
    }

    fun onClickChangeProfile(view: View) {

        MakeToast().shortToast(mContext, "Change Profile")
    }

    override fun getBrands(carousalAdapterModels: ArrayList<CarousalAdapterModel>) {
        this.carousalAdapterModels = carousalAdapterModels
    }

    fun onClickFeatureProduct(view: View) {
        MakeToast().shortToast(mContext, "onClickFeatureProduct")
    }

    fun onClickLatestProduct(view: View) {
        MakeToast().shortToast(mContext, "onClickLatestProduct")
    }

    fun setHomeModelData(homeDataModel: HomeDataModel) {
        this.homeDataModel = homeDataModel
    }

    fun setDrawerLayout(mDrawerLayout: DrawerLayout) {
        this.mDrawerLayout = mDrawerLayout
    }


}
