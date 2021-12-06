package webkul.opencart.mobikul.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.content.res.AppCompatResources
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast

import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.CustomToastBinding


class MakeToast {
    private var inflater: LayoutInflater? = null
    private var toast: Toast? = null
    private var toastBinding: CustomToastBinding? = null
    private var textView: TextView? = null


    fun shortToast(context: Context, msg: String?) {
        inflater = LayoutInflater.from(context)
        if (msg != null) {
            val toastDrawable = AppCompatResources.getDrawable(context, R.drawable.toast_border)
            toastBinding = CustomToastBinding.inflate(inflater!!)
            textView = toastBinding!!.toastTxt
            textView?.text = Html.fromHtml(msg)
            toastBinding?.customToastLayout?.background = toastDrawable
            toast = Toast(context)
            toast?.setGravity(Gravity.BOTTOM, 0, 15)
            toast?.view = toastBinding!!.root
            toast?.duration = Toast.LENGTH_SHORT
            toast?.show()
        }
    }

    fun longToast(context: Context, msg: String) {
        inflater = LayoutInflater.from(context)
        toastBinding = CustomToastBinding.inflate(inflater!!)
        textView = toastBinding!!.toastTxt
        textView?.text = Html.fromHtml(msg)
        toast = Toast(context)
        toast?.setGravity(Gravity.BOTTOM, 0, 15)
        toast?.view = toastBinding!!.root
        toast?.duration = Toast.LENGTH_LONG
        toast?.show()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mGetInstance: MakeToast? = null
        val instance: MakeToast
            get() {
                if (mGetInstance == null) {
                    mGetInstance = MakeToast()
                }
                return mGetInstance!!
            }
    }

}
