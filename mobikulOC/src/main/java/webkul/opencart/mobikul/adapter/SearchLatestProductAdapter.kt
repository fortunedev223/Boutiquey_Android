package webkul.opencart.mobikul.adapter

import android.app.Activity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import de.hdodenhof.circleimageview.CircleImageView
import webkul.opencart.mobikul.handlers.RecentSearchHandler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.GetHomePage.Product
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.SearchProductItemLayoutBinding

class SearchLatestProductAdapter(internal var activity: Activity, internal var list: List<Product>) : RecyclerView.Adapter<SearchLatestProductAdapter.MyViewHolder>() {
    internal var TAG = SearchLatestProductAdapter::class.java.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val searchProductItemLayoutBinding = SearchProductItemLayoutBinding.inflate(LayoutInflater.from(activity), parent, false)
        return MyViewHolder(searchProductItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == 0) {
            holder.searchProductItemLayoutBinding.root.visibility= View.GONE
        } else {
            holder.searchProductItemLayoutBinding.data = list[position-1]
            if (list[position-1].thumb != null) {
                Glide.with(activity)
                        .load(list[position-1].thumb)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(holder.productImage)
            } else {
                Glide.with(activity)
                        .load(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(holder.productImage)
            }
            holder.searchProductItemLayoutBinding.handler = RecentSearchHandler(activity)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    inner class MyViewHolder(internal var searchProductItemLayoutBinding: SearchProductItemLayoutBinding) : RecyclerView.ViewHolder(searchProductItemLayoutBinding.root) {
        internal var productImage: ImageView
        internal var search: TextView
        internal var firstItem: TextView
        internal var mainLinear: CardView

        init {
            productImage = searchProductItemLayoutBinding.productImage
            productImage.layoutParams = LinearLayout.LayoutParams(
                    Utils.getDeviceScreenWidth() / 5,
                    Utils.getDeviceScreenWidth() / 5)
            productImage.setPadding(10, 10, 10, 10)
            search = searchProductItemLayoutBinding.search
            mainLinear = searchProductItemLayoutBinding.mainLinear
            firstItem = searchProductItemLayoutBinding.firstItem
        }
    }
}
