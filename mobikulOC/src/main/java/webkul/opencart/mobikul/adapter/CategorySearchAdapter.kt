package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.handlers.LeftNavHandlers
import webkul.opencart.mobikul.databinding.SearchCategoryItemBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CategorySearchAdapter(internal var activity: Activity, internal var categoryArrayList: ArrayList<RightNavAdapterModel>) : RecyclerView.Adapter<CategorySearchAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val searchCategoryItemBinding = SearchCategoryItemBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(searchCategoryItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.searchCategoryItemBinding.data = categoryArrayList[position]
        holder.searchCategoryItemBinding.handler = LeftNavHandlers(activity)
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    inner class MyViewHolder(internal var searchCategoryItemBinding: SearchCategoryItemBinding) : RecyclerView.ViewHolder(searchCategoryItemBinding.root) {
        internal var textLabel: TextView
        init {
            textLabel = searchCategoryItemBinding.textLabel
        }
    }
}
