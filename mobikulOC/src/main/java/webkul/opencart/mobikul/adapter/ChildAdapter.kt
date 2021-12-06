package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.model.GetHomePage.Product
import webkul.opencart.mobikul.model.RecentSearchModel
import webkul.opencart.mobikul.roomdatabase.RecentSearchTable
import webkul.opencart.mobikul.databinding.SearchChildItemBinding
import java.util.*
import android.graphics.drawable.GradientDrawable
import android.support.v7.content.res.AppCompatResources
import webkul.opencart.mobikul.R


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class ChildAdapter(internal var activity: Activity, internal var categoryNameList: ArrayList<webkul.opencart.mobikul.CategoryName>,
                   internal var categoryArrayList: ArrayList<RightNavAdapterModel>, internal var recentViewedTableList: List<RecentSearchModel>,
                   internal var recentSearchTableList: List<RecentSearchTable>, internal var carousalAdapterModelArrayList: ArrayList<CarousalAdapterModel>,
                   internal var productArrayList: ArrayList<Product>) : RecyclerView.Adapter<ChildAdapter.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val searchChildItemBinding = SearchChildItemBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyHolder(searchChildItemBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.childList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        holder.childList.isNestedScrollingEnabled = false
        holder.searchChildItemBinding.data = categoryNameList.get(position)
        if (position == 0) {
            holder.childList.adapter = CategorySearchAdapter(activity, categoryArrayList)
            holder.llParent.visibility = if (categoryArrayList.size > 0) View.VISIBLE else View.GONE
            holder.childList.visibility = if (categoryArrayList.size > 0) View.VISIBLE else View.GONE
            holder.textLebel.visibility = if (categoryArrayList.size > 0) View.VISIBLE else View.GONE
            holder.itemFirst.visibility = View.GONE
        } else if (position == 1) {
            holder.childList.adapter = RecentSearchWordListAdapter(activity, recentSearchTableList)
            holder.llParent.visibility = if (recentSearchTableList.size > 0) View.VISIBLE else View.GONE
            holder.childList.visibility = if (recentSearchTableList.size > 0) View.VISIBLE else View.GONE
            holder.textLebel.visibility = if (recentSearchTableList.size > 0) View.VISIBLE else View.GONE
            holder.itemFirst.visibility = View.GONE
        }
//        else if (position == 2) {
//            holder.itemFirst.visibility = if (productArrayList.size > 0) View.VISIBLE else View.GONE
//            holder.childList.adapter = SearchLatestProductAdapter(activity, productArrayList)
//            holder.llParent.visibility = if (productArrayList.size > 0) View.VISIBLE else View.GONE
//            holder.childList.visibility = if (productArrayList.size > 0) View.VISIBLE else View.GONE
//            holder.textLebel.visibility = if (productArrayList.size > 0) View.VISIBLE else View.GONE
//            holder.textLebel.setTextColor(activity.resources.getColor(R.color.white))
//            holder.llParent.background = AppCompatResources.getDrawable(activity, R.drawable.homescreen_gredient3)
//        }
        else if (position == 2) {
            holder.childList.adapter = RecentSearch(activity, recentViewedTableList)
            holder.llParent.visibility = if (recentViewedTableList.size > 0) View.VISIBLE else View.GONE
            holder.childList.visibility = if (recentViewedTableList.size > 0) View.VISIBLE else View.GONE
            holder.textLebel.visibility = if (recentViewedTableList.size > 0) View.VISIBLE else View.GONE
            holder.itemFirst.visibility = View.GONE
        } else if (position == 3) {
            holder.childList.adapter = CarousalAdapter(activity, carousalAdapterModelArrayList)
            holder.llParent.visibility = if (carousalAdapterModelArrayList.size > 0) View.VISIBLE else View.GONE
            holder.childList.visibility = if (carousalAdapterModelArrayList.size > 0) View.VISIBLE else View.GONE
            holder.textLebel.visibility = if (carousalAdapterModelArrayList.size > 0) View.VISIBLE else View.GONE
            holder.itemFirst.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    inner class MyHolder(internal var searchChildItemBinding: SearchChildItemBinding) : RecyclerView.ViewHolder(searchChildItemBinding.root) {
        internal var textLebel: TextView
        internal var itemFirst: TextView
        internal var childList: RecyclerView
        internal var llParent: LinearLayout

        init {
            itemFirst = searchChildItemBinding.itemFirst
            textLebel = searchChildItemBinding.textLebel
            childList = searchChildItemBinding.childList
            llParent = searchChildItemBinding.llParent
        }
    }

}
