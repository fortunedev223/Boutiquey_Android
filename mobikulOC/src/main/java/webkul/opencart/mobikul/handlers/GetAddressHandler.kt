package webkul.opencart.mobikul.handlers

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.activity.DashBoard
import webkul.opencart.mobikul.adapterModel.GetAddressAdaperModel
import webkul.opencart.mobikul.AddrBookActivity
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.NewAddressForm
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox



/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class GetAddressHandler(private val mContext: Context) {
    private val REQUEST_CODE_ADDRESS = 1
    private var baseModelCallback: Callback<BaseModel>? = null

    fun onClickEdit(view: View, model: GetAddressAdaperModel) {
        val i = Intent(mContext, NewAddressForm::class.java)
        i.putExtra("addressId", model.addressId)
        i.putExtra("activity_title", mContext.resources.getString(R.string.add_new_add))
        if (mContext is AddrBookActivity) {
            mContext.startActivityForResult(i, REQUEST_CODE_ADDRESS)
        } else if (mContext is DashBoard) {
            mContext.startActivityForResult(i, REQUEST_CODE_ADDRESS)
        }
    }

    fun onClickDelete(view: View, model: GetAddressAdaperModel) {
        baseModelCallback = object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                SweetAlertBox.dissmissSweetAlertBox()
                val alert = AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
                alert.setCancelable(false)
                alert.setNegativeButton(mContext.resources.getString(R.string.dialog_ok)
                ) { dialog, which ->
                    dialog.dismiss()
                    if (mContext is AddrBookActivity) {
                        val i = mContext.intent
                        mContext.finish()
                        mContext.startActivity(i)
                    } else {
                        val i = (mContext as DashBoard).intent
                        mContext.finish()
                        mContext.startActivity(i)
                    }
                }
                alert.setMessage(response.body()!!.message).show()
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

            }
        }

        if (!model.defaultId!!.equals(model.addressId!!, ignoreCase = true)) {
            val alert = AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
            alert.setCancelable(false)
            alert.setNegativeButton(mContext.resources.getString(R.string.cancel))
            { dialog, which -> dialog.dismiss() }
            alert.setPositiveButton(mContext.resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                SweetAlertBox().showProgressDialog(mContext)
                RetrofitCallback.deleteAddressCall(mContext, model.addressId!!, RetrofitCustomCallback(baseModelCallback, mContext))
            }
            alert.setMessage(mContext.resources.getString(R.string.del_your_address_alert)).show()
        } else {
            val alert = AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
            alert.setCancelable(false)
            alert.setPositiveButton(mContext.resources.getString(R.string.dialog_ok)) { dialog, which -> dialog.dismiss() }
            alert.setMessage(R.string.warning_you_can_not_delete_your_default_address_).show()
        }
    }
}
