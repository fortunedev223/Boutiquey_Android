package webkul.opencart.mobikul

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.app.ActionBar.LayoutParams
import android.content.*
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.database.Cursor
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.view.Window
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap
import java.util.Locale

import cn.pedant.SweetAlert.SweetAlertDialog
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.HomePageCurrencyAdapter
import webkul.opencart.mobikul.adapter.HomePageLanguageAdapter
import webkul.opencart.mobikul.adapter.LeftNavAdapter
import webkul.opencart.mobikul.adapter.SimilarProductAdapter
import webkul.opencart.mobikul.adapterModel.CurrencyAdapterModel
import webkul.opencart.mobikul.adapterModel.LanguageAdapterModel
import webkul.opencart.mobikul.adapterModel.SimiliarProductsAdapterModel
import webkul.opencart.mobikul.adapterModel.ViewProductSimpleBannerAdapterModel
import webkul.opencart.mobikul.callback.WishListStatus
import webkul.opencart.mobikul.handlers.ViewProductSimpleHandler
import webkul.opencart.mobikul.helper.*
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.FileUpload.FileUpload
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.model.GetProduct.ProductDetail
import webkul.opencart.mobikul.model.GetProduct.RelatedProduct
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ActivityViewSimpleProductBinding
import webkul.opencart.mobikul.databinding.CustomOptionDateViewBinding
import webkul.opencart.mobikul.databinding.CustomOptionFileViewBinding
import webkul.opencart.mobikul.databinding.CustomOptionTimeViewBinding
import webkul.opencart.mobikul.databinding.ReviewLayoutBinding
import webkul.opencart.mobikul.databinding.ViewProductSimpleBannerBinding
import webkul.opencart.mobikul.offline.database.DataBaseHandler

/**
Webkul Software. *
@Mobikul
@OpencartMobikul
@author Webkul
@copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
@license https://store.webkul.com/license.html
 */
open class ViewProductSimple : BaseActivity(), WishListStatus {

    lateinit var customOptnList: ArrayList<CustomoptionData>
    var productName: TextView? = null
    var newPrice: TextView? = null
    var price: TextView? = null
    var model: TextView? = null
    private var productDetail: ProductDetail? = null
    var INTENTCHECK = 100
    var ratingBar: RatingBar? = null
    var productOptionValueId: Int = 0
    var radioProductOptionValueId: Int = 0
    internal var productImageArr: JSONArray? = null
    internal var checkBoxList = ArrayList<MultiOptionData>()
    internal lateinit var listAdapter: ProductDescExpListAdapter
    internal lateinit var listDataHeader: List<String>
    internal lateinit var listDataChild: HashMap<String, List<String>>
    internal lateinit var fileJSONArr: JSONArray
    internal var flag = false
    internal var productData: ProductData? = null
    internal lateinit var productPrice: TextView
    internal var inputByUser = JSONObject()
    var optionsObj = JSONObject()
    internal var checkedList = HashMap<String, String>()
    internal var minuteCurrent = 0
    internal var hourCurrent = 0
    internal var minuteCurrent2 = 0
    internal var hourCurrent2 = 0
    internal var isOkayClicked: Boolean = false
    internal lateinit var dateAndTime: Array<String>
    internal lateinit var Titles: Array<CharSequence>
    internal lateinit var listener: DetailOnPageChangeListener
    internal lateinit var imageUrls: Array<String?> // images contained in banner
    // private Context context=getApplicationContext();
    private var configSharedPref: SharedPreferences? = null
    private var spinnerIdList: ArrayList<Int>? = null
    private var extras: Bundle? = null
    private var toolbarViewProductSimple: Toolbar? = null
    private var pager: ViewPager? = null
    private lateinit var dotList: Array<ImageView?>
    private var dotImage: ImageView? = null
    private var layoutContainer: RelativeLayout? = null
    private var currentRating: String? = null
    private var uri: Uri? = null
    private var fileSelectedTV: TextView? = null
    private var fileCode: String? = null
    private var mOfflineDataBaseHandler: DataBaseHandler? = null
    private val TAG = "ViewProductSimple"
    private var viewSimpleProductBinding: ActivityViewSimpleProductBinding? = null
    private var productDetailCallback: Callback<ProductDetail>? = null
    private var handler: ViewProductSimpleHandler? = null
    private var title: TextView? = null
    private var description: TextView? = null
    private var feature: TextView? = null
    private var review: TextView? = null
    private var addToCart: TextView? = null
    private var drawerLayout: DrawerLayout? = null
    private var writeProductReview: Callback<BaseModel>? = null
    var message: String? = null
    var status: Int? = -1

