package webkul.opencart.mobikul.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import cn.pedant.SweetAlert.SweetAlertDialog
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.helper.Utils


class SweetAlertBox {

    fun showProgressDialog(context: Context) {
        val activity = context as Activity
        if (SweetAlertBox.sweetAlertDialog != null && SweetAlertBox.sweetAlertDialog?.isShowing!!) {
            sweetAlertDialog?.dismissWithAnimation()
            sweetAlertDialog = null
        } else {
            sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            sweetAlertDialog?.titleText = context.getResources().getString(R.string.loading)
            sweetAlertDialog?.contentText = context.getResources().getString(R.string.wait_for_a_moment)
            sweetAlertDialog?.setCancelable(false)
            if (!activity.isFinishing) {
                sweetAlertDialog?.show()
            }
        }
    }

    fun showWarningPopUp(context: Context, title: String, message: String, id: String) {
        sweetAlertDialog = SweetAlertDialog(context)
        sweetAlertDialog?.titleText = context.resources.getString(R.string.warning)
        sweetAlertDialog?.changeAlertType(SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog?.confirmText = context.resources.getString(R.string.ok)
        sweetAlertDialog?.cancelText = context.resources.getString(R.string.cancel)
        sweetAlertDialog?.setCancelClickListener { sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation() }
        sweetAlertDialog?.setConfirmClickListener { sweetAlertDialog ->
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra("productId", id)
            context.startActivity(intent)
            sweetAlertDialog.dismissWithAnimation()
        }
        sweetAlertDialog?.contentText = message
        sweetAlertDialog?.show()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.getDeviceScreenWidth() / 7)
        params.setMargins(8, 8, 8, 8)
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.setPadding(0, 0, 0, 0)
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.setPadding(0, 0, 0, 0)
    }


    fun showPopUp(context: Activity, title: String, message: String, headerTitle: String, REDIRECT: Boolean) {
        sweetAlertDialog = SweetAlertDialog(context)
        sweetAlertDialog?.titleText = headerTitle
        sweetAlertDialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
        sweetAlertDialog?.confirmText = title
        sweetAlertDialog?.showCancelButton(false)
        sweetAlertDialog?.setConfirmClickListener { sweetAlertDialog ->
            if (REDIRECT) {
                val viewOrder = Intent(context, CheckoutActivity::class.java)
                context.startActivity(viewOrder)
                context.finish()
            } else {
                val viewOrder = Intent(context, MainActivity::class.java)
                viewOrder.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                context.startActivity(viewOrder)
                context.finish()
            }
            sweetAlertDialog.dismissWithAnimation()
        }
        sweetAlertDialog?.contentText = message
        sweetAlertDialog?.show()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.getDeviceScreenWidth() / 7)
        params.setMargins(8, 8, 8, 8)
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.setPadding(0, 0, 0, 0)
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.setPadding(0, 0, 0, 0)
    }

    fun showWarningWishlistPopUp(context: Context, title: String, message: String) {
        sweetAlertDialog = SweetAlertDialog(context)
        sweetAlertDialog?.titleText = context.resources.getString(R.string.warning)
        sweetAlertDialog?.changeAlertType(SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog?.confirmText = context.getString(R.string.ok)
        sweetAlertDialog?.setCancelable(false)
        sweetAlertDialog?.cancelText = context.resources.getString(R.string.cancel)
//        sweetAlertDialog?.getButton(-1)?.setPadding(0, 0, 0, 12)
        sweetAlertDialog?.setCancelClickListener { sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation() }
        sweetAlertDialog?.setConfirmClickListener { sweetAlertDialog ->
            val intent = Intent(context, LoginActivity::class.java)
            if (context is MyWishlistActivity) {
                context.finish()
            }
            context.startActivity(intent)
            sweetAlertDialog.dismissWithAnimation()
        }
        sweetAlertDialog?.contentText = message
        sweetAlertDialog?.show()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.getDeviceScreenWidth() / 7)
        params.setMargins(8, 8, 8, 8)
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.setPadding(0, 0, 0, 0)
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.setPadding(0, 0, 0, 0)

    }

    fun showWarningPopUpPaymentMethod(context: Context, title: String, message: String) {
        sweetAlertDialog = SweetAlertDialog(context)
        sweetAlertDialog?.titleText = context.resources.getString(R.string.warning)
        sweetAlertDialog?.changeAlertType(SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog?.confirmText = context.getString(R.string.ok)
        sweetAlertDialog?.setConfirmClickListener { sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation() }
        sweetAlertDialog?.setCancelable(false)
        sweetAlertDialog?.contentText = message
        sweetAlertDialog?.show()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.getDeviceScreenWidth() / 7)
        params.setMargins(8, 8, 8, 8)
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.setPadding(0, 0, 0, 0)
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.setPadding(0, 0, 0, 0)
    }

    fun showErrorPopUp(context: Context, title: String, message: String?) {
        sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
        sweetAlertDialog?.setTitleText(title)
                ?.setContentText(message)
                ?.setConfirmText(context.getString(R.string.ok))
                ?.show()
    }

    fun showAreYouSurePopUp(context: Context, title: String, message: String) {
        sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog?.setTitleText(title)
                ?.setContentText(message)
                ?.setCancelText(context.getString(R.string.no))
                ?.setConfirmText(context.getString(R.string.yes))
                ?.show()
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, Utils.getDeviceScreenWidth() / 7)
        params.setMargins(8, 8, 8, 8)
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.layoutParams = params
        sweetAlertDialog?.findViewById<Button>(R.id.confirm_button)?.setPadding(0, 0, 0, 0)
        sweetAlertDialog?.findViewById<Button>(R.id.cancel_button)?.setPadding(0, 0, 0, 0)
    }

    fun retryNetWorkCall(context: Context) {
        if (sweetAlertDialog == null) {
            sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
            sweetAlertDialog?.setTitleText(context.getString(R.string.retry))
                    ?.setConfirmText(context.getString(R.string.ok))
                    ?.setContentText(context.getString(R.string.retry))
                    ?.show()

            sweetAlertDialog?.setConfirmClickListener { sweetAlertDialog ->
                val intent = (context as Activity).intent
                context.finish()
                context.startActivity(intent)
                sweetAlertDialog.dismissWithAnimation()
            }
        }
    }

    fun getSweetAlertDialog(): SweetAlertDialog? {
        return sweetAlertDialog
    }

    companion object {
        private var mInstance: SweetAlertBox? = null
        var sweetAlertDialog: SweetAlertDialog? = null
        val instance: SweetAlertBox
            get() {
                if (mInstance == null) {
                    mInstance = SweetAlertBox()
                }
                return mInstance!!
            }

        fun dissmissSweetAlertBox() {
            if (SweetAlertBox.sweetAlertDialog != null && SweetAlertBox.sweetAlertDialog?.isShowing!!) {
                sweetAlertDialog?.dismissWithAnimation()
                sweetAlertDialog = null
            }
        }
    }
}
