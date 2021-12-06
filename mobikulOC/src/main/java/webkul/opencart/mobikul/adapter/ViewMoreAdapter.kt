package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.app.Dialog
import android.graphics.Paint
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import retrofit2.Call
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.handlers.ViewMoreHandler
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist

import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.utils.MakeToast
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ViewMoreItemLayoutBinding
import java.util.ArrayList

class ViewMoreAdapter(private val context: Activity, private val data: ArrayList<HomePageAdapteModel>) : RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder>() {

    internal var mContext: Activity? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewMoreItemLayoutBinding = ViewMoreItemLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(viewMoreItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mydata = data[position]
        if (Validation.checkSpecialPrice(mydata.specialPrice)) {
            holder.viewMoreItemLayoutBinding.productprice.textSize = 12f
            holder.viewMoreItemLayoutBinding.productprice.paintFlags = holder.viewMoreItemLayoutBinding.productprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.viewMoreItemLayoutBinding.productprice.paintFlags = 0
            holder.viewMoreItemLayoutBinding.productprice.text = mydata.price
        }
        holder.viewMoreItemLayoutBinding.data = mydata
        holder.viewMoreItemLayoutBinding.productImage.setTag(R.id.productImage, position)
        holder.viewMoreItemLayoutBinding.handler = ViewMoreHandler(context)
        holder.viewMoreItemLayoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(val viewMoreItemLayoutBinding: ViewMoreItemLayoutBinding) : RecyclerView.ViewHolder(viewMoreItemLayoutBinding.root) {
        private val productImage: ImageView
        private val wishlist: ImageView
        private val price: TextView
        private val specialPrice: TextView
        private val homeProductCardView: CardView
        private val llParent: LinearLayout

        init {
            homeProductCardView = viewMoreItemLayoutBinding.productCardv
            productImage = viewMoreItemLayoutBinding.productImage
            price = viewMoreItemLayoutBinding.productprice
            specialPrice = viewMoreItemLayoutBinding.specialProductPrice
            wishlist = viewMoreItemLayoutBinding.wishlist
            llParent = viewMoreItemLayoutBinding.llParent
            val params = FrameLayout.LayoutParams(
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2).toInt(),
                    (webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2).toInt())
            productImage.layoutParams = params
        }
    }

    fun addAll(items: List<HomePageAdapteModel>) {
        data.addAll(items)
    }
}
