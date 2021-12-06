package webkul.opencart.mobikul.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.eventbus.GlobalEventBus
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.OrderReturnInfoModel.ReturnInfo
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox

class ReturnInfo : BaseActivity() {
    lateinit var returnId: String
    fun checkConn(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_info)
        toolbarLoginActivity = findViewById<ViewGroup>(R.id.toolbar).findViewById<Toolbar>(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbarLoginActivity)
        val title: TextView = findViewById<ViewGroup>(R.id.toolbar).findViewById(R.id.title) as TextView
        title.text = (getString(R.string.return_inforation))
        if (!checkConn()) {
            showDialog(this)
        } else {
            if (intent.hasExtra(Constant.RETURN_ID)) {
                makeApiCall(intent.getStringExtra(Constant.RETURN_ID))
            }
        }
    }

    @Subscribe
    fun getMessageEvent(message: String) {
        returnId = message
    }

    override fun onStart() {
        super.onStart()
        GlobalEventBus.eventBus!!.register(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalEventBus.eventBus!!.unregister(this)
    }

    private fun makeApiCall(returnId: String) {
        val callback = object : Callback<ReturnInfo> {
            override fun onFailure(call: Call<ReturnInfo>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ReturnInfo>?, response: Response<ReturnInfo>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                setData(response!!.body())
            }
        }
        SweetAlertBox().showProgressDialog(this)
        RetrofitCallback.returnInfoCall(this, returnId, RetrofitCustomCallback<ReturnInfo>(callback, this))
    }


    private fun setData(body: ReturnInfo?) {
        val returnIdTextView: TextView = findViewById(R.id.return_id) as TextView
        val orderId: TextView = findViewById(R.id.order_id) as TextView
        val dateAdded: TextView = findViewById(R.id.date_added) as TextView
        val orderDate: TextView = findViewById(R.id.order_date) as TextView
        val name: TextView = findViewById(R.id.product_name) as TextView
        val model: TextView = findViewById(R.id.model) as TextView
        val quantity: TextView = findViewById(R.id.quantity) as TextView
        val reason: TextView = findViewById(R.id.reason) as TextView
        val opened: TextView = findViewById(R.id.opened) as TextView
        val comment: TextView = findViewById(R.id.comment) as TextView
        returnIdTextView.text = body!!.returnId
        orderId.text = body.orderId
        dateAdded.text = body.dateAdded
        orderDate.text = body.dateOrdered
        name.text = body.product
        model.text = body.model
        quantity.text = body.quantity
        reason.text = body.reason
        opened.text = body.opened
        if (!body.comment.equals("")) {
            comment.text = body.comment
        } else {
            comment.text = getString(R.string.no_comment_review)
        }
    }
}
