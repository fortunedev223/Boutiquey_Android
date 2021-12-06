package webkul.opencart.mobikul.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.MainAcitivityAdapter
import webkul.opencart.mobikul.adapterModel.HomePageAdapteModel
import webkul.opencart.mobikul.callback.GetHomeProductData
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.roomdatabase.AppDataBaseController
import webkul.opencart.mobikul.databinding.FragmentFeatureProductBinding

class FeatureProduct : Fragment(), GetHomeProductData {
    private var fragmentFeatureProductBinding: FragmentFeatureProductBinding? = null
    private var homeDataModel: HomeDataModel? = null
    private var featureProduct: RecyclerView? = null
    private var homeDataModelCallback: Callback<HomeDataModel>? = null
    private var acitivityAdapter: MainAcitivityAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFeatureProductBinding = FragmentFeatureProductBinding.inflate(inflater, container, false)
        featureProduct = fragmentFeatureProductBinding!!.featuredProduct
        if (homeDataModel != null) {
        } else {
            homeDataModelCallback = object : Callback<HomeDataModel> {
                override fun onResponse(call: Call<HomeDataModel>, response: Response<HomeDataModel>) {
                    response.body()?.let {
                        homeDataModel = it
                    }
                }

                override fun onFailure(call: Call<HomeDataModel>, t: Throwable) {

                }
            }
        }
        return fragmentFeatureProductBinding!!.root
    }


    override fun getHomeProductData(homeDataModel: HomeDataModel?, isRefresh: Boolean) {
        this.homeDataModel = homeDataModel
    }
}
