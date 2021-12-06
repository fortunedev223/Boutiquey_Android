package webkul.opencart.mobikul

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Point
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_my_wishlist.*
import org.json.JSONObject
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.GetWishlistAdapter
import webkul.opencart.mobikul.adapterModel.GetWishlistAdapterModel
import webkul.opencart.mobikul.model.GetWishlist.GetWishlist
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.analytics.MobikulApplication
import webkul.opencart.mobikul.databinding.ActivityMyWishlistBinding


class MyWishlistActivity : BaseActivity() {
    internal lateinit var spinner: ProgressBar
    internal var screen_width: Int = 0
    internal var actionBar: ActionBar? = null
    var wishlistBinding: ActivityMyWishlistBinding? = null
        private set
    private var getWishlistCallback: Callback<GetWishlist>? = null
    private var wishlistRecyclerview: RecyclerView? = null
    private var wishlistadapter: GetWishlistAdapter? = null
    private var title: TextView? = null

    override fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onResume() {
        if (itemCart != null) {
            val customerDataShared = getSharedPreferences("customerData", Context.MODE_PRIVATE)
            val icon = itemCart!!.icon as LayerDrawable
            Utils.setBadgeCount(this, icon, customerDataShared.getString("cartItems", "0"))
        }
        super.onResume()
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnline()
        if (!isInternetAvailable) {
            showDialog(this)
        } else {
            wishlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_wishlist)
            mMobikulApplication = application as MobikulApplication
            toolbarLoginActivity = wishlistBinding!!.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbarLoginActivity)
            title = wishlistBinding!!.toolbar!!.findViewById<View>(R.id.title) as TextView
            title!!.text = getString(R.string.mywishlist_action_title)
            actionBar = supportActionBar
            if (actionBar != null) {
                actionBar!!.setDisplayHomeAsUpEnabled(true)
            }

            val shared = getSharedPreferences("customerData", Context.MODE_PRIVATE)
            val isLoggedIn = shared.getBoolean("isLoggedIn", false)
            if (!isLoggedIn) {
                SweetAlertBox().showWarningWishlistPopUp(this@MyWishlistActivity, "", resources.getString(R.string.guest_wishlist_msg))
            } else {
                getWishlistCallback = object : Callback<GetWishlist> {
                    override fun onResponse(call: Call<GetWishlist>, response: Response<GetWishlist>) {
                        setWishlistData(response.body())
                    }

                    override fun onFailure(call: Call<GetWishlist>, t: Throwable) {}
                }
                RetrofitCallback.getWishlistCall(this@MyWishlistActivity, RetrofitCustomCallback(getWishlistCallback, this@MyWishlistActivity))
                AppSharedPreference.updateWishlistStatus(this, true)
                val mywishlistContainer = wishlistBinding!!.mywishlistContainer
                mywishlistContainer.visibility = View.GONE
                val display = windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                screen_width = size.x
                movetohomepage.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            }
        }
    }

    private fun setWishlistData(wishlistData: GetWishlist?) {
        if (wishlistData?.wishlistData != null) {
            if (wishlistData.wishlistData!!.isNotEmpty()) {
                val list = ArrayList<GetWishlistAdapterModel>()
                wishlistRecyclerview = wishlistBinding!!.wishlistRecyclerview
                for (i in 0 until wishlistData.wishlistData!!.size) {
                    list.add(GetWishlistAdapterModel(
                            wishlistData.wishlistData!![i].productId,
                            wishlistData.wishlistData!![i].name!!,
                            wishlistData.wishlistData!![i].thumb,
                            wishlistData.wishlistData!![i].price!!,
                            wishlistData.wishlistData!![i].formattedSpecial,
                            wishlistData.wishlistData!![i].special,
                            wishlistData.wishlistData!![i].stock,
                            wishlistData.wishlistData!![i].hasOption!!,
                            wishlistData.wishlistData!![i].dominantColor))
                }
                wishlistBinding?.mywishlistContainer?.visibility = View.VISIBLE
                wishlistadapter = GetWishlistAdapter(this@MyWishlistActivity, list)
                wishlistRecyclerview?.adapter = wishlistadapter
                wishlistRecyclerview?.layoutManager = LinearLayoutManager(this@MyWishlistActivity,
                        LinearLayoutManager.VERTICAL, false)
                spinner = wishlistBinding!!.mywishlistprogress
                spinner.visibility = View.GONE
            }
        } else {
            wishlistBinding?.scrollView?.visibility = View.GONE
            wishlistBinding?.emptyLayout?.visibility = View.VISIBLE
            wishlistBinding?.emptyText?.text = wishlistData?.message
            wishlistBinding?.emptyText?.visibility = View.VISIBLE
        }
        spinner = wishlistBinding?.mywishlistprogress!!
        spinner.visibility = View.GONE
    }

    fun updateWishlist() {
        spinner.visibility = View.VISIBLE
        RetrofitCallback.getWishlistCall(this@MyWishlistActivity, RetrofitCustomCallback(getWishlistCallback, this@MyWishlistActivity))
    }

}
