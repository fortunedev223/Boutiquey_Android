package webkul.opencart.mobikul

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel

import java.util.ArrayList

import webkul.opencart.mobikul.databinding.ActivitySubcategoryBinding
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.SubcategoryModel.Category
import webkul.opencart.mobikul.model.SubcategoryModel.SubCategoryModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.utils.SweetAlertBox

class Subcategory : BaseActivity() {
    lateinit var subcategoryBinding: ActivitySubcategoryBinding
    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null
    private var title: TextView? = null
    var categories: ArrayList<Category>? = ArrayList()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            super.onBackPressed()
        }
    }

    override fun isOnline() {
        val conMgr = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo
        isInternetAvailable = !(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_bell).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isOnline()
        subcategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_subcategory)
        toolbar = subcategoryBinding.toolbar?.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        this.setTitle(R.string.app_name)
        title = subcategoryBinding.toolbar?.findViewById(R.id.title)
        title?.typeface = null
        if (intent.extras != null) {
            title?.text = intent.extras?.getString("CATEGORY_NAME")
        }
        subcategoryBinding.catImage.layoutParams = LinearLayout.LayoutParams(
                Utils.getDeviceScreenWidth(),
                Utils.getDeviceScreenWidth() / 2)
        subcategoryBinding.catImage.setBackgroundColor(Color.parseColor(intent.getStringExtra("dominant")))
        Log.d("DominantColor", "=======>" + intent.getStringExtra("dominant"))
        Glide.with(this)
                .load(intent.getStringExtra("image"))
                .into(subcategoryBinding.catImage)
        makeApiCall()
    }

    private fun makeApiCall() {
        SweetAlertBox.instance.showProgressDialog(this)
        RetrofitCallback.getSubCategory(this, intent.getStringExtra("id"),
                object : Callback<SubCategoryModel> {
                    override fun onFailure(call: Call<SubCategoryModel>?, t: Throwable?) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        Log.d("Navdrawer", t.toString())
                    }

                    override fun onResponse(call: Call<SubCategoryModel>?, response: Response<SubCategoryModel>?) {
                        SweetAlertBox.dissmissSweetAlertBox()
                        if (response?.body()?.error != 1 &&
                                response?.body()?.categories?.size != 0) {
                            val cat = Category()
                            cat.childStatus = false
                            cat.name = getString(R.string.view_all) + " " + intent.extras?.getString("CATEGORY_NAME")
                            cat.dominantColor = intent.getStringExtra("dominant")
                            cat.path = intent.getStringExtra("id")
                            cat.icon = intent.getStringExtra("image")
                            cat.image = intent.getStringExtra("image")
                            categories?.add(cat)
                            categories?.addAll(response?.body()?.categories!!)
                            supportFragmentManager.beginTransaction()
                                    .add(subcategoryBinding.container.id,
                                            SubcateryFragment.newInstance(categories!!))
                                    .commit()
                        }
                    }
                })
    }

}