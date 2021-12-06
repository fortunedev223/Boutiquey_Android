package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import de.hdodenhof.circleimageview.CircleImageView
import webkul.opencart.mobikul.handlers.RecentSearchHandler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.RecentSearchModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.RecentSearchLayoutBinding


class RecentSearch(val mContext: Context, val list: List<RecentSearchModel>) : RecyclerView.Adapter<RecentSearch.Myholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val layoutBinding = RecentSearchLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return Myholder(layoutBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val recentSearchModel = list[position]
        holder.layoutBinding.data = recentSearchModel
        if (recentSearchModel.productImage != null) {
            Glide.with(mContext)
                    .load(recentSearchModel.productImage)
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(holder.productImage)
        } else {
            Glide.with(mContext)
                    .load(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(holder.productImage)
        }
        holder.layoutBinding.handler = RecentSearchHandler(mContext)
    }

    inner class Myholder(val layoutBinding: RecentSearchLayoutBinding) : RecyclerView.ViewHolder(layoutBinding.root) {

        val title: TextView
        val productImage: ImageView
        val main_linear: LinearLayout

        init {
            title = layoutBinding.search
            productImage = layoutBinding.productImage
            main_linear = layoutBinding.mainLinear
            var params = LinearLayout.LayoutParams(
                    Utils.getDeviceScreenWidth() / 3,
                    Utils.getDeviceScreenWidth() / 3)
            productImage.layoutParams = params
            params.gravity = Gravity.CENTER_HORIZONTAL
            productImage.setPadding(8, 8, 8, 8)
        }
    }
}