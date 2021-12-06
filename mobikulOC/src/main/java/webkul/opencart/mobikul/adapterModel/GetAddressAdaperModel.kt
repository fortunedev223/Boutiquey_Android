package webkul.opencart.mobikul.adapterModel

import android.text.Html

/**
 * Created by manish.choudhary on 17/10/17.
 */

class GetAddressAdaperModel(var addressId: String?, value: String, var defaultId: String?) {
    var value: String? = null
        get() = Html.fromHtml(field).toString()

    init {
        this.value = value
    }
}
