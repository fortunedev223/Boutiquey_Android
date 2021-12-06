package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.ArrayList
import webkul.opencart.mobikul.adapterModel.RightNavAdapterModel
import webkul.opencart.mobikul.handlers.LeftNavHandlers
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.databinding.SubcategorySingleLayoutBinding


class SubcategoryAdapter(val adapterModels: ArrayList<RightNavAdapterModel>, val mcontext: Context) :
        RecyclerView.Adapter<SubcategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutBinding = SubcategorySingleLayoutBinding.inflate(LayoutInflater.from(mcontext))
        return Holder(layoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = adapterModels[position]
        holder.layoutBinding.data = data
        holder.layoutBinding.subcategoryLayout.tag = position
        holder.imageView.setBackgroundColor(Color.parseColor(adapterModels.get(position).dominantColor))
        if (data.imageUrl != null) {
            Glide.with(mcontext)
                    .load(data.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<String, GlideDrawable> {
                        override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: GlideDrawable, model: String,
                                                     target: Target<GlideDrawable>,
                                                     isFromMemoryCache: Boolean,
                                                     isFirstResource: Boolean): Boolean {
                            holder.imageView.setBackgroundColor(0)
                            return false
                        }
                    })
                    .dontAnimate()
                    .into(holder.imageView)
            holder.subCategoryTxt.text = data.categoryName
            holder.layoutBinding.handler = LeftNavHandlers(mcontext)
            holder.layoutBinding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    inner class Holder(val layoutBinding: SubcategorySingleLayoutBinding) : RecyclerView.ViewHolder(layoutBinding.root) {
        val subCategoryTxt: TextView
        private val frameLayout: LinearLayout
        val imageView: ImageView
        private val shimmerFrameLayout: ShimmerFrameLayout

        init {
            shimmerFrameLayout = layoutBinding.shimmerViewContainer
            frameLayout = layoutBinding.subcategoryLayout
            imageView = layoutBinding.subCategoryImage
            subCategoryTxt = layoutBinding.subcategoryTxt
            val params = LinearLayout.LayoutParams(Utils.getDeviceScreenWidth() / 5, Utils.getDeviceScreenWidth() / 5)
            params.setMargins(4, 2, 4, 2)
            imageView.setPadding(10, 10, 10, 10)
            imageView.layoutParams = params
        }
    }
}
