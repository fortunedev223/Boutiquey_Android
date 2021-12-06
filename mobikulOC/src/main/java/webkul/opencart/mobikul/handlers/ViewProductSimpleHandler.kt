package webkul.opencart.mobikul.handlers

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.content.res.AppCompatResources
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.crashlytics.android.Crashlytics

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.util.HashMap

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.ProductDetails
import webkul.opencart.mobikul.adapterModel.ViewProductSimpleBannerAdapterModel
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.Cart
import webkul.opencart.mobikul.CustomoptionData
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.callback.OnCustomPasser
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.GetProduct.ProductDetail
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.Utils
import webkul.opencart.mobikul.ViewPagerExampleActivity
import webkul.opencart.mobikul.ViewProductSimple
import webkul.opencart.mobikul.offline.database.DataBaseHandler

import com.facebook.login.widget.ProfilePictureView.TAG
import webkul.opencart.mobikul.activity.Review_List
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.callback.WishListStatus
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.databinding.GdprNotificationInfoLayoutBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class ViewProductSimpleHandler(private val mcontext: Context, private val wishListStatus: WishListStatus) : BaseActivity(), OnCustomPasser {
    private var writeProductReview: Callback<BaseModel>? = null
    private var customList: ArrayList<CustomoptionData>? = null
    private var checklist: HashMap<*, *>? = null
    private var fileCode: String? = null
    private var radioProductOptionValueId: Int = 0
    private var productOptionValueId: Int = 0
    private var optionsObj: JSONObject? = null
    private val mOfflineDataBaseHandler: DataBaseHandler
    lateinit var dialog: Dialog
    var INTENT_CHECK = 100
    private var currentRating: String? = null
    private var mDrawerLayout: DrawerLayout? = null

    init {
        radioSelectOption = ArrayList()
        radioSelectionMap = HashMap()
        selectCustomMap = HashMap()
        mOfflineDataBaseHandler = DataBaseHandler(mcontext)
    }

    fun onClickPrevious(view: View, detail: ProductDetail) {
        val intent = (mcontext as ViewProductSimple).intent
        intent.putExtra("idOfProduct", detail.productPrev!![0].productId)
        intent.putExtra("nameOfProduct", detail.productPrev!![0].name)
        mcontext.startActivity(intent)
        mcontext.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        mcontext.finish()
    }

    fun onClickshareProduct(view: View, detail: ProductDetail) {
        if (FIRSTCLICK == 1) {
            val sendIntent = Intent()
            val share = Constant.BASE_URL + "?name=" + detail.getName() + "&product_id=" + detail.productId.toString()
            var url: java.net.URL? = null
            try {
                url = URL(share.replace("&quot;".toRegex(), ""))
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, detail.href!!.toString().replace("@amp;", "").replace(" ".toRegex(), "%20"))
            sendIntent.type = "text/plain"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                FIRSTCLICK = 0
                (mcontext as ViewProductSimple).startActivityForResult(Intent.createChooser(sendIntent, "Choose an Action:", null), INTENT_CHECK)
            } else {
                FIRSTCLICK = 0
                (mcontext as ViewProductSimple).startActivityForResult(sendIntent, INTENT_CHECK)
            }
        }
    }

    fun onClickAddToWishlist(view: View, detail: ProductDetail) {
        mcontext as ViewProductSimple
        if (!mcontext.isInternetAvailable) {
            mcontext.showDialog(mcontext)
        } else {
            val shared = mcontext.getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val isLoggedIn = shared.getBoolean("isLoggedIn", false)
            if (isLoggedIn) {
                val wishlistCallback = object : Callback<AddtoWishlist> {
                    override fun onResponse(call: Call<AddtoWishlist>, response: Response<AddtoWishlist>) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        if (response.body()!!.status != null && response?.body()!!.status == true) {
                            wishListStatus.getStatus(response.body()?.status!!)
                        } else {
                            wishListStatus.getStatus(false)
                        }
                        MakeToast().shortToast(mcontext, response.body()!!.message)
                    }

                    override fun onFailure(call: Call<AddtoWishlist>, t: Throwable) {

                    }
                }
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.addToWishlistCall(mcontext, detail.productId.toString(), RetrofitCustomCallback(wishlistCallback, mcontext))
            } else {
                SweetAlertBox().showWarningPopUp(mcontext, "", mcontext.resources.getString(R.string.wishlist_msg), detail.productId.toString())
            }
        }
    }

    fun onClickNext(view: View, detail: ProductDetail) {
        val intent = (mcontext as ViewProductSimple).intent
        intent.putExtra("idOfProduct", detail.productNext!![0].productId)
        intent.putExtra("nameOfProduct", detail.productNext!![0].name)
        mcontext.startActivity(intent)
        mcontext.overridePendingTransition(R.anim.reverse_fadein, R.anim.nothing)
        mcontext.finish()
    }

    fun onClickBuyNow(view: View, data: ProductDetail) {
        mcontext as ViewProductSimple
        if (!mcontext.isInternetAvailable) {
            mcontext.showDialog(mcontext)
        } else {
            val product_qty = mcontext.getbinding()!!.optionLayout!!.qty
            val quantity = Integer.parseInt(product_qty.text.toString())
            val qty = product_qty.text.toString()
            val chk = data.options!!.size
            optionsObj = JSONObject()
            val cartModelCallback = object : Callback<AddToCartModel> {
                override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                    if (response.body()?.error != 1) {
                        val total = response.body()!!.total!!
                        Log.d(TAG, "TotalITems-------> $total")
                        AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                        val icon = mcontext.itemCart!!.icon as LayerDrawable
                        Log.d(TAG, "CartCount-----> " + AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                        Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                        mcontext.startActivity(Intent(mcontext, Cart::class.java))
                    } else {
                        MakeToast.instance.shortToast(mcontext, response.body()?.message)
                    }
                    SweetAlertBox.dissmissSweetAlertBox()


                }

                override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
            }
            Log.d(TAG, "onClickBuyNow: option chk------>" + chk.toString())
            if (customList != null && customList!!.size != 0) {
//            && quantity <= Integer.parseInt(data.quantity) && Integer.parseInt(data.quantity) >= 1
                if (quantity >= 1) {
                    var canShowReqDialog = true
                    var isAllReqOptnFilled = true
                    try {
                        for (noOfViews in customList!!.indices) {
                            Log.d(TAG, "onClickBuyNow: --------->" + customList!![noOfViews].type!!)
                            if (customList!![noOfViews].type == "time" || customList!![noOfViews].type == "date") {
                                val et = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                                if (!et.text.toString().isEmpty() && et.text.toString() != "Select Date" && et.text.toString() != "Select Time") {
                                    if (customList!![noOfViews].type == "date") {
                                        optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                                    } else {
                                        optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                                    }
                                } else {
                                    if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    }
                                }
                            } else if (customList!![noOfViews].type == "datetime") {
                                val etDate = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                                val etTime = mcontext.findViewById<View>(customList!![noOfViews].associatedId) as EditText
                                val temp = JSONObject()
                                if (!etDate.text.toString().isEmpty() && !etTime.text.toString().isEmpty() && etDate.text.toString() != "Select Date" && etTime.text.toString() != "Select Time") {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, etDate.text.toString() + " " + etTime.text.toString())
                                } else {
                                    if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    } else {
                                        if (!etDate.text.toString().isEmpty() && etTime.text.toString().isEmpty() || etDate.text.toString().isEmpty() && !etTime.text.toString().isEmpty()) {
                                            isAllReqOptnFilled = false
                                            if (canShowReqDialog) {
                                                val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                                dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + R.string._is_not_complete_))
                                                dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                                dlgAlert.setCancelable(true)
                                                dlgAlert.create().show()
                                                canShowReqDialog = false
                                            }
                                        }
                                    }
                                }
                            } else if (customList!![noOfViews].type == "textarea" || customList!![noOfViews].type == "text") {
                                val et = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1 && et.text.toString().isEmpty()) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            } else if (customList!![noOfViews].type == "select") {
                                val spinner = mcontext.findViewById<View>(customList!![noOfViews].id) as Spinner
                                if (selectCustomMap.size != 0) {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                            "" + selectCustomMap[noOfViews])
                                } else {
                                    if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    }
                                }
                            } else if (customList!![noOfViews].type == "image") {
                                val spinner = mcontext.findViewById<View>(customList!![noOfViews].id) as Spinner
                                if (spinner.selectedItemPosition != 0) {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                            "" + productOptionValueId)
                                } else {
                                    if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    }
                                }
                            } else if (customList!![noOfViews].type == "file") {
                                if (fileCode != null) {
                                    if (!fileCode!!.isEmpty()) {
                                        optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                                "" + fileCode!!)
                                    }
                                }

                            } else if (customList!![noOfViews].type == "radio") {
                                if (radioSelectionMap.size != 0) {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                            "" + radioSelectionMap[noOfViews])
                                } else {
                                    if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    }
                                }
                            } else if (customList!![noOfViews].type == "checkbox" || customList!![noOfViews].type == "multiple") {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1 && checklist != null && checklist!!.size != 0) {
                                    val optionIdArray = JSONArray()
                                    if (checklist != null) {
                                        if (checklist!!.size != 0) {
                                            val keys = checklist!!.keys.toTypedArray()
                                            for (i in keys.indices) {
                                                optionIdArray.put(checklist!![keys[i]])
                                            }
                                        }
                                    }
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, optionIdArray)
                                } else {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if (isAllReqOptnFilled) {
                        Log.d(TAG, "onClickBuyNow: -------->" + optionsObj.toString())
                        SweetAlertBox().showProgressDialog(mcontext)
                        RetrofitCallback.addToCartCall(mcontext, data.productId.toString(), qty, optionsObj!!, RetrofitCustomCallback(cartModelCallback, mcontext))
                    }
                } else {
                    MakeToast().shortToast(mcontext, mcontext.getResources().getString(R.string.enter_valid_quantity))
                }
            } else if (quantity >= 1) {
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.addToCartWithoutOptionCall(mcontext, data.productId.toString(), qty, RetrofitCustomCallback(cartModelCallback, mcontext))
            } else {
                MakeToast().shortToast(mcontext, mcontext.getResources().getString(R.string.enter_valid_quantity))
            }
        }
    }

    fun onClickAdd(view: View, data: ProductDetail) {
        val prodout_qty = (mcontext as ViewProductSimple).getbinding()!!.optionLayout!!.qty
        var qty = Integer.parseInt(prodout_qty.text.toString())
        if (qty >= 1) {
            qty++
            prodout_qty.text = qty.toString()
        }
    }

    fun onClickSub(view: View, data: ProductDetail) {
        val prodout_qty = (mcontext as ViewProductSimple).getbinding()!!.optionLayout!!.qty
        var qty = Integer.parseInt(prodout_qty.text.toString())
        if (qty > 1) {
            qty--
            prodout_qty.text = qty.toString()
        }
    }

    fun onClickImage(data: ViewProductSimpleBannerAdapterModel) {
        val intent = Intent(mcontext, ViewPagerExampleActivity::class.java)
        intent.putExtra("dominant", data.dominantColor)
        intent.putExtra("imageUrl", data.popup)
        intent.putExtra("productName", data.productTitle)
        intent.putStringArrayListExtra("productImageArr", data.imageList as ArrayList<String>?)
        intent.putStringArrayListExtra("childList", data.childList as ArrayList<String>?)
        mcontext.startActivity(intent)
    }


    fun onClickAddToCart(view: View, data: ProductDetail) {
        val product_qty = (mcontext as ViewProductSimple).getbinding()!!.optionLayout!!.qty
        val quantity = Integer.parseInt(product_qty.text.toString())
        val qty = product_qty.text.toString()
        optionsObj = JSONObject()
        val cartModelCallback = object : Callback<AddToCartModel> {
            override fun onResponse(call: Call<AddToCartModel>, response: Response<AddToCartModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response.body()?.error != 1) {
                    MakeToast().shortToast(mcontext, response.body()!!.message)
                    val total = response.body()!!.total!!
                    AppSharedPreference.editSharedPreference(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS, total)
                    val icon = mcontext.itemCart!!.icon as LayerDrawable
                    Utils.setBadgeCount(mcontext, icon, AppSharedPreference.getCartItems(mcontext, Constant.CUSTOMER_SHARED_PREFERENCE_KEY_CART_ITEMS))
                } else {
                    MakeToast.instance.shortToast(mcontext, response.body()?.message)
                }
            }

            override fun onFailure(call: Call<AddToCartModel>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
                Log.d("Addtocart", "onFailure:-------> " + t.toString())
            }
        }
        if (customList != null && customList!!.size != 0) {
            if (quantity >= 1) {
                var canShowReqDialog = true
                var isAllReqOptnFilled = true
                try {
                    for (noOfViews in customList!!.indices) {
                        if (customList!![noOfViews].type == "checkbox" || customList!![noOfViews].type == "multiple") {
                            if (Integer.parseInt(customList!![noOfViews].isRequired) == 1 && checklist != null && checklist!!.size != 0) {
                                val optionIdArray = JSONArray()
                                if (checklist != null) {
                                    if (checklist!!.size != 0) {
                                        val keys = checklist!!.keys.toTypedArray()
                                        for (i in keys.indices) {
                                            optionIdArray.put(checklist!![keys[i]])
                                        }
                                    }
                                }
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, optionIdArray)
                            } else {
                                isAllReqOptnFilled = false
                                if (canShowReqDialog) {
                                    val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                    dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                    dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                    dlgAlert.setCancelable(true)
                                    dlgAlert.create().show()
                                    canShowReqDialog = false
                                }
                            }
                        } else if (customList!![noOfViews].type == "time" || customList!![noOfViews].type == "date") {
                            val et = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                            if (!et.text.toString().isEmpty() && et.text.toString() != "Select Date" && et.text.toString() != "Select Time") {
                                if (customList!![noOfViews].type == "date") {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                                } else {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                                }
                            } else {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            }
                        } else if (customList!![noOfViews].type == "datetime") {
                            val etDate = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                            val etTime = mcontext.findViewById<View>(customList!![noOfViews].associatedId) as EditText
                            val temp = JSONObject()
                            if (!etDate.text.toString().isEmpty() && !etTime.text.toString().isEmpty() && etDate.text.toString() != "Select Date" && etTime.text.toString() != "Select Time") {
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, etDate.text.toString() + " " + etTime.text.toString())
                            } else {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                } else {
                                    if (!etDate.text.toString().isEmpty() && etTime.text.toString().isEmpty() || etDate.text.toString().isEmpty() && !etTime.text.toString().isEmpty()) {
                                        isAllReqOptnFilled = false
                                        if (canShowReqDialog) {
                                            val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                            dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + R.string._is_not_complete_))
                                            dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                            dlgAlert.setCancelable(true)
                                            dlgAlert.create().show()
                                            canShowReqDialog = false
                                        }
                                    }
                                }
                            }
                        } else if (customList!![noOfViews].type == "textarea" || customList!![noOfViews].type == "text") {
                            val et = mcontext.findViewById<View>(customList!![noOfViews].id) as EditText
                            optionsObj!!.put("" + customList!![noOfViews].productOptionId!!, et.text.toString())
                            if (Integer.parseInt(customList!![noOfViews].isRequired) == 1 && et.text.toString().isEmpty()) {
                                isAllReqOptnFilled = false
                                if (canShowReqDialog) {
                                    val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                    dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                    dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                    dlgAlert.setCancelable(true)
                                    dlgAlert.create().show()
                                    canShowReqDialog = false
                                }
                            }
                        } else if (customList!![noOfViews].type == "select") {
                            val spinner = mcontext.findViewById<View>(customList!![noOfViews].id) as Spinner
                            if (selectCustomMap.size != 0) {
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                        "" + selectCustomMap[noOfViews])
                            } else {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            }
                        } else if (customList!![noOfViews].type == "image") {
                            val spinner = mcontext.findViewById<View>(customList!![noOfViews].id) as Spinner
                            if (spinner.selectedItemPosition != 0) {
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                        "" + productOptionValueId)
                            } else {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            }
                        } else if (customList!![noOfViews].type == "file") {
                            if (fileCode != null) {
                                if (!fileCode!!.isEmpty()) {
                                    optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                            "" + fileCode!!)
                                }
                            }
                        } else if (customList!![noOfViews].type == "radio") {
                            //                            RadioButton selectedRadioBtn = (RadioButton) ((ViewProductSimple) mContext).findViewById(radioGroup.getCheckedRadioButtonId());
                            //                            int idx = radioGroup.indexOfChild(selectedRadioBtn);
                            //                            //return negative if nothing is selected
                            if (radioSelectionMap.size != 0) {
                                optionsObj!!.put("" + customList!![noOfViews].productOptionId!!,
                                        "" + radioSelectionMap[noOfViews])
                            } else {
                                if (Integer.parseInt(customList!![noOfViews].isRequired) == 1) {
                                    isAllReqOptnFilled = false
                                    if (canShowReqDialog) {
                                        val dlgAlert = AlertDialog.Builder(mcontext, R.style.AlertDialogTheme)
                                        dlgAlert.setMessage(Html.fromHtml(mcontext.getResources().getString(R.string.field_) + "<b>" + customList!![noOfViews].title + "</b>" + mcontext.getResources().getString(R.string._is_not_complete_)))
                                        dlgAlert.setPositiveButton(mcontext.getResources().getString(android.R.string.ok), null)
                                        dlgAlert.setCancelable(true)
                                        dlgAlert.create().show()
                                        canShowReqDialog = false
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (isAllReqOptnFilled) {
                    val mcontext: BaseActivity = mcontext
                    if (mcontext.isInternetAvailable) {
                        SweetAlertBox().showProgressDialog(mcontext)
                        RetrofitCallback.addToCartCall(mcontext as ViewProductSimple, data.productId.toString(),
                                qty, optionsObj!!, RetrofitCustomCallback(cartModelCallback, mcontext))
                    } else {
                        AppDataBaseController.setAddCartData(mcontext as ViewProductSimple,
                                AddCartTable(0, data.productId.toString(), qty, optionsObj!!.toString()))
                        MakeToast.instance.shortToast(mcontext, mcontext.getString(R.string.add_item_offline))
                    }
                }
            } else {
                MakeToast().shortToast(mcontext, mcontext.getResources().getString(R.string.enter_valid_quantity))
            }
        } else {
            val mcontext: BaseActivity = mcontext
            if (mcontext.isInternetAvailable) {
                SweetAlertBox().showProgressDialog(mcontext)
                RetrofitCallback.addToCartWithoutOptionCall(
                        mcontext as ViewProductSimple,
                        data.productId.toString(),
                        qty,
                        RetrofitCustomCallback(cartModelCallback, mcontext))
            } else {
                val jsonObject: JSONObject = JSONObject()
                try {
                    jsonObject.put("product_id", data.productId.toString())
                    jsonObject.put("quantity", qty)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                AppDataBaseController.setAddCartData(mcontext as ViewProductSimple, AddCartTable(0, data.productId.toString(), qty, jsonObject.toString()))
                MakeToast.instance.shortToast(mcontext, mcontext.getString(R.string.add_item_offline))
                mOfflineDataBaseHandler.updateIntoOfflineDB("addtocart", jsonObject.toString(), "cartItem")
            }
        }
    }

    fun onClickFeature(view: View, data: ProductDetail) {
        val intent = Intent(mcontext, ProductDetails::class.java)
        intent.putExtra("detail", data)
        intent.putExtra("pos", 1)
        mcontext.startActivity(intent)
    }


    fun onClickReview(data: ProductDetail) {
        val intent = Intent(mcontext, Review_List::class.java)
        intent.putExtra("id", data.productId.toString())
        intent.putExtra("title", mcontext.getString(R.string.review) + " (" + data.getName() + ")")
        mcontext.startActivity(intent)
    }


    @SuppressLint("SetTextI18n")
    fun onClickDetail(view: View, data: ProductDetail) {
        val intent = Intent(mcontext, ProductDetails::class.java)
        intent.putExtra("detail", data)
        intent.putExtra("pos", 0)
        mcontext.startActivity(intent)
    }

    fun openReview(v: View, data: ProductDetail) {
        dialog = Dialog(mcontext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_review_dialog)
        dialog.show()
        try {
            val ratePoints = dialog.findViewById<View>(R.id.review_rating_bar) as RatingBar
            ratePoints.stepSize = 1f
            ratePoints.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> currentRating = rating.toString() + "" }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val cancel = dialog.findViewById<View>(R.id.btn_cancel) as Button
        dialog.setTitle(null)
        cancel.setOnClickListener { dialog.dismiss() }
        val apply = dialog.findViewById<View>(R.id.btn_apply) as Button
        apply.setOnClickListener { onReviewSubmit(dialog, data) }
    }

    fun onReviewSubmit(dialog: Dialog, data: ProductDetail) {
        writeProductReview = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                try {
                    if (response.body()!!.error == 0) {
                        MakeToast().shortToast(mcontext, response.body()!!.message)
                    } else {
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
        }
        if (AppSharedPreference.isLogin(this, Constant.CUSTOMER_SHARED_PREFERENCE_NAME)) {
            (dialog.findViewById<View>(R.id.user_name) as EditText).setText(AppSharedPreference.getCustomerName(this, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        } else {
            (dialog.findViewById<View>(R.id.user_name) as EditText).setText("")
        }
        val userNameET = dialog.findViewById<View>(R.id.user_name) as EditText
        val userName = userNameET.text.toString().trim { it <= ' ' }
        val cmtET = dialog.findViewById<View>(R.id.cmt) as EditText
        val cmt = cmtET.text.toString().trim { it <= ' ' }
        var isFormValidated = true

        if (userName.matches("".toRegex())) {
            userNameET.error = "Your Name" + " " + resources.getString(R.string.is_require_text)
            userNameET.requestFocus()
            isFormValidated = false
        } else if (cmt.matches("".toRegex())) {
            cmtET.error = "Your Comment" + " " + resources.getString(R.string.is_require_text)
            cmtET.requestFocus()
            isFormValidated = false
        } else if (cmt.length < 25) {
            cmtET.error = resources.getString(R.string.warning_enter_review_text)
            cmtET.requestFocus()
            isFormValidated = false
        }

        if (isFormValidated) {
            SweetAlertBox().showProgressDialog(mcontext)
            RetrofitCallback.writeReviewCall(ctx, userName, cmt,
                    currentRating.toString(), data.productId.toString(), RetrofitCustomCallback(writeProductReview, mcontext))
        }
    }

    override fun customData(list: ArrayList<CustomoptionData>) {
        customList = list
    }

    override fun getFileCode(fileCode: String) {
        this.fileCode = fileCode
    }

    override fun getChecklist(map: HashMap<*, *>) {
        checklist = map
    }

    override fun clearCheckList(id: String) {
        checklist!!.remove(id)
    }

    override fun getRadioProductId(key: Int, id: Int) {
        radioProductOptionValueId = id
        radioSelectOption.add(radioProductOptionValueId)
        radioSelectionMap[key] = id
    }

    override fun getSpinnerProductId(key: Int, id: Int) {
        productOptionValueId = id
        selectCustomMap[key] = id
    }

    fun setDrawerLayout(mDrawerLayout: DrawerLayout?) {
        this.mDrawerLayout = mDrawerLayout
    }

    companion object {
        var FIRSTCLICK = 1
        lateinit var radioSelectOption: ArrayList<Int>
        lateinit var radioSelectionMap: HashMap<Int, Int>
        lateinit var selectCustomMap: HashMap<Int, Int>
    }
}