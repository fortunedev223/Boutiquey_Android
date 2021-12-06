package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.LanguageAdapterModel
import webkul.opencart.mobikul.handlers.LanguageHandler
import webkul.opencart.mobikul.databinding.LanguageLayoutBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class HomePageLanguageAdapter(val mcontext: Context, val languageDataHolders: ArrayList<LanguageAdapterModel>)
    : RecyclerView.Adapter<HomePageLanguageAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutBinding = LanguageLayoutBinding.inflate(LayoutInflater.from(mcontext))
        return Holder(layoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dataHolder = languageDataHolders[position]
        holder.layoutBinding.data = dataHolder
        holder.layoutBinding.handler = LanguageHandler(mcontext)
        holder.layoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return languageDataHolders.size
    }

    inner class Holder(internal var layoutBinding: LanguageLayoutBinding) : RecyclerView.ViewHolder(layoutBinding.root)
}
