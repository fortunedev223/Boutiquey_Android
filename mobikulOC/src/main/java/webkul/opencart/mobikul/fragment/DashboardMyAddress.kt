package webkul.opencart.mobikul.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import org.json.JSONObject

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.GetAddressAdapter
import webkul.opencart.mobikul.adapterModel.GetAddressAdaperModel
import webkul.opencart.mobikul.NewAddressForm
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.FragmentDashboardMyAddressBinding


class DashboardMyAddress : Fragment() {
    private var addressBinding: FragmentDashboardMyAddressBinding? = null
    private var Container: LinearLayout? = null
    private var spinner: ProgressBar? = null
    private var margin: Int = 0
    private var layoutParams: LinearLayout.LayoutParams? = null
    private var jo: JSONObject? = null
    private val responseObject: JSONObject? = null
    private val defaultAddress: String? = null
    private val addressId: String? = null
    internal var REQUEST_CODE_ADDRESS = 1
    private var linearLayout: LinearLayout? = null
    private var getAddressCallback: Callback<webkul.opencart.mobikul.model.GetAddressModel.GetAddress>? = null
    private var addressLayout: RecyclerView? = null
    private var adapter: GetAddressAdapter? = null
    private var list: ArrayList<GetAddressAdaperModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addressBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard_my_address, container, false)
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            addressBinding!!.shadowView.visibility = View.GONE
        }
        Container = addressBinding!!.addrbookContainer
        spinner = addressBinding!!.addrbookprogress
        linearLayout = addressBinding!!.newAddress
        linearLayout!!.setOnClickListener {
            val i = Intent(activity, NewAddressForm::class.java)
            i.putExtra("activity_title", resources.getString(R.string.add_new_add))
            startActivityForResult(i, REQUEST_CODE_ADDRESS)
        }

        Container!!.visibility = View.GONE
        margin = (10 * resources.displayMetrics.density).toInt()
        layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        jo = JSONObject()
        addressLayout = addressBinding!!.addressLayout
        addressLayout!!.isNestedScrollingEnabled = false
        list = ArrayList()
        getAddressCallback = object : Callback<webkul.opencart.mobikul.model.GetAddressModel.GetAddress> {
            override fun onResponse(call: Call<webkul.opencart.mobikul.model.GetAddressModel.GetAddress>, response: Response<webkul.opencart.mobikul.model.GetAddressModel.GetAddress>) {
                spinner!!.visibility = View.GONE
                Container!!.visibility = View.VISIBLE
                addressBinding!!.addressLayout.removeAllViews()
                if (list!!.size != 0) {
                    list!!.clear()
                }
                if (response.body()!!.addressData != null) {
                    for (i in 0 until response.body()!!.addressData!!.size) {
                        list!!.add(GetAddressAdaperModel(response.body()!!.addressData!![i].addressId,
                                response.body()!!.addressData!![i].value!!,
                                response.body()!!.default))
                        if (isAdded)
                            adapter = GetAddressAdapter(activity!!, list!!)
                        addressLayout!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        addressLayout!!.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<webkul.opencart.mobikul.model.GetAddressModel.GetAddress>, t: Throwable) {

            }
        }
        RetrofitCallback.getAddressCall(activity!!, RetrofitCustomCallback(getAddressCallback, activity))
        return addressBinding!!.root
    }


}// Required empty public constructor


