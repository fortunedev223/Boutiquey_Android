package webkul.opencart.mobikul.handlers

import android.content.Context
import android.content.Intent

import webkul.opencart.mobikul.adapterModel.DashBoardOrderAdapterModel
import webkul.opencart.mobikul.ViewMyOrderActivity


class DashBoardMyOrderHandler(private val mcontext: Context) {

    fun onClickDetails(data: DashBoardOrderAdapterModel) {
        println(" on Order clicking ==== ")
        val intent = Intent(mcontext, ViewMyOrderActivity::class.java)
        val orderID = data.orderId!!.substring(data.orderId!!.indexOf("#") + 1, data.orderId!!.length)
        intent.putExtra("orderId", orderID)
        intent.putExtra("status", data.status)
        mcontext.startActivity(intent)
    }
}
