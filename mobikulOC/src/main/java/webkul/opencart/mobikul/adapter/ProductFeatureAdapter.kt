package webkul.opencart.mobikul.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.ProductFeatureAdapterModel
import webkul.opencart.mobikul.databinding.FeatureLayoutBinding
import webkul.opencart.mobikul.databinding.SingleFeatureBinding

/**
 * Created by manish.choudhary on 23/9/17.
 */

class ProductFeatureAdapter(private val mContext: Context, private val adapterModels: ArrayList<ProductFeatureAdapterModel>) : RecyclerView.Adapter<ProductFeatureAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val featureLayoutBinding = FeatureLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return MyHolder(featureLayoutBinding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val featureAdapterModel = adapterModels[position]
        holder.setIsRecyclable(false)
        try {
            for (i in 0 until featureAdapterModel.featureText!!.size) {
                val layoutBinding = SingleFeatureBinding.inflate(LayoutInflater.from(mContext))
                layoutBinding.name.text = featureAdapterModel.featureName!![i]
                layoutBinding.text.text = featureAdapterModel.featureText!![i]
                holder.featureLayoutBinding.featured.addView(layoutBinding.root)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.featureLayoutBinding.data = featureAdapterModel
        holder.featureLayoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    inner class MyHolder(internal var featureLayoutBinding: FeatureLayoutBinding) : RecyclerView.ViewHolder(featureLayoutBinding.root)
}
