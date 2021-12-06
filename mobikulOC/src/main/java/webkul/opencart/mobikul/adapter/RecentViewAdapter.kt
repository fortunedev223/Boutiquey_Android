package webkul.opencart.mobikul.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.handlers.RecentViewedHandler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.RecentViewProductLayoutBinding
import java.util.ArrayList

class RecentViewAdapter(val mContext: Context, val list: ArrayList<HomePageAdapteModel>) : RecyclerView.Adapter<RecentViewAdapter.Myholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val binding = RecentViewProductLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return Myholder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val model = list.get(position)
        if (Validation.checkSpecialPrice(model.specialPrice)) {
            holder.binding.productprice.textSize = 12f
            holder.binding.productprice.paintFlags = holder.binding.productprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.data = model
            holder.binding.handler = RecentViewedHandler(mContext)
            holder.binding.executePendingBindings()
        } else {
            holder.binding.data = model
            holder.binding.handler = RecentViewedHandler(mContext)
            holder.binding.executePendingBindings()
        }
    }

    class Myholder(val binding: RecentViewProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.layoutParams = LinearLayout.LayoutParams(Utils.getDeviceScreenWidth() / 4,
                    Utils.getDeviceScreenWidth() / 4)
        }
    }
}