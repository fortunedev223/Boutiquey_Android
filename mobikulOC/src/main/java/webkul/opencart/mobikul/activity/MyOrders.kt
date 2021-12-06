package webkul.opencart.mobikul.activity

import android.content.Context
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.DashBoardMyOrderAdapter
import webkul.opencart.mobikul.adapterModel.DashBoardOrderAdapterModel
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.model.DashboardMyOrder.DashboardMyOrder
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.ActivityMyOrdersBinding

class MyOrders : BaseActivity() {
    private var ordersBinding: ActivityMyOrdersBinding? = null
    protected var pageNumber = 1
    var toolbarOrders: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DashBoardMyOrderAdapter? = null
    private var dashBoardMyOrdersCallback: Callback<DashboardMyOrder>? = null
    private var dashBoardMyOrdersLazyCallback: Callback<DashboardMyOrder>? = null
    private var title: TextView? = null
    protected var mToast: Toast? = null
    private var loading = false
    private var productTotal: Int = 0
    private var totalItems: Int = 0

    fun checkConn(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    private val ScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            var lastCompletelyVisibleItemPosition = 0

            try {
                lastCompletelyVisibleItemPosition = (recyclerView!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            } catch (exception: Exception) {
                lastCompletelyVisibleItemPosition = (recyclerView!!.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }

            try {
                if (mToast != null)
                    mToast!!.setText((lastCompletelyVisibleItemPosition + 1).toString() + resources.getString(R.string.of_toast_for_no_of_item) + productTotal)
                else
                    mToast = Toast.makeText(this@MyOrders, (lastCompletelyVisibleItemPosition + 1).toString() + resources.getString(R.string.of_toast_for_no_of_item) + productTotal, Toast.LENGTH_SHORT)
            } catch (e1: Resources.NotFoundException) {
                e1.printStackTrace()
            }

            if (dy > 5) {
                mToast!!.show()
            }


            try {
                if (lastCompletelyVisibleItemPosition == totalItems - 1 && totalItems < productTotal) {
                    if (!loading) {
                        loading = true
                        pageNumber++
                        setLazyLoad()
                        SweetAlertBox().showProgressDialog(this@MyOrders)
                        RetrofitCallback.dashboardMyOrderCall(this@MyOrders,
                                pageNumber.toString(),
                                RetrofitCustomCallback(dashBoardMyOrdersLazyCallback, this@MyOrders))
                    }
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_orders)
        recyclerView = ordersBinding?.myorderRecylcerview
        toolbarOrders = ordersBinding?.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbarOrders)
        title = ordersBinding?.toolbar?.findViewById<View>(R.id.title) as TextView
        title?.text = getString(R.string.myorder_action_title)
        dashBoardMyOrdersCallback = object : Callback<DashboardMyOrder> {
            override fun onResponse(call: Call<DashboardMyOrder>, response: Response<DashboardMyOrder>) {
                ordersBinding?.progressBar?.visibility = View.GONE
                if (response.body()?.error!! == 1) {
                    ordersBinding?.errorTv?.visibility = View.VISIBLE
                } else if (response.body()?.error == 0) {
                    if (response.body()?.orderData != null) {
                        ordersBinding!!.errorTv.visibility = View.GONE
                        val dataHolderArrayList = ArrayList<DashBoardOrderAdapterModel>()
                        for (i in 0 until response.body()!!.orderData!!.size) {
                            dataHolderArrayList.add(DashBoardOrderAdapterModel(this@MyOrders,
                                    response.body()!!.orderData!![i].orderId!!,
                                    response.body()!!.orderData!![i].name,
                                    response.body()!!.orderData!![i].status,
                                    response.body()!!.orderData!![i].dateAdded!!,
                                    response.body()!!.orderData!![i].products!!,
                                    response.body()!!.orderData!![i].total!!))
                        }
                        adapter = DashBoardMyOrderAdapter(dataHolderArrayList, this@MyOrders)
                        recyclerView!!.adapter = adapter
                        recyclerView!!.layoutManager = LinearLayoutManager(this@MyOrders)
                        recyclerView!!.addOnScrollListener(ScrollListener)
                        totalItems = totalItems + response.body()!!.orderData!!.size
                        productTotal = Integer.parseInt(response.body()!!.orderTotals)
                    } else {
                        ordersBinding?.errorTv?.visibility = View.VISIBLE
                    }
                }

            }

            override fun onFailure(call: Call<DashboardMyOrder>, t: Throwable) {

            }
        }
        if (!checkConn()) {
            showDialog(this)
        } else {
            RetrofitCallback.dashboardMyOrderCall(this@MyOrders,
                    pageNumber.toString(),
                    RetrofitCustomCallback(dashBoardMyOrdersCallback, this@MyOrders))
        }
    }

    private fun setLazyLoad() {
        dashBoardMyOrdersLazyCallback = object : Callback<DashboardMyOrder> {
            override fun onResponse(call: Call<DashboardMyOrder>, response: Response<DashboardMyOrder>) {
                SweetAlertBox.sweetAlertDialog!!.dismissWithAnimation()
                if (response.body()!!.error == 1) {
                    ordersBinding!!.errorTv.visibility = View.VISIBLE
                } else if (response.body()!!.error == 0) {
                    if (response.body()!!.orderData != null) {
                        ordersBinding!!.errorTv.visibility = View.GONE
                        val dataHolderArrayList = ArrayList<DashBoardOrderAdapterModel>()
                        for (i in 0 until response.body()!!.orderData!!.size) {
                            dataHolderArrayList.add(DashBoardOrderAdapterModel(this@MyOrders,
                                    response.body()?.orderData!![i].orderId!!,
                                    response.body()?.orderData!![i].name,
                                    response.body()?.orderData!![i].status,
                                    response.body()?.orderData!![i].dateAdded!!,
                                    response.body()?.orderData!![i].products!!,
                                    response.body()?.orderData!![i].total!!))
                        }
                        adapter?.addAll(dataHolderArrayList)
                        adapter?.notifyDataSetChanged()
                        totalItems = totalItems + response.body()!!.orderData!!.size
                        productTotal = Integer.parseInt(response.body()!!.orderTotals)
                        loading = false
                    } else {
                        ordersBinding?.errorTv?.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<DashboardMyOrder>, t: Throwable) {

            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_bell).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }
}
