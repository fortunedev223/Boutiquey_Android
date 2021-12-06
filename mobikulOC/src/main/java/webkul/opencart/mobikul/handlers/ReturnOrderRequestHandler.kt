package webkul.opencart.mobikul.handlers

import android.app.Activity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.utils.Validation
import webkul.opencart.mobikul.databinding.ActivityReturnOrderRequestBinding

/**
 * Created by manish.choudhary on 12/6/18.
 */
class ReturnOrderRequestHandler(val mContext: Activity, val returnOrderBinding: ActivityReturnOrderRequestBinding) {

    fun onClickSubmit() {
        val firstName: String = returnOrderBinding.fname.text.toString()
        val lastName: String = returnOrderBinding.lname.text.toString()
        val email: String = returnOrderBinding.email.text.toString()
        val telephone: String = returnOrderBinding.telephone.text.toString()
        val orderId: String = returnOrderBinding.orderId.text.toString()
        val dateOrder: String = returnOrderBinding.dateAdded.text.toString()
        val productName: String = returnOrderBinding.productName.text.toString()
        val model: String = returnOrderBinding.productCode.text.toString()
        val quantity: String = returnOrderBinding.productQty.text.toString()
        val returnReasonId: String = returnOrderBinding.reason.getTag().toString()
        val opened: String = if (returnOrderBinding.yes.isChecked) {
            "1"
        } else {
            "0"
        }
        val comment: String = returnOrderBinding.comment.text.toString()
        if (firstName == "") {
            returnOrderBinding.fname.requestFocus()
            returnOrderBinding.fname.setError(mContext.getString(R.string.fname_is_required))
        } else if (lastName == "") {
            returnOrderBinding.lname.requestFocus()
            returnOrderBinding.lname.setError(mContext.getString(R.string.lname_is_required))
        } else if (email == "") {
            returnOrderBinding.email.requestFocus()
            returnOrderBinding.email.setError(mContext.getString(R.string.email_is_required))
        } else if (!Validation.isEmailValid(email)) {
            returnOrderBinding.email.requestFocus()
            returnOrderBinding.email.setError(mContext.getString(R.string.enter_valid_email))
        } else {
            val callback = object : Callback<BaseModel> {
                override fun onFailure(call: Call<BaseModel>?, t: Throwable?) {
                    SweetAlertBox.dissmissSweetAlertBox()
                }

                override fun onResponse(call: Call<BaseModel>?, response: Response<BaseModel>?) {
                    SweetAlertBox.dissmissSweetAlertBox()
                    if (response!!.body()!!.error != 1) {
//                        mContext as ReturnOrderRequest
                        SweetAlertBox().showPopUp(mContext, "Continue", response.body()!!.message, mContext.resources.getString(R.string.prod_return), false)
//                        mContext.finish()
                    }
                }
            }
            SweetAlertBox().showProgressDialog(mContext)
            RetrofitCallback.addReturnDataRequest(mContext, firstName, lastName, email, telephone, orderId,
                    dateOrder, productName, model, quantity, returnReasonId, opened, comment, RetrofitCustomCallback(callback, mContext))

        }

    }

}