package webkul.opencart.mobikul.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.DashBoardMyOrderAdapter
import webkul.opencart.mobikul.adapterModel.DashBoardOrderAdapterModel
import webkul.opencart.mobikul.model.DashboardMyOrder.DashboardMyOrder
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.FragmentDashBoardMyOrdersBinding


class DashBoardMyOrders : Fragment() {
    var ordersBinding: FragmentDashBoardMyOrdersBinding? = null
        private set
    protected var pageNumber = 1
    private var recyclerView: RecyclerView? = null
    private var adapter: DashBoardMyOrderAdapter? = null
    private var dashBoardMyOrdersCallback: Callback<DashboardMyOrder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        ordersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board_my_orders, container, false)
        recyclerView = ordersBinding!!.myorderRecylcerview
        dashBoardMyOrdersCallback = object : Callback<DashboardMyOrder> {
            override fun onResponse(call: Call<DashboardMyOrder>, response: Response<DashboardMyOrder>) {
                val dataHolderArrayList = ArrayList<DashBoardOrderAdapterModel>()
                if (response.body()!!.orderData != null) {
                    if (response.body()!!.orderData!!.size != 0) {
                        for (i in 0 until response.body()!!.orderData!!.size) {
                            dataHolderArrayList.add(DashBoardOrderAdapterModel(activity!!,
                                    response.body()!!.orderData!![i].orderId!!,
                                    response.body()!!.orderData!![i].name,
                                    response.body()!!.orderData!![i].status,
                                    response.body()!!.orderData!![i].dateAdded!!,
                                    response.body()!!.orderData!![i].products!!,
                                    response.body()!!.orderData!![i].total!!))
                        }
                        if (isAdded)
                            adapter = DashBoardMyOrderAdapter(dataHolderArrayList, activity!!)
                        recyclerView!!.adapter = adapter
                        recyclerView!!.layoutManager = LinearLayoutManager(activity)
                        recyclerView!!.hasFixedSize()
                    } else {
                        ordersBinding!!.errorTv.visibility = View.VISIBLE

                    }
                }

            }

            override fun onFailure(call: Call<DashboardMyOrder>, t: Throwable) {

            }
        }
        RetrofitCallback.dashboardMyOrderCall(activity!!, pageNumber.toString(), RetrofitCustomCallback(dashBoardMyOrdersCallback, activity))
        return ordersBinding!!.root
    }


}// Required empty public constructor
