package webkul.opencart.mobikul.adapter

import android.content.Context
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.databinding.HomeProductCarousalBinding
import webkul.opencart.mobikul.model.GetHomePage.Modules

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class HomePageCarousalAdapter(val mContext: Context, val modules: ArrayList<Modules>)
    : RecyclerView.Adapter<HomePageCarousalAdapter.Myholder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Myholder {
        val binding = HomeProductCarousalBinding.inflate(LayoutInflater.from(mContext), p0, false)
        return Myholder(binding)
    }

    override fun getItemCount(): Int {
        return modules.size
    }

    override fun onBindViewHolder(holder: Myholder, p1: Int) {
        holder.binding.viewAll.setOnClickListener {
            (mContext as MainActivity).mMainActivityBinding?.handlers?.onClickViewAll(modules.get(p1).id!!, modules.get(p1).name!!)
        }
        holder.binding.data = modules.get(p1)
        holder.binding.executePendingBindings()


//        Handler().postDelayed(object : Runnable {
//            override fun run() {
//            }
//        }, ((p1 + 1) * 50).toLong())
    }

    class Myholder(val binding: HomeProductCarousalBinding) : RecyclerView.ViewHolder(binding.root)
}
