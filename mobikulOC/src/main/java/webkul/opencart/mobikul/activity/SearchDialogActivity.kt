package webkul.opencart.mobikul.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.search_dialog_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.*
import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.CategoryActivity
//import webkul.opencart.mobikul.MLKIT.CameraSearchActivity
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.model.GetHomePage.Product
import webkul.opencart.mobikul.model.RecentSearchModel
import webkul.opencart.mobikul.model.SearchProductModel.SearchDatum
import webkul.opencart.mobikul.model.SearchProductModel.SearchProduct
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.roomdatabase.RecentSearchTable
import webkul.opencart.mobikul.roomdatabase.RecentViewedTable
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.ViewProductSimple
import webkul.opencart.mobikul.databinding.SearchDialogActivityBinding
import webkul.opencart.mobikul.databinding.ShowMlDialogBinding
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.utils.SweetAlertBox

class SearchDialogActivity : AppCompatActivity() {
    var searchDialogActivityBinding: SearchDialogActivityBinding? = null
    lateinit var autoCompleteAdapter: ArrayAdapter<String>
    private var homeDataModelCallback: Callback<HomeDataModel>? = null
    fun hideKeyword(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var v = activity.currentFocus
        if (v == null) {
            v = View(activity)
        }
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchDialogActivityBinding = DataBindingUtil.setContentView(this, R.layout.search_dialog_activity)
        searchDialogActivityBinding?.searchList?.layoutManager = LinearLayoutManager(this)
        searchDialogActivityBinding?.searchList?.isNestedScrollingEnabled = false
        searchDialogActivityBinding?.searchEdt?.threshold = 2
        searchDialogActivityBinding?.searchEdt?.setCompoundDrawablesWithIntrinsicBounds(null, null,
                AppCompatResources.getDrawable(this, R.drawable.ic_keyboard_voice_black_24dp), null)

        searchDialogActivityBinding?.back?.setOnClickListener {
            hideKeyword(this@SearchDialogActivity)
            finish()
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        searchDialogActivityBinding?.searchEdt?.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val search = editable.toString()
                if (search.length > 2) {
                    makeApiCall(search, this@SearchDialogActivity, searchDialogActivityBinding!!)
                }
            }
        })

        searchDialogActivityBinding?.searchEdt?.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                openSearchResult(this@SearchDialogActivity, searchDialogActivityBinding!!)
            }
            true
        }

        searchDialogActivityBinding?.searchEdt?.setOnTouchListener { _, event ->
            val right = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (searchDialogActivityBinding!!.searchEdt.right - searchDialogActivityBinding!!.searchEdt.compoundDrawables[right].bounds.width())) {
//                    openSearchResult(a, searchLayoutBinding)
                    var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
// Start the activity, the intent will be populated with the speech text
                    startActivityForResult(intent, 1001)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        mlkit.setOnClickListener {
            var popupWindow: PopupWindow? = null
            val showMlDialogBinding = ShowMlDialogBinding.inflate(LayoutInflater.from(this@SearchDialogActivity), null)
            popupWindow = PopupWindow(showMlDialogBinding.root, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow.showAsDropDown(searchDialogActivityBinding!!.mlkit)
        }
    }


    private fun makeHomeDataCall() {
        homeDataModelCallback = object : Callback<HomeDataModel> {
            override fun onResponse(call: Call<HomeDataModel>, response: Response<HomeDataModel>) {
                if (SweetAlertBox.sweetAlertDialog != null) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }
                if (response.body()?.error == 0) {
                    MainActivity.homeDataModel = response.body()
                    onResume()
                } else {
                    SweetAlertBox.instance.showErrorPopUp(this@SearchDialogActivity, resources.getString(R.string.Error), response?.body()?.message)
                }
            }

            override fun onFailure(call: Call<HomeDataModel>, t: Throwable) {
                t.printStackTrace()
            }
        }
        SweetAlertBox().showProgressDialog(this@SearchDialogActivity)
        RetrofitCallback.getHomePageCall(this@SearchDialogActivity, "",
                RetrofitCustomCallback(homeDataModelCallback, this@SearchDialogActivity),
                webkul.opencart.mobikul.helper.Utils.getScreenWidth(), "")
    }


    override fun onResume() {
        super.onResume()
        if (MainActivity.homeDataModel != null) {
            val dataholders = java.util.ArrayList<RightNavAdapterModel>()
            MainActivity.homeDataModel?.categories?.map {
                it.name?.let { it1 ->
                    RightNavAdapterModel(it1, it.path, it.thumb,
                            it.icon, it.dominantColor, it.childStatus)
                }?.let { it2 -> dataholders.add(it2) }
            }
            val list = ArrayList<webkul.opencart.mobikul.CategoryName>()
            list.add(webkul.opencart.mobikul.CategoryName(resources.getString(R.string.category_search)))
            list.add(webkul.opencart.mobikul.CategoryName(resources.getString(R.string.recent_search)))
//            list.add(webkul.opencart.mobikul.CategoryName(resources.getString(R.string.latest_prd)))
            list.add(webkul.opencart.mobikul.CategoryName(resources.getString(R.string.recent_view)))
            list.add(webkul.opencart.mobikul.CategoryName(resources.getString(R.string.browse_prd)))
            searchDialogActivityBinding?.searchList?.adapter = ChildAdapter(this, list, dataholders,
                    setRecentViewedData(this, AppDataBaseController.getRecentViewedProducts(this), searchDialogActivityBinding!!),
                    setRecentSearchData(this, AppDataBaseController.getRecentSearchList(this), searchDialogActivityBinding!!),
                    setBrandListing(this, searchDialogActivityBinding!!), setLatestProductListing())
        } else {
            makeHomeDataCall()
        }

    }

    private fun openSearchResult(a: Activity, searchDialogActivityBinding: SearchDialogActivityBinding) {
        val keyword = searchDialogActivityBinding.searchEdt.text.toString()
        if (keyword.isEmpty()) {
            Toast.makeText(a, "Empty String", Toast.LENGTH_SHORT).show()
            return
        }
        val recentSearchTable = RecentSearchTable()
        recentSearchTable.word = keyword
        AppDataBaseController.setRecentSearchWord(a, recentSearchTable)
        val intent = Intent(a, CategoryActivity::class.java)
        intent.putExtra("search", keyword)
        a.startActivity(intent)
    }

    private fun makeApiCall(search: String, mContext: Context, searchDialogActivityBinding: SearchDialogActivityBinding) {
        Log.d("SearchDialog", "---------->" + search + "-----ISEMOJI------->" + Validation.isEmoji(search))
        val searchProductCallback = object : Callback<SearchProduct> {
            override fun onResponse(call: Call<SearchProduct>, response: Response<SearchProduct>) {
                if (response.body()!!.searchData != null) {
                    if (response.body()!!.searchData!!.isNotEmpty()) {
                        setSearchProductData(mContext, response.body()!!.searchData, searchDialogActivityBinding, true, search)
                    }
                } else {
                    setSearchProductData(mContext, response.body()!!.searchData, searchDialogActivityBinding, false, search)
                }
            }

            override fun onFailure(call: Call<SearchProduct>, t: Throwable) {
            }
        }
        RetrofitCallback.searchProduct(mContext, search, RetrofitCustomCallback(searchProductCallback, mContext))
    }


    private fun setSearchProductData(mContext: Context, searchData: List<SearchDatum>?,
                                     searchDialogActivityBinding: SearchDialogActivityBinding, b: Boolean, keyWord: String) {
        var itemNameList = emptyList<String>()
        var itemIdList = emptyList<String>()
        if (b) {
            for (i in searchData!!.indices) {
                itemNameList += searchData[i].name!!
                itemIdList += searchData[i].productId!!
            }
            searchDialogActivityBinding.emptySearch.visibility = View.GONE
            autoCompleteAdapter = AutoCompleteCustomAdapter(mContext, R.layout.search_product, itemNameList as ArrayList<String>)
            searchDialogActivityBinding.searchEdt.setAdapter(autoCompleteAdapter)
            searchDialogActivityBinding.searchEdt.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(mContext, ViewProductSimple::class.java)
                intent.putExtra("idOfProduct", itemIdList[position])
                intent.putExtra("nameOfProduct", itemNameList[position])
                mContext.startActivity(intent)
            }
        } else {
        }

    }

    private fun setLatestProductListing(): ArrayList<Product> {
        val list = ArrayList<Product>()
        if (MainActivity.homeDataModel?.latestProducts?.products != null) {
            for (`object` in MainActivity.homeDataModel?.latestProducts?.products?.toTypedArray()!!) {
                list.add(`object`)
            }
        }
        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            if (data != null) {
                val results: List<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val spokenText: String = results.get(0)
                setData(spokenText, this)
            }
        }
    }

    fun setData(searchKey: String, activity: Activity) {
        if (searchKey.isEmpty()) {
            Toast.makeText(activity, "Empty String", Toast.LENGTH_SHORT).show()
            return
        }
        val recentSearchTable = RecentSearchTable()
        recentSearchTable.word = searchKey
        AppDataBaseController.setRecentSearchWord(activity, recentSearchTable)
        val intent = Intent(activity, CategoryActivity::class.java)
        intent.putExtra("search", searchKey)
        activity.startActivity(intent)
    }

    private fun setBrandListing(a: Activity, searchLayoutBinding: SearchDialogActivityBinding): ArrayList<CarousalAdapterModel> {
        val carsoual_size = MainActivity.homeDataModel!!.carousalList?.size
        if (carsoual_size == 0) {
            searchLayoutBinding.tvBrand.visibility = View.GONE
        }
        val carousalAdapterModels = java.util.ArrayList<CarousalAdapterModel>()
        if (MainActivity.homeDataModel!!.carousalList!!.size != 0) {
            searchLayoutBinding.tvBrand.visibility = View.GONE
            if (carsoual_size!! <= 4) {
                MainActivity.homeDataModel?.carousalList!!.map {
                    carousalAdapterModels.add(CarousalAdapterModel(it.title!!, it.image!!, it.link!!, it.dominantColor))
                }
            } else {
                for (i in 0..3) {
                    carousalAdapterModels.add(CarousalAdapterModel(
                            MainActivity.homeDataModel!!.carousalList!![i].title!!,
                            MainActivity.homeDataModel!!.carousalList!![i].image!!,
                            MainActivity.homeDataModel!!.carousalList!![i].link!!,
                            MainActivity.homeDataModel!!.carousalList!![i].dominantColor))
                }
            }
        }
        return carousalAdapterModels
    }


    private fun setRecentSearchData(context: Context, recentSearchData: List<RecentSearchTable>?,
                                    searchLayoutBinding: SearchDialogActivityBinding): ArrayList<RecentSearchTable> {
        val list = ArrayList<RecentSearchTable>()
        for (`object` in recentSearchData!!.toTypedArray()) {
            list.add(`object`)
        }
        val recentList = ArrayList<RecentSearchTable>()
        list.map {
            val recentSearchTable = RecentSearchTable()
            recentSearchTable.id = it.id
            recentSearchTable.word = it.word
            recentList.add(recentSearchTable)
        }
        return recentList
    }

    private fun setRecentViewedData(context: Context, recentSearchData: List<RecentViewedTable>?,
                                    searchLayoutBinding: SearchDialogActivityBinding): ArrayList<RecentSearchModel> {
        val list = ArrayList<RecentViewedTable>()
        for (`object` in recentSearchData!!.toTypedArray()) {
            list.add(`object`)
        }
        val recentList = ArrayList<RecentSearchModel>()
        list.map {
            recentList.add(RecentSearchModel(it.productId, it.prouductName, it.productImage,
                    it.productPrice, it.productSpecialPrice, it.productHasOption))
        }
        return recentList;
    }
}
