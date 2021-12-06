package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.ArrayList
import webkul.opencart.mobikul.adapterModel.SubCategoryV3ThemeAdapterModel
import webkul.opencart.mobikul.handlers.SubCategoryV3Handler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.SubcategoryV3themeLayoutBinding


class SubCategoryV3ThemeAdapter(val models: ArrayList<SubCategoryV3ThemeAdapterModel>, val mContext: Context)
    : RecyclerView.Adapter<SubCategoryV3ThemeAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = SubcategoryV3themeLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val model = models[position]
        holder.binding.data = model
        holder.binding.handler = SubCategoryV3Handler(mContext)
        holder.binding.image.setBackgroundColor(Color.parseColor(models.get(position).dominantColor))
        if (model.imageUrl?.length!! != 0) {
            Glide.with(mContext)
                    .load(model.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .into(holder.binding.image)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class MyHolder(internal var binding: SubcategoryV3themeLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView

        init {
            imageView = binding.image
            val params = RelativeLayout.LayoutParams(
                    Utils.getDeviceScreenWidth(),
                    Utils.getDeviceScreenWidth() / 2)
            imageView.layoutParams = params
        }
    }
}
