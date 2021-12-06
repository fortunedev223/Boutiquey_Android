package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.CurrencyAdapterModel
import webkul.opencart.mobikul.handlers.CurrencyHandler
import webkul.opencart.mobikul.databinding.CurrencyLayoutBinding



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class HomePageCurrencyAdapter(val mcontext: Context,val currencyDataHolders: ArrayList<CurrencyAdapterModel>) : RecyclerView.Adapter<HomePageCurrencyAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutBinding = CurrencyLayoutBinding.inflate(LayoutInflater.from(mcontext))
        return Holder(layoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dataHolder = currencyDataHolders[position]
        holder.layoutBinding.data = dataHolder
        holder.layoutBinding.handler = CurrencyHandler(mcontext)
        holder.layoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return currencyDataHolders.size
    }

    inner class Holder(internal var layoutBinding: CurrencyLayoutBinding) : RecyclerView.ViewHolder(layoutBinding.root)
}
