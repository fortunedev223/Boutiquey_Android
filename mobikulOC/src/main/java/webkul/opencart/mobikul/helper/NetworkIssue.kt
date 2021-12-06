package webkul.opencart.mobikul.helper

import android.content.Context
import webkul.opencart.mobikul.utils.SweetAlertBox

class NetworkIssue {
    companion object {
        fun getNetworkIssue(message: String, mContext: Context) {
            SweetAlertBox.instance.retryNetWorkCall(context = mContext)
        }
    }
}