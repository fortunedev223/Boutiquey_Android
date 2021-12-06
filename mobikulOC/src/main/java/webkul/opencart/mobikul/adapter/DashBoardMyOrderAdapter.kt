package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.DashBoardOrderAdapterModel
import webkul.opencart.mobikul.handlers.DashBoardMyOrderHandler
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.SingleDashboardMyordersLayoutBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class DashBoardMyOrderAdapter(val orderDataHolders: ArrayList<DashBoardOrderAdapterModel>, val mcontext: Context)
    : RecyclerView.Adapter<DashBoardMyOrderAdapter.OrderHolder>() {

    fun addAll(dataHolderArrayList: ArrayList<DashBoardOrderAdapterModel>) {
        orderDataHolders.addAll(dataHolderArrayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val myordersLayoutBinding = DataBindingUtil.inflate<SingleDashboardMyordersLayoutBinding>(LayoutInflater.from(mcontext), R.layout.single_dashboard_myorders_layout, parent, false)
        return OrderHolder(myordersLayoutBinding)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val orderDataHolder = orderDataHolders[position]
        if (holder.myordersLayoutBinding != null) {
            holder.myordersLayoutBinding.data = orderDataHolder
            holder.myordersLayoutBinding.handler = DashBoardMyOrderHandler(mcontext)
            holder.myordersLayoutBinding.executePendingBindings()
        } else {
            Toast.makeText(mcontext, "No Order Found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return orderDataHolders.size
    }

    inner class OrderHolder(val myordersLayoutBinding: SingleDashboardMyordersLayoutBinding) : RecyclerView.ViewHolder(myordersLayoutBinding.root)
}
