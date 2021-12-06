package webkul.opencart.mobikul.adapterModel

import android.content.Context

import webkul.opencart.mobikul.model.ConfirmModel.Option
import webkul.opencart.mobikul.R


class ConfirmOrderAdapteModel(var context: Context?, var productImageUrl: String?, var productName: String?,
                              productQty: String, productSubtotal: String, var options: List<Option>?, var model: String?, var unitPrice: String?) {
    var productQty: String? = null
        get() = context!!.resources.getString(R.string.qty) + " " + field
    var productSubtotal: String? = null
        get() = context!!.resources.getString(R.string.subtotal) + " " + field

    init {
        this.productQty = productQty
        this.productSubtotal = productSubtotal

    }
}
