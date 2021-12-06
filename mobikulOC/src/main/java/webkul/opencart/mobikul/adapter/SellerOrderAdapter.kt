package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.SellerOrderAdapterModel
import webkul.opencart.mobikul.handlers.SellerOrderHandler
import webkul.opencart.mobikul.databinding.ItemMyOrderBinding

class SellerOrderAdapter(private val mcontext: Context, private val sellerOrderAdapterModels: ArrayList<SellerOrderAdapterModel>)
    : RecyclerView.Adapter<SellerOrderAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myOrderBinding = ItemMyOrderBinding.inflate(LayoutInflater.from(mcontext), parent, false)
        return MyHolder(myOrderBinding)
    }

    fun addAll(sellerOrderAdapterModels: ArrayList<SellerOrderAdapterModel>) {
        this.sellerOrderAdapterModels.addAll(sellerOrderAdapterModels)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val sellerOrderAdapterModel = sellerOrderAdapterModels[position]
        holder.myOrderBinding.data = sellerOrderAdapterModel
        holder.myOrderBinding.handler = SellerOrderHandler(mcontext)
        holder.myOrderBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return sellerOrderAdapterModels.size
    }

    inner class MyHolder(val myOrderBinding: ItemMyOrderBinding) : RecyclerView.ViewHolder(myOrderBinding.root)
}
