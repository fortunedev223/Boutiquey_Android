package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.handlers.CarousalHandler
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.databinding.SingleLayoutBrandsBinding
import webkul.opencart.mobikul.helper.Utils


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class BrandsAdapter(val mcontext: Context,val list: ArrayList<CarousalAdapterModel>) : RecyclerView.Adapter<BrandsAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val brandsBinding = SingleLayoutBrandsBinding.inflate(LayoutInflater.from(mcontext), parent, false)
        return MyHolder(brandsBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = list[position]
        holder.brandsBinding.data = model
        holder.brandsBinding.handler = CarousalHandler(mcontext)
        holder.brandsBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(val brandsBinding: SingleLayoutBrandsBinding) : RecyclerView.ViewHolder(brandsBinding.root) {
        private val imageView: ImageView

        init {
            imageView = brandsBinding.brandImage
            val params = LinearLayout.LayoutParams((Utils.getDeviceScreenWidth() / 2.2).toInt(), (Utils.getDeviceScreenWidth()  / 2.2).toInt())
            imageView.layoutParams = params
        }
    }
}
