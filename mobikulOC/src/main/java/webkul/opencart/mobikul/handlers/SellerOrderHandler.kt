package webkul.opencart.mobikul.handlers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View

import webkul.opencart.mobikul.adapterModel.SellerOrderAdapterModel
import webkul.opencart.mobikul.ViewMyOrderActivity

class SellerOrderHandler(private val mContext: Context) {

    fun onClickOrder(view: View, adapterModel: SellerOrderAdapterModel) {
        println(" seller order calling ==== "+adapterModel.orderId+"  "+adapterModel.orderId)
        val intent = Intent(mContext, ViewMyOrderActivity::class.java)
        intent.putExtra("orderId", adapterModel.orderId)
        intent.putExtra("status", adapterModel.status)
        intent.putExtra("isSeller", adapterModel.orderId)
        (mContext as Activity).startActivityForResult(intent,111)
    }
}
