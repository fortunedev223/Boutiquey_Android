package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import webkul.opencart.mobikul.handlers.RecentSearchHandler
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.roomdatabase.RecentSearchTable
import webkul.opencart.mobikul.databinding.RecentSearchItemLayoutBinding

class RecentSearchWordListAdapter(val mContext: Context, private val recentSearchList: List<RecentSearchTable>) :
        RecyclerView.Adapter<RecentSearchWordListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutBinding = RecentSearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(layoutBinding)
    }

    override fun getItemCount(): Int {
        return recentSearchList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layoutBinding.data = recentSearchList[position]
        holder.layoutBinding.handler = RecentSearchHandler(mContext)
    }

    class MyViewHolder(val layoutBinding: RecentSearchItemLayoutBinding) : RecyclerView.ViewHolder(layoutBinding.root){
        init {
            layoutBinding.mainLayout.layoutParams= ConstraintLayout.LayoutParams(Utils.getDeviceScreenWidth()/4,Utils.getDeviceScreenWidth()/4)
        }
    }
}