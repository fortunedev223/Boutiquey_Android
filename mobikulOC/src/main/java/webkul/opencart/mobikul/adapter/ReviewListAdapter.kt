package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import webkul.opencart.mobikul.databinding.ReviewLayoutBinding
import webkul.opencart.mobikul.model.ReviewListModel.Review

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

class ReviewListAdapter(val mContext: Context, val reviews: ArrayList<Review>) : RecyclerView.Adapter<ReviewListAdapter.MyHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        val binding = ReviewLayoutBinding.inflate(LayoutInflater.from(mContext), p0, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun addAll(review: ArrayList<Review>) {
        reviews.addAll(review)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        holder.binding.auther.text = reviews.get(pos).author
        holder.binding.date.text = reviews.get(pos).dateAdded
        holder.binding.ratingBar.rating = reviews.get(pos).rating?.toFloat()!!
        holder.binding.reviewMsg.text = reviews.get(pos).text
        holder.binding.executePendingBindings()
    }

    class MyHolder(val binding: ReviewLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
