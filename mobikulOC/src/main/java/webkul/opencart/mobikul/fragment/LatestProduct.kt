package webkul.opencart.mobikul.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.MainAcitivityAdapter
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.callback.GetHomeProductData
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.FragmentLatestProductBinding

class LatestProduct : Fragment(), GetHomeProductData {
    private var latestProductBinding: FragmentLatestProductBinding? = null
    private var homeDataModel: HomeDataModel? = null
    private var latestProduct: RecyclerView? = null
    private var acitivityAdapter: MainAcitivityAdapter? = null
    private var homeDataModelCallback: Callback<HomeDataModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        latestProductBinding = FragmentLatestProductBinding.inflate(inflater, container, false)
        latestProduct = latestProductBinding!!.latestProduct
        if (homeDataModel != null) {
            setData()
        } else {
            homeDataModelCallback = object : Callback<HomeDataModel> {
                override fun onResponse(call: Call<HomeDataModel>, response: Response<HomeDataModel>) {
                    response.body()?.let {
                        homeDataModel = it
                        setData()
                    }
                }

                override fun onFailure(call: Call<HomeDataModel>, t: Throwable) {

                }
            }
            RetrofitCallback.getHomePageCall(
                    activity!!, "",
                    RetrofitCustomCallback(
                            homeDataModelCallback,
                            activity),
                    Utils.getScreenWidth(),
                    "")
        }
        return latestProductBinding!!.root
    }

    private fun setData() {
        val homePageAdapteModels = ArrayList<HomePageAdapteModel>()
        homeDataModel!!.latestProducts!!.products?.let {
            it.map { p ->
//                homePageAdapteModels.add(HomePageAdapteModel(p.thumb!!, p.price!!, p.name!!, p.productId!!, p.special!!, p.hasOption!!, p.wishlist_status!!))
            }
        }
//        acitivityAdapter = MainAcitivityAdapter(activity!!, homePageAdapteModels)
//        latestProduct!!.layoutManager = GridLayoutManager(activity, 2)
//        latestProduct!!.adapter = acitivityAdapter
//        latestProduct!!.isNestedScrollingEnabled = false
    }

    override fun getHomeProductData(homeDataModel: HomeDataModel?, isRefresh: Boolean) {
        this.homeDataModel = homeDataModel
        println(" ===kkkk===  ")
        if (isRefresh)
            setData()
    }

}
