package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import webkul.opencart.mobikul.MainActivity

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.handlers.LeftNavHandlers
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.LeftNavLayoutBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class LeftNavAdapter(private val mcontext: Context, private val dataholders: ArrayList<RightNavAdapterModel>) : RecyclerView.Adapter<LeftNavAdapter.Holder>() {
    private val FADE_DURATION = 1500 // in milliseconds

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutBinding = LeftNavLayoutBinding.inflate(LayoutInflater.from(mcontext))
        return Holder(layoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val navDataholder = dataholders[position]
        holder.leftNavLayoutBinding.data = navDataholder
        holder.leftNavLayoutBinding.leftNavLl.tag = position
        holder.leftNavLayoutBinding.handler = LeftNavHandlers(mcontext, holder.leftNavLayoutBinding)
        holder.leftNavLayoutBinding.image.setBackgroundColor(Color.parseColor(navDataholder.dominantColor))
        Glide.with(mcontext)
                .load(navDataholder.iconUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        holder.leftNavLayoutBinding.image.setBackgroundColor(0)
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String,
                                                 target: Target<GlideDrawable>,
                                                 isFromMemoryCache: Boolean,
                                                 isFirstResource: Boolean): Boolean {
                        holder.leftNavLayoutBinding.image.setBackgroundColor(0)
                        return false
                    }
                })
                .skipMemoryCache(true)
                .into(holder.sub_image)
        holder.leftNavLayoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return dataholders.size
    }

    inner class Holder(val leftNavLayoutBinding: LeftNavLayoutBinding) : RecyclerView.ViewHolder(leftNavLayoutBinding.root) {
        private val linearLayout: LinearLayout
        private val categoryName: TextView
        val sub_image: ImageView
        val shimmerFrameLayout: ShimmerFrameLayout

        init {
            val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            linearLayout = leftNavLayoutBinding.leftNavLl
            categoryName = leftNavLayoutBinding.categoryName
            sub_image = leftNavLayoutBinding.image
            val params1 = FrameLayout.LayoutParams(
                    Utils.getDeviceScreenWidth() / 10,
                    Utils.getDeviceScreenWidth() / 10)
            sub_image.layoutParams = params1
            if (MainActivity.homeDataModel?.languages?.code.equals("ar")) {
                params1.setMargins(0, 0, 20, 0)
            } else {
                params1.setMargins(20, 0, 0, 0)
            }
            shimmerFrameLayout = leftNavLayoutBinding.shimmerViewContainer
            linearLayout.layoutParams = params
            params.setMargins(0, 4, 0, 4)
        }
    }
}
