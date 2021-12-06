package webkul.opencart.mobikul.activity

import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.ViewMoreAdapter
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.handlers.CategoryActivityHandler
import webkul.opencart.mobikul.model.ProductCategory.Sort
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.SortByData
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.ViewMoreDataForLatest
import webkul.opencart.mobikul.databinding.ActivityViewmoreBinding
import java.util.ArrayList

class ViewMoreActivity : BaseActivity(), SortByData {
    private var activityViewmoreBinding: ActivityViewmoreBinding? = null
    private var title: TextView? = null
    private var toolbar: Toolbar? = null
    private var viewMoreAdapter: ViewMoreAdapter? = null
    private var viewList: RecyclerView? = null
    private var latestMore: Callback<ViewMoreDataForLatest>? = null
    private var mToast: Toast? = null
    private var totalItems: Int = 0
    private var pageNumber: Int = 1
    private var pageLimit: Int = 10
    private var productTotal: Int = 10
    private var loading: Boolean = false
    val homePageAdapteModels = ArrayList<HomePageAdapteModel>()
    internal var sortData: Array<String>? = arrayOf("", "", "")
    private var categoryActivityHandler: CategoryActivityHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewmoreBinding = DataBindingUtil.setContentView(this, R.layout.activity_viewmore)
        title = activityViewmoreBinding!!.toolbar!!.findViewById(R.id.title)
        toolbar = activityViewmoreBinding!!.toolbar!!.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        title!!.setText(intent.getStringExtra("title"))
        actionBar.setDisplayHomeAsUpEnabled(true)
        viewList = activityViewmoreBinding!!.viewList
        val sort = AppCompatResources.getDrawable(this, R.drawable.ic_sort)
        activityViewmoreBinding!!.sort.setCompoundDrawablesRelativeWithIntrinsicBounds(sort, null, null, null)
        activityViewmoreBinding!!.handlers = CategoryActivityHandler(this)
        categoryActivityHandler = activityViewmoreBinding!!.handlers
        categoryActivityHandler!!.setSortByData(this)
        categoryActivityHandler!!.setSortData(this.sortData!!)
        viewList?.isNestedScrollingEnabled = false
        viewList?.addOnScrollListener(ScrollListener)
        viewList?.layoutManager = GridLayoutManager(this@ViewMoreActivity, 2)
        if (intent != null && intent.hasExtra("type"))
            fetchMoreData(intent.getStringExtra("type"))
    }

    private val ScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var lastCompletelyVisibleItemPosition = 0
            try {
                lastCompletelyVisibleItemPosition = (recyclerView.getLayoutManager() as GridLayoutManager).findLastVisibleItemPosition()
            } catch (exception: Exception) {
                Log.d("DEBUG", "lastCompletelyVisibleItemPosition")
                lastCompletelyVisibleItemPosition = (recyclerView.getLayoutManager() as LinearLayoutManager).findLastVisibleItemPosition()
            }
            try {
                if (mToast != null)
                    mToast!!.setText((lastCompletelyVisibleItemPosition + 1).toString() + resources.getString(R.string.of_toast_for_no_of_item) + productTotal)
                else
                    mToast = Toast.makeText(this@ViewMoreActivity, (lastCompletelyVisibleItemPosition + 1).toString() + resources.getString(R.string.of_toast_for_no_of_item) + productTotal, Toast.LENGTH_SHORT)
            } catch (e1: Resources.NotFoundException) {
                e1.printStackTrace()
            }

            if (dy > 5) {
                mToast!!.show()
            }

            if (dy < -80 || dy > 80) {
                activityViewmoreBinding!!.notificationLayout.setVisibility(View.GONE)
            }

            try {
                if (lastCompletelyVisibleItemPosition == totalItems - 1 && totalItems < productTotal) {
                    if (!loading) {
                        activityViewmoreBinding!!.listcategoryRequestBar.setVisibility(View.VISIBLE)
                        loading = true
                        pageNumber++
                        fetchMoreData(intent.getStringExtra("type"))
                    }
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }
    }


    fun fetchMoreData(type: String) {
        latestMore = object : Callback<ViewMoreDataForLatest> {
            override fun onResponse(call: Call<ViewMoreDataForLatest>, response: Response<ViewMoreDataForLatest>) {
                loading = false
                if (response.body()!!.error == 0) {
                    activityViewmoreBinding!!.listcategoryRequestBar.visibility = View.GONE
                    if (SweetAlertBox.sweetAlertDialog != null) {
                        SweetAlertBox.dissmissSweetAlertBox()
                    }

                    var viewMoreFeatured: ViewMoreDataForLatest? = response.body()
                    if (response.body()!!.sorts != null && response.body()!!.sorts!!.size > 0) {
                        val sortDataModels = ArrayList<Sort>()
                        if (viewMoreFeatured!!.sorts!!.isNotEmpty()) {
                            viewMoreFeatured!!.sorts?.let {
                                it.map { f ->
                                    println(" Value === " + f.value + " " + f.order)
                                    sortDataModels.add(f)
                                }
                            }
                        }
                        if (sortDataModels.size > 0) {
                            categoryActivityHandler!!.setSortList(sortDataModels)
                        }
                    }
                    if (response.body()!!.latestProduct != null && response.body()!!.latestProduct!!.size > 0) {
                        if (viewMoreFeatured!!.latestProduct!!.isNotEmpty()) {
//                            val homePageAdapteModelsnew = ArrayList<HomePageAdapteModel>()
                            viewMoreFeatured!!.latestProduct?.let {
                                it.map { f ->
                                    homePageAdapteModels.add(HomePageAdapteModel(f.thumb, f.price!!, f.name!!,
                                            f.productId!!, f.special!!.toString(), f.formattedSpecial!!,
                                            f.isHasOption!!, f.isWishlistStatus!!, f.dominant_color))
                                }
                            }
                        }
                        totalItems = homePageAdapteModels.size
                        productTotal = response.body()!!.productTotal
                        println(" Checking == " + viewMoreAdapter + "  " + homePageAdapteModels.size)
                        if (viewMoreAdapter == null) {
                            println(" Checking == inside " + viewMoreAdapter + "  " + homePageAdapteModels.size)
                            viewMoreAdapter = ViewMoreAdapter(this@ViewMoreActivity, homePageAdapteModels)
                            viewList!!.adapter = viewMoreAdapter
                        } else {
                            viewMoreAdapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ViewMoreDataForLatest>, t: Throwable) {
                t.printStackTrace()
            }
        }
        RetrofitCallback.getProductCarousal(this, type,
                pageNumber.toString(),
                pageLimit.toString(),
                sortData!![1],
                sortData!![0],
                RetrofitCustomCallback(latestMore, this))

        if (!loading)
            SweetAlertBox().showProgressDialog(this@ViewMoreActivity)
    }


    override fun datavalue(data: Array<String>) {
        pageNumber = 1
        totalItems = 0
        if (data != null)
            sortData = data
        categoryActivityHandler?.setSortData(this.sortData!!)
        homePageAdapteModels.clear()
        fetchMoreData(intent.getStringExtra("type"))
    }

}
