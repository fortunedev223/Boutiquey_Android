package webkul.opencart.mobikul.handlers

import android.content.Context
import android.view.View

import webkul.opencart.mobikul.adapterModel.ConfirmOrderAdapteModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ConfirmorderProductLayoutBinding

/**
 * Created by manish.choudhary on 31/10/17.
 */

class ConfirmCheckoutHandler(private val mContext: Context, private val productLayoutBinding: ConfirmorderProductLayoutBinding) {

    fun onClickViewAll(view: View, model: ConfirmOrderAdapteModel) {
        if (touch) {
            productLayoutBinding.model.text = mContext.resources.getString(R.string.model) + ": " + model.model
            productLayoutBinding.unit.text = mContext.resources.getString(R.string.unit_price) + ": " + model.unitPrice
            val option = mContext.resources.getString(R.string.select_option) + ": " + if (model.options!!.size != 0) model.options!![0].value else ""
            if (model.options!!.size == 0) {
                productLayoutBinding.optionLayout.visibility = View.GONE
            } else {
                productLayoutBinding.optionLayout.text = option
            }
            productLayoutBinding.detailsLayout.visibility = View.VISIBLE
            touch = false
        } else {
            productLayoutBinding.detailsLayout.visibility = View.GONE
            touch = true
        }
    }

    companion object {
        private var touch = true
    }
}
