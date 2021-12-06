package webkul.opencart.mobikul.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_order_return_view.*
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.OrderRetrunAdapter
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.eventbus.GlobalEventBus
import webkul.opencart.mobikul.model.OrderReturnModel.OrderReturn
import webkul.opencart.mobikul.model.OrderReturnModel.ReturnDatum
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.SealedClass
import webkul.opencart.mobikul.utils.SweetAlertBox

class OrderReturnView : BaseActivity() {
    fun checkConn(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_return_view)
        toolbarLoginActivity = findViewById<ViewGroup>(R.id.toolbar).findViewById<android.widget.Toolbar>(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbarLoginActivity)
        val title: TextView = findViewById<ViewGroup>(R.id.toolbar).findViewById(R.id.title) as TextView
        title.text = (getString(R.string.return_inforation))
        if (!checkConn()) {
            showDialog(this)
        } else {
            makeApiCall()
        }
    }

    private fun makeApiCall() {
        val callback = object : Callback<OrderReturn> {
            override fun onFailure(call: Call<OrderReturn>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<OrderReturn>?, response: Response<OrderReturn>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response!!.body()!!.error != 1) {
                    if (response.body()!!.returnData!!.size != 0) {
                        setData(response.body()!!.returnData!!)
                    } else {
                        error_tv.visibility = View.VISIBLE
                        error_tv.text = resources.getString(R.string.no_return)
                    }
                } else {
                    error_tv.visibility = View.VISIBLE
                    error_tv.text = resources.getString(R.string.no_return)
                }
            }
        }
        SweetAlertBox().showProgressDialog(this)
        RetrofitCallback.orderReturnViewCall(this, RetrofitCustomCallback<OrderReturn>(callback, this))
    }

    override fun onStart() {
        super.onStart()
        GlobalEventBus.eventBus!!.register(this)
    }

    override fun onStop() {
        super.onStop()
        GlobalEventBus.eventBus!!.unregister(this)
    }

    @Subscribe
    fun getMessageEvent(message: String) {
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }


    private fun setData(returnData: List<ReturnDatum>) {
        val recylcerView: RecyclerView = findViewById(R.id.order_return) as RecyclerView
        val adapter: OrderRetrunAdapter
        val list = ArrayList<SealedClass.OrderReview>()
        for (i in returnData.indices) {
            list.add(SealedClass.OrderReview(
                    returnData.get(i).returnId!!,
                    returnData.get(i).orderId!!,
                    returnData.get(i).name!!,
                    returnData.get(i).status!!,
                    returnData.get(i).dateAdded!!))
        }
        adapter = OrderRetrunAdapter(list, this)
        recylcerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recylcerView.adapter = adapter
    }
}
