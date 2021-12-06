package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.GetAddressAdaperModel
import webkul.opencart.mobikul.handlers.GetAddressHandler
import webkul.opencart.mobikul.databinding.AddressItemsBinding



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class GetAddressAdapter(val mContext: Context,val getAddresses: ArrayList<GetAddressAdaperModel>) : RecyclerView.Adapter<GetAddressAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val child = AddressItemsBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyHolder(child)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val getAddress = getAddresses[position]
        holder.child.data = getAddress
        holder.child.handler = GetAddressHandler(mContext)
        holder.child.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return getAddresses.size
    }

    inner class MyHolder(val child: AddressItemsBinding) : RecyclerView.ViewHolder(child.root)
}
