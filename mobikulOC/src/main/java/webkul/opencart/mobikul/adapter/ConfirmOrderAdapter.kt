package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.ConfirmOrderAdapteModel
import webkul.opencart.mobikul.handlers.ConfirmCheckoutHandler
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ConfirmorderProductLayoutBinding
import webkul.opencart.mobikul.helper.Utils


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class ConfirmOrderAdapter(val mcontext: Context,val list: ArrayList<ConfirmOrderAdapteModel>) : RecyclerView.Adapter<ConfirmOrderAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val productLayoutBinding = DataBindingUtil.inflate<ConfirmorderProductLayoutBinding>(LayoutInflater.from(mcontext), R.layout.confirmorder_product_layout, parent, false)
        return Holder(productLayoutBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val adapteModel = list[position]
        holder.productLayoutBinding.data = adapteModel
        holder.productLayoutBinding.handler = ConfirmCheckoutHandler(mcontext, holder.productLayoutBinding)
        holder.productLayoutBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(internal var productLayoutBinding: ConfirmorderProductLayoutBinding) : RecyclerView.ViewHolder(productLayoutBinding.root) {
        private val productImage: ImageView
        private val detailLayout: LinearLayout
        private val details: TextView
        private val model: TextView
        private val unitPrice: TextView
        private val optionTv: TextView

        init {
            productImage = productLayoutBinding.orderImg
            details = productLayoutBinding.details
            model = productLayoutBinding.model
            detailLayout = productLayoutBinding.detailsLayout
            unitPrice = productLayoutBinding.unit
            optionTv = productLayoutBinding.optionLayout
            val params = LinearLayout.LayoutParams(Utils.getDeviceScreenWidth()  / 3, Utils.getDeviceScreenWidth()  / 3)
            productImage.layoutParams = params
        }
    }
}
