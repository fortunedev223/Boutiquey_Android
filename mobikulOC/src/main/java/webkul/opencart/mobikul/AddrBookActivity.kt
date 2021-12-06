package webkul.opencart.mobikul

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.databinding.DataBindingUtil
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_addr_book.*

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.GetAddressAdapter
import webkul.opencart.mobikul.adapterModel.GetAddressAdaperModel
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.model.GetAddressModel.GetAddress
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.ActivityAddrBookBinding

class AddrBookActivity : BaseActivity() {
    var responseAddrBook: Any? = null
    private var spinner: ProgressBar? = null
    internal var REQUEST_CODE_ADDRESS = 1
    private val progress: ProgressDialog? = null
    internal lateinit var addrbookdataContainer: LinearLayout
    private val addressId: String? = null
    internal var margin: Int = 0
    internal var editorAddrBookActivity: Editor? = null
    internal var defaultAddress: String? = null
    internal lateinit var layoutParams: LinearLayout.LayoutParams
    var configSharedAddrBookActivity: SharedPreferences? = null
    private var isInternetAvailableAddressBook: Boolean = false
    private var sharedAddrBook: SharedPreferences? = null
    private var Container: LinearLayout? = null
    private var addrBookBinding: ActivityAddrBookBinding? = null
    private var title: TextView? = null
    private val newAddressReturn = 201
    private var getAddressCallback: Callback<GetAddress>? = null
    private var addressLayout: RecyclerView? = null
    private var adapter: GetAddressAdapter? = null
    private var list: ArrayList<GetAddressAdaperModel>? = null

    override fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailableAddressBook = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(newAddressReturn)
    }

    override fun onResume() {
        if (itemCart != null) {
            val customerDataShared = getSharedPreferences(Constant.CUSTOMER_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val icon = itemCart!!.icon as LayerDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"))
        }
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnline()
        if (!isInternetAvailableAddressBook) {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }//No button clicked
            }
            showDialog(this)
        } else {
            addrBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_addr_book)
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                addrBookBinding!!.shadowView.visibility = View.GONE
            }
            toolbarLoginActivity = addrBookBinding!!.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbarLoginActivity)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            title = addrBookBinding?.toolbar?.findViewById<View>(R.id.title) as TextView
            title?.text = getString(R.string.addrbook_action_title)
            addressLayout = addrBookBinding?.addressLayout
            addressLayout?.isNestedScrollingEnabled = false
            Container = addrBookBinding?.addrbookContainer!!
            spinner = addrBookBinding?.addrbookprogress!!
            Container?.visibility = View.GONE
            margin = (10 * resources.displayMetrics.density).toInt()
            addrbookdataContainer = addrBookBinding?.addrbookContainer!!
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            addrbookdataContainer.layoutParams = layoutParams
            sharedAddrBook = getSharedPreferences("customerData", Context.MODE_PRIVATE)
            val isLoggedIn = sharedAddrBook!!.getBoolean("isLoggedIn", false)
            if (!isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)
            }
            list = ArrayList()
            getAddressCallback = object : Callback<GetAddress> {
                override fun onResponse(call: Call<GetAddress>, response: Response<GetAddress>) {
                    if (response.body()!!.error != 1) {
                        spinner?.visibility = View.GONE
                        Container?.visibility = View.VISIBLE
                        addrBookBinding?.addressLayout?.removeAllViews()
                        if (list!!.size != 0) {
                            list!!.clear()
                        }
                        if (response.body()?.addressData?.size == 0) {
                            error_tv?.visibility = View.VISIBLE
                        } else {
                            error_tv?.visibility = View.GONE
                        }
                        for (i in 0 until response.body()?.addressData!!.size) {
                            list?.add(GetAddressAdaperModel(
                                    response.body()!!.addressData!![i].addressId,
                                    response.body()!!.addressData!![i].value!!,
                                    response.body()!!.default))
                            adapter = GetAddressAdapter(this@AddrBookActivity, list!!)
                            addressLayout?.layoutManager = LinearLayoutManager(this@AddrBookActivity, LinearLayoutManager.VERTICAL, false)
                            addressLayout?.adapter = adapter
                        }
                    } else if (response.body()?.error!! == 1) {
                        spinner?.visibility = View.GONE
                        error_tv?.visibility = View.VISIBLE
                        error_tv?.text = response.body()?.message
                    } else {
                        spinner?.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<GetAddress>, t: Throwable) {

                }
            }
            RetrofitCallback.getAddressCall(this@AddrBookActivity, RetrofitCustomCallback(getAddressCallback, this@AddrBookActivity))
        }
    }

    fun addNewAddress(v: View) {
        val i = Intent(this@AddrBookActivity, NewAddressForm::class.java)
        i.putExtra("activity_title", resources.getString(R.string.add_new_add))
        startActivityForResult(i, REQUEST_CODE_ADDRESS)
    }

    fun changeAddress(v: View) {
        val addressId = v.tag.toString()
        val i = Intent(this@AddrBookActivity, NewAddressForm::class.java)
        i.putExtra("addressId", addressId)
        i.putExtra("activity_title", resources.getString(R.string.add_new_add))
        startActivityForResult(i, REQUEST_CODE_ADDRESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_ADDRESS && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK)
            RetrofitCallback.getAddressCall(this@AddrBookActivity, RetrofitCustomCallback(getAddressCallback, this@AddrBookActivity))
        }
    }
}
