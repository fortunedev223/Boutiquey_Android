package webkul.opencart.mobikul.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapter.BrandsAdapter
import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ActivityBrowerByBrandsBinding

class BrowerByBrands : BaseActivity() {
    private var brandsBinding: ActivityBrowerByBrandsBinding? = null
    private var mrecyclerview: RecyclerView? = null
    private var list: ArrayList<CarousalAdapterModel>? = null
    private var adapter: BrandsAdapter? = null
    private var title: TextView? = null

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fadeout, R.anim.nothing)
        this.finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        brandsBinding = DataBindingUtil.setContentView(this, R.layout.activity_brower_by_brands)
        mrecyclerview = brandsBinding!!.brandsRecyclerview
        toolbarLoginActivity = brandsBinding!!.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbarLoginActivity)
        title = brandsBinding!!.toolbar!!.findViewById<View>(R.id.title) as TextView?
        setTitle(getString(R.string.browse_by_brands))
        if (intent.hasExtra("data")) {
            list = intent.getParcelableArrayListExtra("data")
        }
        title!!.text = resources.getString(R.string.browse_by_brands)
        adapter = BrandsAdapter(this@BrowerByBrands, list!!)
        mrecyclerview!!.adapter = adapter
        mrecyclerview!!.layoutManager = GridLayoutManager(this@BrowerByBrands, 2)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_bell).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }
}
