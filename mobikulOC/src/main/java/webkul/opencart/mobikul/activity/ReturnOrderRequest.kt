package webkul.opencart.mobikul.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.handlers.ReturnOrderRequestHandler
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest
import webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnReason
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.ActivityReturnOrderRequestBinding
import java.text.SimpleDateFormat
import java.util.*

class ReturnOrderRequest : BaseActivity() {
    var title: TextView? = null
    lateinit var returnOrderBinding: ActivityReturnOrderRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        returnOrderBinding = DataBindingUtil.setContentView(this, R.layout.activity_return_order_request)
        val toolbarReturnOrder: Toolbar = returnOrderBinding.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
        title = toolbarReturnOrder.findViewById(R.id.title)
        title!!.text = resources.getString(R.string.prod_return)
        setSupportActionBar(toolbarReturnOrder)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        returnOrderBinding.selectDate.setOnClickListener {
            setDateData()
        }
        if (!checkConn()) {
            showDialog(this)
        } else {
            makeApiCall(intent)
        }
    }

    fun checkConn(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    private fun setDateData() {
        val myCalendar = Calendar.getInstance()
        val mDate = DatePickerDialog(this, R.style.AlertDialogTheme, null, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        val date1 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            returnOrderBinding.dateAdded.text = sdf.format(myCalendar.time)
        }

        mDate.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                val datePicker = mDate
                        .datePicker
                date1.onDateSet(datePicker,
                        datePicker.year,
                        datePicker.month,
                        datePicker.dayOfMonth)
            }
        }

        mDate.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel()
            }
        }

        mDate.show()
        mDate.setCancelable(true)
        mDate.setCanceledOnTouchOutside(true)
    }

    private fun makeApiCall(intent: Intent) {
        val callback = object : Callback<webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest> {
            override fun onFailure(call: Call<webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest>?, t: Throwable?) {
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onResponse(call: Call<webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest>?,
                                    response: Response<webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response!!.body()!!.error != 1) {
                    setData(response.body())
                }
            }
        }

        SweetAlertBox().showProgressDialog(this)
        RetrofitCallback.returnDataRequest(this,
                intent.getStringExtra(Constant.ORDER_ID),
                intent.getStringExtra(Constant.PRODUCT_ID),
                RetrofitCustomCallback(callback, this));

    }

    private fun setData(body: ReturnOrderRequest?) {
        returnOrderBinding.data = body
        returnOrderBinding.handler = ReturnOrderRequestHandler(this, returnOrderBinding)
        if (body!!.returnReasons!!.size != 0) {
            setReasonSpinnerData(body.returnReasons)
        }
    }

    private fun setReasonSpinnerData(returnReasons: List<ReturnReason>?) {
        val spinner: Spinner = returnOrderBinding.reason
        val list = ArrayList<String>()
        for (i in returnReasons!!.indices) {
            list.add(returnReasons.get(i).name!!)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner.setTag(returnReasons.get(p2).returnReasonId)
            }
        }
    }
}
