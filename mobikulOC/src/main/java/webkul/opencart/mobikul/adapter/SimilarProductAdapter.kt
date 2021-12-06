package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.SimiliarProductsAdapterModel
import webkul.opencart.mobikul.handlers.SimilarProductHandler
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.SimilarProductsBinding
import webkul.opencart.mobikul.helper.Utils


class SimilarProductAdapter(val mcontext: Context, val datas: ArrayList<SimiliarProductsAdapterModel>) : RecyclerView.Adapter<SimilarProductAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SimilarProductsBinding.inflate(LayoutInflater.from(mcontext), null, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dataHolder = datas[position]
        holder.setIsRecyclable(false)
        if (Validation.checkSpecialPrice(dataHolder.isProductSpecial)) {
            holder.similarProductsBinding.productprice.textSize = 12f
            holder.similarProductsBinding.productprice.paintFlags = holder.similarProductsBinding.productprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        if (dataHolder.wishlistStatus) {
            holder.similarProductsBinding.wishlist.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_selected))
        } else {
            holder.similarProductsBinding.wishlist.setImageDrawable(AppCompatResources.getDrawable(mcontext, R.drawable.wishlist_v3_unselected))
        }
        holder.similarProductsBinding.data = dataHolder
        holder.similarProductsBinding.handler = SimilarProductHandler(mcontext)
        holder.similarProductsBinding.executePendingBindings()

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class Holder(val similarProductsBinding: SimilarProductsBinding) : RecyclerView.ViewHolder(similarProductsBinding.root) {
        private val productImage: ImageView
        init {
            productImage = similarProductsBinding.productImage
            productImage.scaleType = ImageView.ScaleType.CENTER_CROP
            val params = RelativeLayout.LayoutParams((Utils.getDeviceScreenWidth() / 2.2).toInt(), (Utils.getDeviceScreenWidth() / 2.4).toInt())
            params.layoutDirection = Gravity.CENTER
            productImage.layoutParams = params
            similarProductsBinding.relative.layoutParams = LinearLayout.LayoutParams(
                    Utils.getDeviceScreenWidth() / 2,
                    (Utils.getDeviceScreenWidth() / 1.3).toInt())
        }
    }
}
