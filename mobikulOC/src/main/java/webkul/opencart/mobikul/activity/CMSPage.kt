package webkul.opencart.mobikul.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ActivityCmspageBinding
import webkul.opencart.mobikul.model.CmsPageModel.CmsPage
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox

class CMSPage : AppCompatActivity() {

    private lateinit var mBinding: ActivityCmspageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cmspage)
        val toolbar = mBinding.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val title = mBinding.toolbar?.findViewById<View>(R.id.title) as TextView
        title.text = intent.getStringExtra("title")
        makeApiCall(intent.getStringExtra("id"))
    }

    private fun makeApiCall(information_id: String?) {
        val callback = object : Callback<CmsPage> {
            override fun onFailure(call: Call<CmsPage>?, t: Throwable?) {
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onResponse(call: Call<CmsPage>?, response: Response<CmsPage>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response?.body()?.error != 1) {
                    mBinding.webView.loadData(response?.body()?.description!!, "text/html; charset=UTF-8", null)
                }
            }
        }
        SweetAlertBox.instance.showProgressDialog(this)
        RetrofitCallback.getInfo(
                this,
                information_id!!,
                RetrofitCustomCallback(callback, this))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
