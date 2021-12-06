package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.handlers.CarousalHandler
import webkul.opencart.mobikul.databinding.CarousalLayoutBinding
import webkul.opencart.mobikul.helper.Utils


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class CarousalAdapter(val mcontext: Context, val carousalAdapterModels: ArrayList<CarousalAdapterModel>) : RecyclerView.Adapter<CarousalAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val carousalLayoutBinding = CarousalLayoutBinding.inflate(LayoutInflater.from(mcontext), parent, false)
        return Holder(carousalLayoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = carousalAdapterModels[position]
        holder.carousalLayoutBinding.data = model
        holder.carousalLayoutBinding.handler = CarousalHandler(mcontext)
        holder.carousalLayoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return carousalAdapterModels.size
    }

    inner class Holder(val carousalLayoutBinding: CarousalLayoutBinding) : RecyclerView.ViewHolder(carousalLayoutBinding.root) {
        private val imageView: ImageView

        init {
            imageView = carousalLayoutBinding.brandImage
            val params = LinearLayout.LayoutParams((Utils.getDeviceScreenWidth() / 2.2).toInt(), (Utils.getDeviceScreenWidth() / 2.2).toInt())
            imageView.layoutParams = params
        }
    }
}
