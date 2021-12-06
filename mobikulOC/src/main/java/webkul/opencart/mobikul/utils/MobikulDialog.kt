package webkul.opencart.mobikul.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import webkul.opencart.mobikul.databinding.MobikulDialogBinding
import android.app.Activity


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */


class MobikulDialog private constructor(private val context: Context) : TypeDialog, Build {

    override fun dissmiss() {
        if (mInstance != null && mDialog != null && mDialog?.isShowing!!) {
            mDialog?.dismiss()
            mInstance = null
        }
    }

    override fun setProgress(): Build {
        return this
    }

    override fun setSuccess(): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setError(): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWarning(): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mDialog: Dialog? = null

    override fun build() {
        mDialog = Dialog(context)
        val binding = MobikulDialogBinding.inflate(LayoutInflater.from(context))
        mDialog?.setContentView(binding.root)
        mDialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
//        if ((context as Activity).hasWindowFocus()) {
//            mDialog?.show()
//        }
        mDialog?.show()
    }

    companion object {

        private var mInstance: MobikulDialog? = null

        fun getInstance(context: Context): TypeDialog {
            synchronized(this) {
                if (mInstance == null) {
                    mInstance = MobikulDialog(context)
                }
                return mInstance as MobikulDialog
            }
        }
    }
}

interface Build {
    fun build()
}

interface TypeDialog {
    fun setProgress(): Build
    fun setSuccess(): Build
    fun setError(): Build
    fun setWarning(): Build
    fun dissmiss()
}