    override fun onResume() {
        if (itemCart != null) {
            try {
                val customerDataShared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                val icon = itemCart!!.icon as LayerDrawable
                // Update LayerDrawable's BadgeDrawable
                Log.d("UpdateCartICon", "====>" + customerDataShared.getString("cartItems", "0"))
                Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"))
            } catch (e: Exception) {
                // TODO: handle exception
            }
        }
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        val id = item.itemId
        if (id == android.R.id.home) {
            configSharedPref = getSharedPreferences("configureView", Context.MODE_PRIVATE)
            if (!configSharedPref!!.getBoolean("isMainCreated", false)) {
                val i = Intent(this@ViewProductSimple, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
            } else {
                onBackPressed()
            }
            return true
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println(" CHECKING  ---- " + intent.hasExtra("isNotification"))
        if (intent.hasExtra("isNotification")) {
            val i = Intent(this@ViewProductSimple, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        } else {
            overridePendingTransition(R.anim.nothing, R.anim.fadeout)
            this.finish()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_bell).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extras = intent.extras
        if (extras != null) {
            try {
                productId = Integer.parseInt(extras!!.getString("idOfProduct"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        isOnline()
        mOfflineDataBaseHandler = DataBaseHandler(this)
        if (!isInternetAvailable) {
            handler = ViewProductSimpleHandler(this@ViewProductSimple, this@ViewProductSimple)
            val databaseCursor = mOfflineDataBaseHandler!!.selectFromOfflineDB("getProduct", productId.toString() + "")
            if (databaseCursor != null)
                Log.d(TAG, "Number of Records found: " + databaseCursor.count)
            if (databaseCursor != null && databaseCursor.count != 0) {
                databaseCursor.moveToFirst()
                val responseData = databaseCursor.getString(0)
                Log.d(TAG, "Data from Database Query: Method Name : " + databaseCursor.getString(1) + ", Data : " + databaseCursor.getString(0))
                databaseCursor.close()
                viewSimpleProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_simple_product)
                toolbarViewProductSimple = viewSimpleProductBinding!!.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
                setSupportActionBar(toolbarViewProductSimple)
                title = viewSimpleProductBinding!!.toolbar!!.findViewById<View>(R.id.title) as TextView
                title!!.text = Html.fromHtml(extras!!.getString("nameOfProduct"))
                val actionBar = supportActionBar
                actionBar?.setDisplayHomeAsUpEnabled(true)
                Titles = arrayOf(resources.getString(R.string.description), resources.getString(R.string.features), resources.getString(R.string.reviews))
                productPrice = viewSimpleProductBinding!!.productPrice
                listDataHeader = ArrayList()
                listDataChild = HashMap()
                @SuppressLint("SimpleDateFormat")
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val c = Calendar.getInstance()
                dateAndTime = df.format(c.time).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                try {
                    inputByUser.put("options", optionsObj)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                listAdapter = ProductDescExpListAdapter(this@ViewProductSimple, listDataHeader, listDataChild)
                fileJSONArr = JSONArray()
                layoutContainer = viewSimpleProductBinding!!.layoutContainer
                viewSimpleProductBinding!!.handler = handler
                productDetail = Gson().fromJson(responseData, ProductDetail::class.java)
                setProductData(productDetail)
            } else
                showDialog(this)
        } else {
            viewSimpleProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_simple_product)
            hideSoftKeyboard(viewSimpleProductBinding!!.root)
            handler = ViewProductSimpleHandler(this@ViewProductSimple, this@ViewProductSimple)
            viewSimpleProductBinding?.handler = handler
            toolbarViewProductSimple = viewSimpleProductBinding!!.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
            title = viewSimpleProductBinding!!.toolbar!!.findViewById<View>(R.id.title) as TextView
            title?.text = Html.fromHtml(extras!!.getString("nameOfProduct"))
            title?.typeface = null
            setSupportActionBar(toolbarViewProductSimple)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            addToCart = viewSimpleProductBinding?.addTocartButton
            val drop = AppCompatResources.getDrawable(this, R.drawable.right_arrow)
            description = viewSimpleProductBinding!!.descriptionTv
            feature = viewSimpleProductBinding!!.featureTv
            review = viewSimpleProductBinding!!.reviewTv
            description!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drop, null)
            feature!!.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drop, null)
            Titles = arrayOf(resources.getString(R.string.description), resources.getString(R.string.features), resources.getString(R.string.reviews))
            productPrice = viewSimpleProductBinding!!.productPrice
            listDataHeader = ArrayList()
            listDataChild = HashMap()
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val c = Calendar.getInstance()
            dateAndTime = df.format(c.time).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            try {
                inputByUser.put("options", optionsObj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            listAdapter = ProductDescExpListAdapter(this@ViewProductSimple, listDataHeader, listDataChild)
            fileJSONArr = JSONArray()
            layoutContainer = viewSimpleProductBinding?.layoutContainer!!
            getbinding()?.addYourReviewTxt?.paintFlags = getbinding()?.addYourReviewTxt?.paintFlags!! or Paint.UNDERLINE_TEXT_FLAG
            layoutContainer?.visibility = View.GONE
            productDetailCallback = object : Callback<ProductDetail> {
                override fun onResponse(call: Call<ProductDetail>, response: Response<ProductDetail>) {
                    if (response.body()?.error!! != 1) {
                        productDetail = response.body()
                        setProductData(productDetail)
                    } else {
                        val sweetAlertDialog = SweetAlertDialog(this@ViewProductSimple, SweetAlertDialog.WARNING_TYPE)
                        if (response.body()?.error == 1 && response.body()?.message != null) {
                            sweetAlertDialog.setTitleText(resources.getString(R.string.warning))
                                    .setTitleText(resources.getString(R.string.warning))
                                    .setContentText(response.body()?.message)
                                    .setConfirmText(resources.getString(R.string.dialog_ok))
                                    .setConfirmClickListener { sDialog ->
                                        sDialog.dismissWithAnimation()
                                        val intent = Intent(this@ViewProductSimple, MainActivity::class.java)
                                        finish()
                                        startActivity(intent)
                                    }
                                    .show()
                        } else {
                            sweetAlertDialog.setTitleText(resources.getString(R.string.warning))
                                    .setTitleText(resources.getString(R.string.warning))
                                    .setContentText(resources.getString(R.string.something_went_wrong))
                                    .setConfirmText(resources.getString(R.string.dialog_ok))
                                    .setConfirmClickListener { sDialog ->
                                        sDialog.dismissWithAnimation()
                                        val intent = Intent(this@ViewProductSimple, MainActivity::class.java)
                                        finish()
                                        startActivity(intent)
                                    }
                                    .show()
                        }
                        sweetAlertDialog.setCancelable(false)
                    }
                }

                override fun onFailure(call: Call<ProductDetail>, t: Throwable) {
                }
            }
            RetrofitCallback.getProductCall(this@ViewProductSimple, productId.toString(),
                    RetrofitCustomCallback(productDetailCallback, this@ViewProductSimple))
        }
        handler?.setDrawerLayout(drawerLayout)
        writeProductReview = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                try {
                    if (response.body()!!.error == 0) {
                        dialog.dismiss()
                        MakeToast().shortToast(this@ViewProductSimple, response.body()!!.message)
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
    }

    fun showReview(view: View) {
        viewSimpleProductBinding?.reviewLayout?.parent?.requestChildFocus(getbinding()?.reviewLayout, getbinding()?.reviewLayout)
        webkul.opencart.mobikul.helper.Utils.objectAnimator(getbinding()!!.reviewLayout)
    }

    private fun setProductData(productDetail: ProductDetail?) {
        AppDataBaseController.setRecentViewedProduct(this, productDetail)
        getbinding()?.viewProductScrollView?.visibility = View.VISIBLE
        getbinding()?.viewProductProgressBar?.visibility = View.GONE
        getbinding()?.layoutContainer?.visibility = View.VISIBLE
        viewSimpleProductBinding!!.data = productDetail
        if (Validation.checkSpecialPrice(productDetail!!.special)) {
            viewSimpleProductBinding!!.productPrice.textSize = 12f
            viewSimpleProductBinding!!.productPrice.paintFlags = viewSimpleProductBinding!!.productPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        if (productDetail.reviewGuest!!) {
            viewSimpleProductBinding?.reviewGuest?.visibility = View.VISIBLE
            viewSimpleProductBinding?.reviewLayout?.visibility = View.VISIBLE
        } else {
            viewSimpleProductBinding?.reviewGuest?.visibility = View.GONE
            viewSimpleProductBinding?.reviewLayout?.visibility = View.GONE
            viewSimpleProductBinding?.reviewContainer?.visibility = View.GONE
        }
        if (productDetail.minimum?.toInt()!! > 1) {
            viewSimpleProductBinding?.minimumContainer?.visibility = View.VISIBLE
            viewSimpleProductBinding?.minimumText?.text = getString(R.string.product_minimum) + " " + productDetail?.minimum
        }
        val wishlist = AppCompatResources.getDrawable(this, R.drawable.wishlishv3_product_page)
        viewSimpleProductBinding?.wishlistTv?.setCompoundDrawablesRelativeWithIntrinsicBounds(wishlist, null, null, null)
        productDetail.let {
            if (it.wishlistStatus != null) {
                if (productDetail.wishlistStatus!!) {
                    viewSimpleProductBinding?.wishlistTv?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(this,
                            R.drawable.wishlishv3_selected),
                            null, null, null)
                } else {
                    viewSimpleProductBinding?.wishlistTv?.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(this,
                            R.drawable.wishlishv3_product_page),
                            null, null, null)
                }
            }
        }
        val productOffline = GsonBuilder().create().toJson(productDetail)
        mOfflineDataBaseHandler?.updateIntoOfflineDB("getProduct", productOffline, productId.toString() + "")
        viewSimpleProductBinding?.viewProductSimpleDetailLayout?.visibility = View.VISIBLE
        layoutContainer?.visibility = View.VISIBLE
        pager = viewSimpleProductBinding?.bannerPager
        pager?.adapter = ImagePagerAdapterSimple()
        var layoutParams = LinearLayout.LayoutParams(
                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 1)
        pager?.layoutParams = layoutParams
        loadBannerImages(productDetail.images!!.size, productDetail)
        if (productDetail.relatedProducts?.size!! != 0) {
            try {
                setSimilarProducts(productDetail.relatedProducts,
                        findViewById<View>(R.id.related_Product_ll) as LinearLayout)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            viewSimpleProductBinding?.relatedProductTv?.visibility = View.GONE
        }

        if (productDetail.reviewData!!.reviews!!.size != 0) {
            var layoutBinding: ReviewLayoutBinding
            for (i in 0 until productDetail.reviewData!!.reviews!!.size) {
                layoutBinding = ReviewLayoutBinding.inflate(LayoutInflater.from(this@ViewProductSimple))
                layoutBinding.reviewMsg.text = productDetail.reviewData!!.reviews!![i].text
                layoutBinding.auther.text = productDetail.reviewData!!.reviews!![i].author
                layoutBinding.date.text = productDetail.reviewData!!.reviews!![i].dateAdded
                layoutBinding.ratingBar.rating = productDetail.reviewData!!.reviews!![i].rating!!.toFloat()
                getbinding()!!.reviewLayout.addView(layoutBinding.root)
            }
        } else {
            viewSimpleProductBinding?.reviewContainer?.visibility = View.GONE
        }

        if (productDetail.options!!.size == 0) {
            viewSimpleProductBinding?.requiredFieldTV?.visibility = View.GONE
            viewSimpleProductBinding?.customOptionLLOuter?.visibility = View.GONE
        } else if (productDetail.options!!.size != 0) {
            customOptnList = ArrayList()
            spinnerIdList = ArrayList()
            val viewProductLayout = viewSimpleProductBinding?.customOptionLL!!
            for (noOfCustomOpt in 0 until productDetail.options?.size!!) {
                customOptnList.add(CustomoptionData(productDetail.options!![noOfCustomOpt].name,
                        productDetail.options!![noOfCustomOpt].type,
                        productDetail.options!![noOfCustomOpt].required,
                        0.0, "", "", productDetail.options!![noOfCustomOpt].optionId,
                        productId,
                        0.0, productDetail.options!![noOfCustomOpt].productOptionId
                ))
                var newCustomOptionLabel = customOptnList[noOfCustomOpt].title
                val customOptionsName = TextView(this@ViewProductSimple)
                customOptionsName.textSize = 18f
                customOptionsName.setTextColor(Color.parseColor("#727272"))
                customOptionsName.setPadding(0, 15, 0, 15)
                //is affecting price if any custom option affect the price
                var isAffectingPrice: Boolean? = false
                if (Integer.parseInt(customOptnList[noOfCustomOpt].isRequired) == 1) {
                    newCustomOptionLabel += "<font color=\"#FF2107\">" + "*" + "</font></small></i>"
                }

                if (customOptnList[noOfCustomOpt].unformatedDefaultPrice != 0.0) {
                    isAffectingPrice = true
                }
                if (isAffectingPrice!!) {
                    var formattedDefaultPrice = customOptnList[noOfCustomOpt].formatedDefaultPrice
                    if (customOptnList[noOfCustomOpt].priceType != "fixed") {
                        var price = productData!!.getPrice()
                        val unformatedDefaultPrice = customOptnList[noOfCustomOpt].unformatedDefaultPrice
                        val pattern = productData!!.getPriceFormat().getPattern()
                        val precision = productData!!.getPriceFormat().getPrecision()
                        price = price!! * unformatedDefaultPrice!! / 100
                        val precisionFormat = "%." + precision + "f"
                        val formatedPrice = String.format(precisionFormat, price)
                        val newPattern = pattern.replace("%s".toRegex(), formatedPrice)
                        formattedDefaultPrice = newPattern
                        newCustomOptionLabel += " +$formattedDefaultPrice"
                    } else {
                        newCustomOptionLabel += "  +$formattedDefaultPrice"
                    }
                }
                customOptionsName.text = Html.fromHtml(newCustomOptionLabel)
                customOptionsName.setPadding(0, 10, 0, 10)
                viewProductLayout.addView(customOptionsName)
                /*************************************** field ******** AREA ******* text  */
                if (customOptnList[noOfCustomOpt].type == "text" || customOptnList[noOfCustomOpt].type == "textarea") {
                    val editTextView = EditText(this@ViewProductSimple)
                    editTextView.setPadding(10, 10, 10, 10)
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        editTextView.setBackgroundDrawable(resources.getDrawable(R.drawable.border, theme))
                    } else {
                        val drawable = AppCompatResources.getDrawable(this, R.drawable.border)
                        editTextView.setBackgroundDrawable(drawable)

                    }
                    if (customOptnList[noOfCustomOpt].type == "text")
                        editTextView.setSingleLine()
                    val id = View.generateViewId()
                    editTextView.id = id
                    customOptnList[noOfCustomOpt].id = id
                    viewProductLayout.addView(editTextView)
                }

                /* ************************************* FILE *************************************** */

                if (customOptnList[noOfCustomOpt].type == "file") {
                    val child = CustomOptionFileViewBinding.inflate(layoutInflater)
                    val idOfFileNameTV = View.generateViewId()
                    fileSelectedTV = child.fileSelectedTV
                    fileSelectedTV!!.id = idOfFileNameTV
                    val browseButton = child.browseButton
                    browseButton.tag = customOptnList[noOfCustomOpt].productOptionId + "/" + idOfFileNameTV
                    Log.d("id used", "" + idOfFileNameTV)
                    customOptnList[noOfCustomOpt].associatedId = idOfFileNameTV
                    browseButton.setOnClickListener {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(this@ViewProductSimple,
                                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this@ViewProductSimple, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                            } else {
                                intentToFileExplorer()
                            }
                        } else {
                            intentToFileExplorer()
                        }
                    }
                    viewProductLayout.addView(child.root)
                }

                /************************************************  DROPDOWN   */
                if (customOptnList[noOfCustomOpt].type == "select") {
                    val SpinnerOptions = ArrayList<String>()
                    SpinnerOptions.add(resources.getString(R.string._please_select_))
                    val spinnerList = ArrayList<MultiOptionData>()
                    spinnerList.add(MultiOptionData(-1, 0.0, "fixed"))
                    for (i in 0 until productDetail.options!![noOfCustomOpt].productOptionValue!!.size) {
                        var optionString: String? = null
                        if (!productDetail.options!![noOfCustomOpt].productOptionValue!![i].price!!.equals("false", ignoreCase = true)
                                && productDetail.options!![noOfCustomOpt].productOptionValue!![i].price?.length!! > 1) {
                            optionString = productDetail.options!![noOfCustomOpt].productOptionValue!![i].name + "(" + productDetail.options!![noOfCustomOpt].productOptionValue!![i].price + ")"
                        } else {
                            optionString = productDetail.options!![noOfCustomOpt].productOptionValue!![i].name
                        }

                        spinnerList.add(MultiOptionData(Integer.parseInt(productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId),
                                0.0,
                                ""))
                        SpinnerOptions.add(optionString!!)
                    }

                    var spinnerArrayAdapter: ArrayAdapter<String>? = null
                    spinnerArrayAdapter = ArrayAdapter(this@ViewProductSimple, android.R.layout.simple_spinner_dropdown_item, SpinnerOptions)
                    val spinner = Spinner(this@ViewProductSimple)
                    val view = View(this@ViewProductSimple)
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    val params = LinearLayout.LayoutParams(6, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        spinner.setBackgroundDrawable(resources.getDrawable(R.drawable.border))
                        view.setBackgroundDrawable(resources.getDrawable(R.drawable.red_button_background, theme))
                    } else {
                        val drawable = AppCompatResources.getDrawable(this, R.drawable.border)
                        val drawable1 = AppCompatResources.getDrawable(this, R.drawable.border)

                        view.setBackgroundDrawable(drawable)
                        spinner.setBackgroundDrawable(drawable1)

                    }
                    view.layoutParams = params
                    val spinnerParms = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 9f)
                    spinner.layoutParams = spinnerParms
                    val layout = LinearLayout(this@ViewProductSimple)
                    layout.orientation = LinearLayout.HORIZONTAL
                    layout.layoutParams = layoutParams
                    //                    layout.addView(view);
                    layout.addView(spinner)
                    val id = View.generateViewId()
                    spinner.id = id
                    customOptnList[noOfCustomOpt].id = id
                    customOptnList[noOfCustomOpt].multiOptionListList = spinnerList
                    spinnerIdList!!.add(id)
                    spinner.adapter = spinnerArrayAdapter
                    spinner.setSelection(0, false)
                    spinner.onItemSelectedListener = object : OnItemSelectedListener {
                        override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                            productOptionValueId = spinnerList[position].optionTypeId
                            handler!!.getSpinnerProductId(noOfCustomOpt, productOptionValueId)
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>) {
                            productOptionValueId = spinnerList[0].optionTypeId
                            handler!!.getSpinnerProductId(noOfCustomOpt, productOptionValueId)
                        }
                    }
                    viewProductLayout.addView(layout)
                }

                /************************************************ Radio-COLOR  */
                if (customOptnList[noOfCustomOpt].type == "radio" && productDetail.options!![noOfCustomOpt].name == "Color") {
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.topMargin = 3
                    params.bottomMargin = 3
                    val linearLayout = LinearLayout(this@ViewProductSimple)
                    linearLayout.orientation = LinearLayout.HORIZONTAL
                    linearLayout.layoutParams = params
                    val rg = RadioGroup(this@ViewProductSimple)//create the RadioGroup
                    val id = View.generateViewId()
                    rg.id = id
                    customOptnList[noOfCustomOpt].id = id
                    linearLayout.id = id
                    val radioList = ArrayList<MultiOptionData>()
                    radioList.add(MultiOptionData(-1, 0.0, "fixed"))
                    customOptnList[noOfCustomOpt].multiOptionListList = radioList
                    val circleImageViewHashMap = HashMap<Int, ImageView>()
                    val profileBackground = AppCompatResources.getDrawable(this, R.drawable.custom_option_rectangle)
                    val profileBackgroundSelect = AppCompatResources.getDrawable(this, R.drawable.select_image_option_color)
                    for (i in 0 until productDetail.options!![noOfCustomOpt].productOptionValue!!.size) {
                        val circleImageView = ImageView(this@ViewProductSimple)
                        circleImageView.id = View.generateViewId()
                        circleImageView.background = profileBackground
                        val imageParams = LinearLayout.LayoutParams(webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 8,
                                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 8)
                        imageParams.setMargins(10, 0, 10, 0)
                        circleImageView.setPadding(10, 10, 10, 10)
                        circleImageView.layoutParams = imageParams
                        val params2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        params2.setMargins(10, 10, 10, 0)
                        if (productDetail.options!![noOfCustomOpt].productOptionValue!![i].image!!.length > 1) {
                            Glide.with(applicationContext)
                                    .load(productDetail.options!![noOfCustomOpt].productOptionValue!![i].image)
                                    .override(70, 70)
                                    .into(circleImageView)
                        }
                        circleImageView.tag = productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId
                        linearLayout.addView(circleImageView, i)
                        circleImageViewHashMap[i] = circleImageView
                        circleImageView.setOnClickListener { view ->
                            for (i in 0 until circleImageViewHashMap.size) {
                                circleImageViewHashMap[i]!!.setBackground(profileBackground)
                            }
                            val circleImageView1 = view as ImageView
                            circleImageView1.background = profileBackgroundSelect
                            handler!!.getRadioProductId(noOfCustomOpt, Integer.parseInt(view.getTag().toString()))
                        }
                    }
                    viewProductLayout.addView(linearLayout, params)
                }
                ////////////////////////////////////-----------------RADIO----------////////////////////////////////////
                //                &&!productDetail.getOptions().get(noOfCustomOpt).getName().equals("Color")

                if (customOptnList[noOfCustomOpt].type == "radio") {
                    val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.topMargin = 3
                    params.bottomMargin = 3
                    val linearLayout = LinearLayout(this@ViewProductSimple)
                    linearLayout.orientation = LinearLayout.HORIZONTAL
                    linearLayout.layoutParams = params
                    val rg = RadioGroup(this@ViewProductSimple)//create the RadioGroup
                    val id = View.generateViewId()
                    rg.id = id
                    customOptnList[noOfCustomOpt].id = id
                    linearLayout.id = id
                    val radioList = ArrayList<MultiOptionData>()
                    radioList.add(MultiOptionData(-1, 0.0, "fixed"))
                    customOptnList[noOfCustomOpt].multiOptionListList = radioList
                    val circularTextViewHashSet = HashMap<Int, Button>()
                    for (i in 0 until productDetail.options!![noOfCustomOpt].productOptionValue!!.size) {
                        val rb = RadioButton(this@ViewProductSimple)
                        rg.addView(rb, params)
                        rb.id = View.generateViewId()
                        rb.setTextColor(Color.parseColor("#626262"))
                        rb.layoutParams = params
                        rb.tag = productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId
                        radioList.add(MultiOptionData(Integer.parseInt(productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId), 0.0, ""))
                        if (!productDetail.options!![noOfCustomOpt].productOptionValue!![i].price!!.equals("false", ignoreCase = true) &&
                                productDetail.options!![noOfCustomOpt].productOptionValue!![i].price?.length!! > 1) {
                            val optionString = (productDetail.options!![noOfCustomOpt].productOptionValue!![i].name
                                    + "(+" + productDetail.options!![noOfCustomOpt].productOptionValue!![i].price + ")")
                            rb.text = optionString
                        } else {
                            val optionString = productDetail.options!![noOfCustomOpt].productOptionValue!![i].name
                            rb.text = optionString
                        }
                    }

                    rg.setOnCheckedChangeListener { group, checkedId ->
                        val id = findViewById<View>(checkedId).tag as String
                        radioProductOptionValueId = Integer.parseInt(id)
                        handler!!.getRadioProductId(noOfCustomOpt, radioProductOptionValueId)
                        //
                    }
                    viewProductLayout.addView(rg, params)
                }
                /***********************************CheckBox  */

                if (customOptnList[noOfCustomOpt].type == "checkbox" || customOptnList[noOfCustomOpt].type == "multiple") {
                    for (i in 0 until productDetail.options!![noOfCustomOpt].productOptionValue!!.size) {
                        val chk = CheckBox(this@ViewProductSimple)
                        val id = View.generateViewId()
                        chk.id = id
                        chk.setTextColor(Color.parseColor("#626262"))
                        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        params.topMargin = 3
                        params.bottomMargin = 3
                        chk.tag = productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId
                        checkBoxList.add(MultiOptionData(id, Integer.parseInt(productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId),
                                Integer.parseInt(productDetail.options!![noOfCustomOpt].productOptionValue!![i].productOptionValueId),
                                0.0, productDetail.options!![noOfCustomOpt].productOptionValue!![i].price))

                        chk.setOnCheckedChangeListener { buttonView, isChecked ->
                            // TODO Auto-generated method stub

                            val id = buttonView.tag as String


                            if (isChecked) {
                                checkedList[id] = id
                                handler!!.getChecklist(checkedList)
                            } else {
                                checkedList.remove(id)
                                handler!!.clearCheckList(id)
                            }
                            Log.d("checked", checkedList.toString() + "")
                        }

                        if (!productDetail.options!![noOfCustomOpt].productOptionValue!![i].price!!.equals("false", ignoreCase = true)
                                && productDetail.options!![noOfCustomOpt].productOptionValue!![i].price?.length!! > 1) {
                            val optionString = productDetail.options!![noOfCustomOpt].productOptionValue!![i].name +
                                    "(+" + productDetail.options!![noOfCustomOpt].productOptionValue!![i].price + ")"
                            chk.text = optionString
                        } else {
                            val optionString = productDetail.options!![noOfCustomOpt].productOptionValue!![i].name
                            chk.text = optionString
                        }

                        viewProductLayout.addView(chk, params)
                    }
                }

                /******************************************date */
                if (customOptnList[noOfCustomOpt].type == "date") {
                    val myCalendar = Calendar.getInstance()
                    val child = CustomOptionDateViewBinding.inflate(layoutInflater)
                    val dateEditText1 = child.dateET
                    val id = View.generateViewId()
                    dateEditText1.id = id
                    dateEditText1.setText("  " + dateAndTime[0])
                    customOptnList[noOfCustomOpt].id = id
                    val addDate = child.addDateImg
                    val drawable1 = AppCompatResources.getDrawable(this, R.drawable.ic_plus)
                    addDate.setImageDrawable(drawable1)
                    dateEditText1.tag = customOptnList[noOfCustomOpt]
                    val resetBtn = child.resetBtn
                    resetBtn.id = View.generateViewId()
                    resetBtn.tag = id
                    resetBtn.setOnClickListener { v ->
                        val resetDate = findViewById<View>(v.tag as Int) as EditText
                        resetDate.setText("Select Date")
                    }

                    val date1 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val myFormat = "yyyy-MM-dd"
                        val sdf = SimpleDateFormat(myFormat, Locale.US)
                        if (isOkayClicked) {
                            dateEditText1.setText(sdf.format(myCalendar.time))
                        }
                        isOkayClicked = false
                    }

                    addDate.setOnClickListener {
                        val mDate = DatePickerDialog(this@ViewProductSimple,
                                /*R.style.AlertDialogTheme,*/
                                null, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH))
                        mDate.setCancelable(true)
                        mDate.setCanceledOnTouchOutside(true)
                        mDate.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                isOkayClicked = true
                                val datePicker = mDate
                                        .datePicker
                                date1.onDateSet(datePicker,
                                        datePicker.year,
                                        datePicker.month,
                                        datePicker.dayOfMonth)
                            }
                        }

                        mDate.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                dialog.cancel()
                                isOkayClicked = false
                            }
                        }
                        mDate.show()
                    }
                    viewProductLayout.addView(child.root)
                }

//						/***********************************dateTime**************************************************/

                if (customOptnList[noOfCustomOpt].type == "datetime") {
                    val density = resources.displayMetrics.density
                    val px = 120 * density
                    val dateLayput = LinearLayout(this@ViewProductSimple)
                    dateLayput.orientation = LinearLayout.HORIZONTAL
                    val LLParams = LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT)
                    dateLayput.layoutParams = LLParams
                    val myCalendar = Calendar.getInstance()
                    val dateEditText1 = EditText(this@ViewProductSimple)
                    dateEditText1.setSingleLine()
                    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    params.setMargins(0, 0, 0, 16)
                    dateEditText1.layoutParams = params
                    dateEditText1.inputType = InputType.TYPE_NULL
                    dateEditText1.isFocusable = false
                    dateEditText1.textAlignment = TEXT_ALIGNMENT_CENTER
                    dateEditText1.setPadding(12, 12, 12, 12)
                    dateEditText1.setText(dateAndTime[0])
                    dateLayput.addView(dateEditText1)
                    val id = View.generateViewId()
                    val id2 = View.generateViewId()
                    dateEditText1.id = id
                    customOptnList[noOfCustomOpt].id = id
                    customOptnList[noOfCustomOpt].associatedId = id2
                    viewProductLayout.addView(dateLayput)
                    val child = CustomOptionTimeViewBinding.inflate(layoutInflater)
                    val timeLayput = LinearLayout(this@ViewProductSimple)
                    timeLayput.orientation = LinearLayout.HORIZONTAL
                    child.addDateImg.visibility = View.GONE
                    timeLayput.layoutParams = LLParams
                    val timeEditText1 = child.timeET
                    timeEditText1.id = id2
//                    timeEditText1.background = ContextCompat.getDrawable(this, R.drawable.border)
                    dateEditText1.background = ContextCompat.getDrawable(this, R.drawable.border)
                    timeEditText1.setText(dateAndTime[1])
                    val resetDateTimeBtn = child.resetBtn
                    resetDateTimeBtn.id = View.generateViewId()
                    resetDateTimeBtn.setText(R.string.reset)
                    val dateAndTimeIds = id.toString() + "/" + id2
                    resetDateTimeBtn.tag = dateAndTimeIds
                    val mcurrentTime = Calendar.getInstance()
                    hourCurrent2 = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    minuteCurrent2 = mcurrentTime.get(Calendar.MINUTE)
                    val date1 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val myFormat = "yyyy-MM-dd"
                        val sdf = SimpleDateFormat(myFormat, Locale.US)
                        if (isOkayClicked) {
                            dateEditText1.setText(sdf.format(myCalendar.time))
                        }
                        isOkayClicked = false
                        if (timeEditText1.text.toString() == "") {
                            val hour = hourCurrent2
                            val minute = minuteCurrent2
                            val mTimePicker: TimePickerDialog
                            val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                                if (isOkayClicked)
                                    timeEditText1.setText(selectedHour.toString() + ":" + selectedMinute)
                                isOkayClicked = false
                                hourCurrent2 = selectedHour
                                minuteCurrent2 = selectedMinute
                            }
                            mTimePicker = TimePickerDialog(this@ViewProductSimple,
                                    /*R.style.AlertDialogTheme,*/
                                    timeListener, hour, minute, false)
                            mTimePicker.setTitle(R.string.select_time)
                            mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    isOkayClicked = true
                                    mTimePicker.onClick(dialog, which)
                                }
                            }

                            mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                                if (which == DialogInterface.BUTTON_NEGATIVE) {
                                    isOkayClicked = false
                                    dialog.cancel()
                                }
                            }
                            mTimePicker.show()
                        }
                    }
                    dateEditText1.setOnClickListener {
                        val mDate = DatePickerDialog(this@ViewProductSimple,
                                /*R.style.AlertDialogTheme,*/
                                null,
                                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        mDate.setCanceledOnTouchOutside(true)
                        mDate.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                isOkayClicked = true
                                val datePicker = mDate
                                        .datePicker
                                date1.onDateSet(datePicker,
                                        datePicker.year,
                                        datePicker.month,
                                        datePicker.dayOfMonth)
                            }
                        }

                        mDate.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                dialog.cancel()
                                isOkayClicked = false
                            }
                        }
                        mDate.show()
                    }

                    resetDateTimeBtn.setOnClickListener { v ->
                        val ids = (v.tag as String).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val resetDate = findViewById<View>(Integer.parseInt(ids[0])) as EditText
                        resetDate.setPadding(12, 12, 12, 12)
                        resetDate.setText("Select Date")
                        resetDate.error = null
                        val resetTime = findViewById<View>(Integer.parseInt(ids[1])) as EditText
                        resetTime.setText("Select Time")
                        resetTime.error = null
                        val mcurrentTime = Calendar.getInstance()
                        hourCurrent2 = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                        minuteCurrent2 = mcurrentTime.get(Calendar.MINUTE)
                        //										updatePrice();
                    }

                    timeEditText1.setOnClickListener {
                        val mTimePicker: TimePickerDialog
                        val hour = hourCurrent2
                        val minute = minuteCurrent2

                        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                            if (isOkayClicked) {
                                timeEditText1.setText(selectedHour.toString() + ":" + selectedMinute)
                                isOkayClicked = false
                                hourCurrent2 = selectedHour
                                minuteCurrent2 = selectedMinute

                                if (dateEditText1.text.toString() == "") {
                                    val mDate = DatePickerDialog(this@ViewProductSimple, R.style.AlertDialogTheme, null, myCalendar.get(Calendar.YEAR),
                                            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                                    mDate.setCanceledOnTouchOutside(true)
                                    mDate.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                                        if (which == DialogInterface.BUTTON_POSITIVE) {
                                            isOkayClicked = true
                                            val datePicker = mDate
                                                    .datePicker
                                            date1.onDateSet(datePicker,
                                                    datePicker.year,
                                                    datePicker.month,
                                                    datePicker.dayOfMonth)
                                        }
                                    }

                                    mDate.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                                            dialog.cancel()
                                            isOkayClicked = false
                                        }
                                    }
                                    mDate.show()
                                }
                            }
                        }

                        mTimePicker = TimePickerDialog(this@ViewProductSimple, R.style.AlertDialogTheme, timeListener, hour, minute, false) //true: 24 hour time
                        mTimePicker.setTitle(R.string.select_time)
                        mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                isOkayClicked = true
                                //							            			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                                mTimePicker.onClick(dialog, which)
                            }
                        }
                        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                isOkayClicked = false
                                dialog.cancel()
                            }
                        }
                        mTimePicker.show()
                    }

                    viewProductLayout.addView(timeLayput)
                    viewProductLayout.addView(child.root)
                }

                //						/**********************************time***************************************************/

                if (customOptnList[noOfCustomOpt].type == "time") {
                    val child = CustomOptionTimeViewBinding.inflate(layoutInflater)
                    val timeEditText1 = child.timeET
                    timeEditText1.setText("  " + dateAndTime[1])
                    val id = View.generateViewId()
                    timeEditText1.id = id
                    customOptnList[noOfCustomOpt].id = id
                    val time = child.addDateImg
                    val drawable1 = AppCompatResources.getDrawable(this, R.drawable.ic_plus)
                    time.setImageDrawable(drawable1)
                    val resetBtn = child.resetBtn
                    resetBtn.tag = id
                    resetBtn.setOnClickListener { v ->
                        val resetDate = findViewById<View>(v.tag as Int) as EditText
                        resetDate.setText("Select Time")
                        val mcurrentTime = Calendar.getInstance()
                        hourCurrent = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                        minuteCurrent = mcurrentTime.get(Calendar.MINUTE)
                    }
                    val mcurrentTime = Calendar.getInstance()
                    hourCurrent = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    minuteCurrent = mcurrentTime.get(Calendar.MINUTE)
                    time.setOnClickListener {
                        val mTimePicker: TimePickerDialog

                        val hour = hourCurrent
                        val minute = minuteCurrent

                        val timeListener = TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                            hourCurrent = selectedHour
                            minuteCurrent = selectedMinute
                            if (isOkayClicked)
                                timeEditText1.setText(selectedHour.toString() + ":" + selectedMinute)
                            isOkayClicked = false
                        }


                        mTimePicker = TimePickerDialog(this@ViewProductSimple,
                                /*R.style.AlertDialogTheme,*/
                                timeListener, hour, minute, false)//true: 24 hour time
                        mTimePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                isOkayClicked = true
                                mTimePicker.onClick(dialog, which)
                            }
                        }
                        mTimePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                isOkayClicked = false
                                dialog.cancel()
                            }
                        }
                        mTimePicker.show()
                    }
                    viewProductLayout.addView(child.root)
                }

            }
            handler!!.customData(customOptnList)
        }
    }

    fun changeProductsLargeImage(v: View) {
        val largeImageURL: String
        val view = v.parent.parent as View
        try {
            largeImageURL = productImageArr!!.getJSONObject(view.tag as Int).getString("popup")
            val productImageView = viewSimpleProductBinding!!.productImageView
            Picasso.with(this@ViewProductSimple).load(largeImageURL).into(productImageView)
            Log.d("productImageArr", productImageArr!!.toString())
            productImageView.setOnClickListener {
                val intent = Intent(baseContext, ViewPagerExampleActivity::class.java)
                try {
                    intent.putExtra("imageUrl", productImageArr!!.getJSONObject(view.tag as Int).getString("popup"))
                    intent.putExtra("productName", mainObject!!.getString("name"))
                    intent.putExtra("productImageArr", productImageArr!!.toString())
                    intent.putExtra("imageSelection", view.tag as Int)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                startActivity(intent)
            }

        } catch (e: JSONException) {
            Log.d("on click image button", e.message)
            e.printStackTrace()
        }
    }

    fun getbinding(): ActivityViewSimpleProductBinding? {
        return viewSimpleProductBinding
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intentToFileExplorer()
            }
            else -> {
            }
        }
    }

    fun intentToFileExplorer() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "file/*"
        try {
            val uri = Uri.parse(Environment.getExternalStorageDirectory().path)
            intent.setDataAndType(uri, "*/*")
            startActivityForResult(Intent.createChooser(intent, "Select File"), 2)
        } catch (e: ActivityNotFoundException) {
            //Your own file explorer if any
        }

    }

    private fun setSimilarProducts(list: List<RelatedProduct>?, parentView: LinearLayout) {
        if (list!!.size != 0) {
            val recyclerView = RecyclerView(this@ViewProductSimple)
            var productAdapter: SimilarProductAdapter
            val dataHolders = ArrayList<SimiliarProductsAdapterModel>()
            for (i in list.indices) {
                dataHolders.add(SimiliarProductsAdapterModel(
                        Html.fromHtml(list[i].name).toString(),
                        list[i].productId,
                        list[i].price!!,
                        list[i].thumb,
                        list[i].stock,
                        list[i].special,
                        list[i].formattedSpecial,
                        list[i].hasOption!!,
                        list[i].wishlistStatus!!,
                        list[i].dominantColor))
                productAdapter = SimilarProductAdapter(this@ViewProductSimple, dataHolders)
                val manager = LinearLayoutManager(this@ViewProductSimple)
                manager.orientation = LinearLayoutManager.HORIZONTAL
                recyclerView.layoutManager = manager
                recyclerView.adapter = productAdapter
                recyclerView.hasFixedSize()
                if (parentView.parent != null) {
                    parentView.removeAllViews()
                    parentView.addView(recyclerView)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_GET_SINGLE_FILE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri_data = data!!.data
                    val path = getPath(this, uri_data)
                    if (path != null) {
                        val f = File(path)
                        uri = Uri.fromFile(f)
                        fileSelectedTV!!.text = f.name
                        SweetAlertBox().showProgressDialog(this@ViewProductSimple)
                        val reqfile = RequestBody.create(MediaType.parse(getMimeType(uri!!.toString())), f)
                        val body = MultipartBody.Part.createFormData("file", f.name, reqfile)
                        val name = RequestBody.create(MediaType.parse(getMimeType(uri!!.toString())), "Upload File Description")
                        RetrofitCallback.getMultipartFile(this@ViewProductSimple, name, body, object : Callback<FileUpload> {
                            override fun onResponse(call: Call<FileUpload>, response: Response<FileUpload>) {
                                SweetAlertBox.dissmissSweetAlertBox()
                                if (response.body()!!.error == null) {
                                    Log.d("FileUpload", "onResponse: :---->" + response.body()!!.code!!)
                                    fileCode = response.body()!!.code
                                    handler!!.getFileCode(fileCode!!)
                                }
                            }

                            override fun onFailure(call: Call<FileUpload>, t: Throwable) {
                                SweetAlertBox.dissmissSweetAlertBox()
                            }
                        })

                    }
                }
            }
            else -> {
            }
        }
        if (requestCode == INTENTCHECK) {
            ViewProductSimpleHandler.FIRSTCLICK = 1
        }
    }

    public override fun onStop() {
        super.onStop()
        if (SweetAlertBox.sweetAlertDialog != null) {
            SweetAlertBox.dissmissSweetAlertBox()
        }
    }


    private fun loadBannerImages(size: Int, productDetail: ProductDetail) {
        try {
            if (size != 0) {
                imageUrls = arrayOfNulls(size)
                for (noOfBannerUrls in 0 until size) {
                    imageUrls[noOfBannerUrls] = productDetail.images!![noOfBannerUrls].popup
                }
                /* lOading images from url to bannner and set image Width and Height by LayoutParam*/
                pager = viewSimpleProductBinding!!.bannerPager
                pager?.setPageTransformer(true, DepthPageTransformer())
                pager?.adapter = ImagePagerAdapterSimple()
                val layoutParams = LinearLayout.LayoutParams(
                        webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                        webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 1)
                pager?.layoutParams = layoutParams
                listener = DetailOnPageChangeListener()
                pager?.setOnPageChangeListener(listener)
                val group = viewSimpleProductBinding?.dotGroup
                dotList = arrayOfNulls(imageUrls.size)
                if (imageUrls.size > 1) {
                    for (i in imageUrls.indices) {
                        dotImage = ImageView(this@ViewProductSimple)
                        dotList[i] = dotImage
                        if (i == 0)
                            dotList[i]!!.setBackgroundResource(R.drawable.selected_dot_icon)
                        else
                            dotList[i]!!.setBackgroundResource(R.drawable.unselected_dot_icon)
                        val params = LinearLayout.LayoutParams(webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 50,
                                webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 50)
                        params.setMargins(10, 0, 10, 0)
                        group?.addView(dotImage, params)
                    }
                }

            } else
                viewSimpleProductBinding?.pagerLayout?.visibility = View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun openReview(v: View) {
        dialog = Dialog(this@ViewProductSimple)
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
        if (AppSharedPreference.isLogin(this, Constant.CUSTOMER_SHARED_PREFERENCE_NAME)) {
            (dialog.findViewById<View>(R.id.user_name) as EditText).setText(AppSharedPreference.getCustomerName(this, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        } else {
            (dialog.findViewById<View>(R.id.user_name) as EditText).setText("")
        }
        val rateGdpr = dialog.findViewById<View>(R.id.checkbox_gdpr) as CheckBox
        val shared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        if (AppSharedPreference.getGdprStatus(this).equals("1") && productDetail?.gdprStatus == 1) {
            val gdprValue = resources.getString(R.string.gdpr_check_value)
            (dialog.findViewById<View>(R.id.read_condition) as TextView).setOnClickListener {
                val mDialog = Dialog(this@ViewProductSimple)
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog.setContentView(R.layout.terms_and_conditions_text)
                val webView = mDialog.findViewById<View>(R.id.webView) as WebView
                webView.settings.displayZoomControls = true
                mDialog.findViewById<View>(R.id.container).layoutParams = RelativeLayout.LayoutParams(
                        webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                        webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight())
                mDialog.findViewById<View>(R.id.close).setOnClickListener { mDialog.dismiss() }
                try {
                    webView.loadData(productDetail!!.gdprContent, "text/html; charset=UTF-8", null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                mDialog.findViewById<View>(R.id.button3).setOnClickListener { mDialog.dismiss() }
                mDialog.show()
            }
            rateGdpr.visibility = View.VISIBLE
        } else {
            rateGdpr.visibility = View.GONE
        }
        val cancel = dialog.findViewById<View>(R.id.btn_cancel) as Button
        dialog.setTitle(null)
        cancel.setOnClickListener { dialog.dismiss() }
        val apply = dialog.findViewById<View>(R.id.btn_apply) as Button
        apply.setOnClickListener { onReviewSubmit(dialog) }
    }

    fun onReviewSubmit(dialog: Dialog) {
        val userNameET = dialog.findViewById<View>(R.id.user_name) as EditText
        val userName = userNameET.text.toString().trim { it <= ' ' }
        val cmtET = dialog.findViewById<View>(R.id.cmt) as EditText
        val checkbox = dialog.findViewById<View>(R.id.checkbox_gdpr) as CheckBox
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
        } else if (currentRating?.toString() == null) {
            webkul.opencart.mobikul.helper.Utils.objectAnimator((dialog.findViewById<View>(R.id.review_rating_bar)))
            isFormValidated = false
        } else if (checkbox.visibility == View.VISIBLE && !checkbox.isChecked) {
            webkul.opencart.mobikul.helper.Utils.objectAnimator((dialog.findViewById<View>(R.id.checkbox_gdpr) as CheckBox))
            isFormValidated = false
        }

        if (isFormValidated) {
            Log.d("RatingProductPage", "=======>" + currentRating.toString())
            SweetAlertBox().showProgressDialog(this@ViewProductSimple)
            RetrofitCallback.writeReviewCall(ctx, userName, cmt, currentRating.toString(),
                    productId.toString(), RetrofitCustomCallback(writeProductReview, this@ViewProductSimple))
        }
    }

    fun writeProductReviewResponse(backresult: String) {
        try {
            SweetAlertBox.dissmissSweetAlertBox()
            responseObject = JSONObject(backresult)
            if (responseObject.has("message")) {
                Toast.makeText(applicationContext, responseObject.getString("message") + "", Toast.LENGTH_LONG)
                        .show()
                dialog.dismiss()
            } else
                Toast.makeText(applicationContext, responseObject.getString("error") + "", Toast.LENGTH_LONG)
                        .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun addSoldByLayout() {
        // do nothing in MObikul
    }

    open fun addSellerInfo() {
        // do nothing in MObikul
    }

    fun setCartbadge() {
        try {
            if (mainObject!!.has("cartCount")) {
                Log.d("DEBUG", mainObject!!.getString("cartCount"))
                val editor = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit()
                editor.putString("cartItems", mainObject!!.getString("cartCount"))
                editor.apply()
                if (itemCart != null) {
                    val customerDataShared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
                    val icon = itemCart!!.icon as LayerDrawable
                    Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"))
                }
            } else
                Log.d("DEBUG", "noValue for cartCount")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private inner class ImagePagerAdapterSimple internal constructor() : PagerAdapter() {

        private val inflater: LayoutInflater

        init {
            inflater = layoutInflater
        }

        override fun destroyItem(container: View, position: Int, `object`: Any) {
            (container as ViewPager).removeView(`object` as View)
        }

        override fun finishUpdate(container: View) {}

        override fun getCount(): Int {
            return if (productDetail!!.images != null) productDetail!!.images!!.size else 0
        }

        override fun instantiateItem(view: View, position: Int): Any {
            val imageLayout = DataBindingUtil.inflate<ViewProductSimpleBannerBinding>(inflater, R.layout.view_product_simple_banner, view as ViewGroup, false)
            val imageView = imageLayout.image
            val params = ConstraintLayout.LayoutParams(
                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 1,
                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 1)
            imageView.layoutParams = params
            imageLayout.handler = ViewProductSimpleHandler(
                    this@ViewProductSimple,
                    this@ViewProductSimple)
            val list = ArrayList<String>()
            val childlist = ArrayList<String>()
            for (i in 0 until productDetail!!.images!!.size) {
                list.add(productDetail!!.images!![i].popup!!)
                childlist.add(productDetail!!.images!![i].thumb!!)
            }
            imageLayout.data = ViewProductSimpleBannerAdapterModel(
                    productDetail?.images!![position].popup,
                    productDetail?.images!![position].thumb,
                    productDetail?.getName(), list, childlist,
                    productDetail?.dominantColor)
            (view as ViewPager).addView(imageLayout.root, 0)
            return imageLayout.root
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

        override fun saveState(): Parcelable? {
            return null
        }

        override fun startUpdate(container: View) {}
    }

    inner class DetailOnPageChangeListener : ViewPager.OnPageChangeListener {
        var currentPage: Int = 0
            private set

        override fun onPageSelected(position: Int) {
            currentPage = position
            for (i in imageUrls.indices) {
                if (i == position)
                    dotList!![i]!!.setBackgroundResource(R.drawable.selected_dot_icon)
                else
                    dotList!![i]!!.setBackgroundResource(R.drawable.unselected_dot_icon)
            }
        }

        override fun onPageScrollStateChanged(arg0: Int) {}

        override fun onPageScrolled(arg0: Int, Offset: Float, positionOffsetPixels: Int) {
            if (Offset > 0.5f) {
            }
        }
    }

    companion object {
        /* objects related to file picker */
        private val REQUEST_GET_SINGLE_FILE = 2
        private val REQUEST_GET_DIR = 3
        var progress: ProgressDialog? = null
        var productId: Int = 0
        lateinit var dialog: Dialog
        var mainObject: JSONObject? = null

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun getPath(context: Context, uri: Uri?): String? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }// MediaProvider
                // DownloadsProvider
            } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)
            return null
        }

        fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                if (cursor != null)
                    cursor.close()
            }
            return null
        }

        fun isExternalStorageDocument(uri: Uri?): Boolean {
            return "com.android.externalstorage.documents" == uri!!.authority
        }

        fun isDownloadsDocument(uri: Uri?): Boolean {
            return "com.android.providers.downloads.documents" == uri!!.authority
        }


        fun isMediaDocument(uri: Uri?): Boolean {
            return "com.android.providers.media.documents" == uri!!.authority
        }

        fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }

        fun getMimeType(url: String): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }
    }

    override fun getStatus(status: Boolean) {
        if (status) {
            val wishlist = AppCompatResources.getDrawable(this, R.drawable.wishlishv3_selected)
            viewSimpleProductBinding!!.wishlistTv.setCompoundDrawablesRelativeWithIntrinsicBounds(wishlist, null, null, null)
        } else {
            val wishlist = AppCompatResources.getDrawable(this, R.drawable.wishlishv3_product_page)
            viewSimpleProductBinding!!.wishlistTv.setCompoundDrawablesRelativeWithIntrinsicBounds(wishlist, null, null, null)
        }
    }

}
