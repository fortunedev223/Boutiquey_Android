package webkul.opencart.mobikul.handlers

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.LinearLayout

import webkul.opencart.mobikul.adapterModel.BottomCheckoutAddressModel
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.fragment.PaymentAddress
import webkul.opencart.mobikul.R

/**
 * Created by manish.choudhary on 17/10/17.
 */

class CheckoutBottomSheetAddressHandler(private val mContext: Context, private val sheetDialog: BottomSheetDialog) {

    fun onClickAddress(view: View, model: BottomCheckoutAddressModel) {
        val background = AppCompatResources.getDrawable(mContext, R.drawable.recyclerview_selector)
        val linearLayout = view as LinearLayout
        linearLayout.background = background
        linearLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.accent_color))
        if (sheetDialog.isShowing) {
            sheetDialog.dismiss()
        }

        if ((mContext as CheckoutActivity).supportFragmentManager.findFragmentByTag(model.fragmentTag) is PaymentAddress) {
            val address = mContext.supportFragmentManager.findFragmentByTag(PaymentAddress::class.java.simpleName) as PaymentAddress
            address.getAddressIdBottomSheet(model.name!!, "", model.address!!, "", model.zip!!, model.addressId!!)
        } else {
            val address = mContext.supportFragmentManager.findFragmentByTag(webkul.opencart.mobikul.fragment.ShippingAddress::class.java.simpleName) as webkul.opencart.mobikul.fragment.ShippingAddress
            address.getAddressIdBottomSheet(model.name!!, "", model.address!!, "", model.zip!!, model.addressId!!)
        }
    }

}
