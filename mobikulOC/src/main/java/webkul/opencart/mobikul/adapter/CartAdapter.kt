package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.CartAdapterModel
import webkul.opencart.mobikul.handlers.CartHandler
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.CartOptionLayoutBinding
import webkul.opencart.mobikul.databinding.ItemCartBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CartAdapter(val mcontext: Context, val cartData: ArrayList<CartAdapterModel>, val viewCart: ViewCart) : RecyclerView.Adapter<CartAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(mcontext)
        val cartBinding = DataBindingUtil.inflate<ItemCartBinding>(inflater, R.layout.item_cart, parent, false)
        return MyHolder(cartBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val cartData = this.cartData[position]
        holder.setIsRecyclable(false)
        holder.cartBinding.data = cartData
        cartData.option?.map {
            val binding = CartOptionLayoutBinding.inflate(LayoutInflater.from(mcontext))
            binding.title.text = Html.fromHtml(it.name).toString()
            binding.value.text = Html.fromHtml(it.value).toString()
            val view = View(mcontext)
            view.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1)
            view.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.white))
            holder.cartBinding.optionsLL.addView(binding.root)
            holder.cartBinding.optionsLL.addView(view)
        }
        holder.cartBinding.handler = CartHandler(mcontext, viewCart, this)
        holder.cartBinding.delete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                AppCompatResources.getDrawable(mcontext, R.drawable.delete_svg), null, null, null)
        holder.cartBinding.wishlist.setCompoundDrawablesRelativeWithIntrinsicBounds(
                AppCompatResources.getDrawable(mcontext, R.drawable.wishlishv3_product_page), null, null, null)
        holder.productqty.tag = position
        holder.cartBinding.addQty.tag = position
        holder.cartBinding.subQty.tag = position
        holder.cartBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return cartData.size
    }

    fun getData(position: Int): CartAdapterModel {
        return cartData[position]
    }

    inner class MyHolder(val cartBinding: ItemCartBinding) : RecyclerView.ViewHolder(cartBinding.root) {
        val productqty: TextView

        init {
            productqty = cartBinding.qty
        }
    }

}
