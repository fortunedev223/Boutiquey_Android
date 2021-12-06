package webkul.opencart.mobikul.activity

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.ScrollView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.adapter.ReviewListAdapter
import webkul.opencart.mobikul.databinding.ActivityReviewListBinding
import webkul.opencart.mobikul.helper.setLinearLayoutManager
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.ReviewListModel.Review
import webkul.opencart.mobikul.model.ReviewListModel.ReviewList
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox

class Review_List : AppCompatActivity() {
    private lateinit var mBinding: ActivityReviewListBinding
    var page: Int = 1
    var limit: Int = 10
    var totalReview: Int = 0
    var currentTotal: Int = 0
    var loading: Boolean = false
    var mAdapter: ReviewListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_review__list)
        val toolbar = mBinding.toolbar!!.findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val title = mBinding.toolbar?.findViewById<View>(R.id.title) as TextView
        title.text = intent.getStringExtra("title")
        makeApiCall()
    }

    private fun makeApiCall() {
        val callback = object : Callback<ReviewList> {
            override fun onFailure(call: Call<ReviewList>?, t: Throwable?) {
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onResponse(call: Call<ReviewList>?, response: Response<ReviewList>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response?.body()?.error != 1 && response?.body()?.reviews?.size!! != 0) {
                    totalReview = response.body()?.total?.toInt()!!
                    currentTotal = response.body()?.reviews?.size!!
                    setReview(response.body()?.reviews!!)
                }
            }
        }
        SweetAlertBox.instance.showProgressDialog(this)
        RetrofitCallback.getReviewList(this,
                intent.getStringExtra("id"),
                page.toString(),
                limit.toString(),
                RetrofitCustomCallback(callback, this))
    }

    private fun setReview(reviews: ArrayList<Review>) {
        mAdapter = ReviewListAdapter(this, reviews)
        mBinding.recyclerview.adapter = mAdapter
        mBinding.recyclerview.setLinearLayoutManager(this, LinearLayoutManager.VERTICAL)
        mBinding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastCompletelyVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastCompletelyVisibleItemPosition == totalReview - 1 && currentTotal < totalReview) {
                    if (!loading) {
                        page++
                        loading = true
                        makeLazyCall()
                    }
                }
            }
        })
    }

    private fun makeLazyCall() {
        val callback = object : Callback<ReviewList> {
            override fun onFailure(call: Call<ReviewList>?, t: Throwable?) {
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onResponse(call: Call<ReviewList>?, response: Response<ReviewList>?) {
                SweetAlertBox.dissmissSweetAlertBox()
                if (response?.body()?.error != 1 && response?.body()?.reviews?.size!! != 0) {
                    currentTotal = currentTotal + response.body()?.reviews?.size!!
                    mAdapter?.addAll(response.body()?.reviews!!)
                    loading = false
                }
            }
        }

        SweetAlertBox.instance.showProgressDialog(this)
        RetrofitCallback.getReviewList(this,
                intent.getStringExtra("id"),
                page.toString(),
                limit.toString(),
                RetrofitCustomCallback(callback, this))

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
