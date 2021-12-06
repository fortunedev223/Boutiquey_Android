package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Paint
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.GetWishlistAdapterModel
import webkul.opencart.mobikul.handlers.GetWishlistHandler
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ItemMyWishlistBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class GetWishlistAdapter(val mcontext: Context, val wishlistDataHolderArrayList: ArrayList<GetWishlistAdapterModel>) :
        RecyclerView.Adapter<GetWishlistAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val wishlistBinding = DataBindingUtil.inflate<ItemMyWishlistBinding>(LayoutInflater.from(mcontext),
                R.layout.item_my_wishlist, parent, false)
        return Holder(wishlistBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val wishlistDataHolder = wishlistDataHolderArrayList[position]
        if (Validation.checkSpecialPrice(wishlistDataHolder.productSpecailPrice)) {
            holder.wishlistBinding.newPrice.textSize = 12f
            holder.wishlistBinding.newPrice.paintFlags = holder.wishlistBinding.newPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.wishlistBinding.handler = GetWishlistHandler(mcontext)
            holder.wishlistBinding.data = wishlistDataHolder
            holder.wishlistBinding.executePendingBindings()
        } else {
            holder.wishlistBinding.handler = GetWishlistHandler(mcontext)
            holder.wishlistBinding.data = wishlistDataHolder
            holder.wishlistBinding.executePendingBindings()
        }
        holder.wishlistBinding.delete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                AppCompatResources.getDrawable(mcontext, R.drawable.delete_svg), null, null, null)
        holder.wishlistBinding.addToCart.setCompoundDrawablesRelativeWithIntrinsicBounds(
                AppCompatResources.getDrawable(mcontext, R.drawable.toolbar_cart), null, null, null)
        holder.wishlistBinding.handler!!.setBinding(holder.wishlistBinding)
    }

    override fun getItemCount(): Int {
        return wishlistDataHolderArrayList.size
    }

    inner class Holder(internal var wishlistBinding: ItemMyWishlistBinding) : RecyclerView.ViewHolder(wishlistBinding.root)
}
