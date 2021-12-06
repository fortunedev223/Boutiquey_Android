package webkul.opencart.mobikul.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import org.json.JSONObject
import java.util.ArrayList
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.fragment.DashBoardMyOrders
import webkul.opencart.mobikul.fragment.DashBoardMyReview
import webkul.opencart.mobikul.fragment.DashboardMyAddress
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ActivityDashBoardNewBinding

/**
Webkul Software. *
@Mobikul
@OpencartMobikul
@author Webkul
@copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
@license https://store.webkul.com/license.html
 */
class DashBoard : BaseActivity() {
    private var dashboardBinding: ActivityDashBoardNewBinding? = null
    private var tabLayout: TabLayout? = null
    private var dashboad_pager: ViewPager? = null
    private var fragmentArrayList: ArrayList<Fragment>? = null
    private var titles: Array<String>? = null
    private var dashBoardMyOrders: DashBoardMyOrders? = null
    private var dashBoardMyReview: DashBoardMyReview? = null
    private var dashboardMyAddress: DashboardMyAddress? = null
    private var title: TextView? = null
    private var toolbarDashboard: Toolbar? = null
    private val REQUEST_CODE_ADDRESS = 1


    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    fun checkConn(): Boolean {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        return !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val intent = intent
            this.finish()
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board_new)
        hideSoftKeyboard(dashboardBinding!!.root)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        toolbarDashboard = dashboardBinding!!.toolbar
        val appBarLayout = dashboardBinding!!.appbar
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                if (p1 <= -216) {
                    toolbarDashboard!!.setBackgroundColor(ContextCompat.getColor(this@DashBoard, R.color.light_gray_color1))
                    menu!!.findItem(R.id.action_bell).isVisible = false
                    menu!!.findItem(R.id.action_cart).isVisible = true
                } else {
                    toolbarDashboard!!.setBackgroundColor(ContextCompat.getColor(this@DashBoard, R.color.light_gray_color1))
                    if (menu != null) {
                        menu!!.findItem(R.id.action_bell).isVisible = false
                        menu!!.findItem(R.id.action_cart).isVisible = true
                    }
                }
            }
        })
        setSupportActionBar(toolbarDashboard)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = dashboardBinding!!.toolbar.findViewById<View>(R.id.title) as TextView
        title!!.text = getString(R.string.dashboard_action_title)
        title!!.setTextColor(ContextCompat.getColor(this@DashBoard, R.color.gray))
        if (!checkConn()) {
            showDialog(this)
        } else {
            fragmentArrayList = ArrayList()
            tabLayout = dashboardBinding!!.dashboardTablayout
            dashboad_pager = dashboardBinding!!.dashboardPager
            dashboardMyAddress = DashboardMyAddress()
            dashBoardMyOrders = DashBoardMyOrders()
            dashBoardMyReview = DashBoardMyReview()
            fragmentArrayList!!.add(dashboardMyAddress!!)
            fragmentArrayList!!.add(dashBoardMyOrders!!)
            fragmentArrayList!!.add(dashBoardMyReview!!)
            titles = arrayOf(resources.getString(R.string.myaddress), resources.getString(R.string.myorders), resources.getString(R.string.myreviews))
            dashboad_pager!!.adapter = DashboardPagerAdapter(supportFragmentManager, fragmentArrayList!!, titles!!)
            tabLayout!!.setupWithViewPager(dashboad_pager)
            tabLayout!!.getTabAt(0)!!.setIcon(R.drawable.addressbook_primary_color)
            tabLayout!!.getTabAt(1)!!.setIcon(R.drawable.ic_order_dashboard_before)
            //      tabLayout.getTabAt(2).setIcon(R.drawable.ic_review_dashboard_before);
            tabLayout!!.setTabTextColors(ContextCompat.getColor(this@DashBoard, R.color.light_gray_color1), Color.parseColor("#FFFFFF"))
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {

                    when (tab.position) {
                        0 -> {
                            tab.setIcon(R.drawable.addressbook_primary_color)
                            tabLayout!!.getTabAt(1)!!.setIcon(R.drawable.ic_order_dashboard_before)
                            //                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_review_dashboard_before);
                        }
                        1 -> {
                            tab.setIcon(R.drawable.order_primary_dashboard)
                            tabLayout!!.getTabAt(0)!!.setIcon(R.drawable.ic_address_dashboard_before)
                            //                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_review_dashboard_before);
                        }
                    }//                    case 2: {
                    //                        tab.setIcon(R.drawable.ic_review_dashboard);
                    //                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_address_dashboard_before);
                    //                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_order_dashboard_before);
                    //                    }
                    //                    break;
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    Log.d("Tab", "onTabUnselected: " + tab.position)

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_bell).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    private inner class DashboardPagerAdapter(fm: FragmentManager, internal var fragmentArrayList: ArrayList<Fragment>, internal var titles: Array<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragmentArrayList[position]
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }


    fun deleteAddressResponse(backresult: String) {
        try {
            responseObject = JSONObject(backresult)
            val alert = AlertDialog.Builder(this@DashBoard, R.style.AlertDialogTheme)
            alert.setNegativeButton(resources.getString(android.R.string.ok)
            ) { dialog, which ->
                dialog.dismiss()
                val i = Intent(this@DashBoard, DashBoard::class.java)
                finish()
                startActivity(i)
            }
            alert.setMessage(responseObject.getString("message")).show()
        } catch (e: Exception) {
            Log.d("Alert webkul.opencart", e.toString() + "")
        }

    }

}
