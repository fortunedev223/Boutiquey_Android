package webkul.opencart.mobikul.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapter.SubCategoryV3ThemeAdapter
import webkul.opencart.mobikul.adapterModel.SubCategoryV3ThemeAdapterModel
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.ActivitySubCategoryV3ThemeBinding

class SubCategoryV3Theme : Fragment() {
    private var categoryRecyclerview: RecyclerView? = null
    private var homeDataModel: HomeDataModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ActivitySubCategoryV3ThemeBinding.inflate(inflater, container, false)
        categoryRecyclerview = binding.subcategoryV3Recylcer
        homeDataModel = MainActivity.homeDataModel
        if (homeDataModel != null && isAdded) {
            setCategoryImage()
        } else {
            val homeDataModelCallback = object : Callback<HomeDataModel> {
                override fun onResponse(call: Call<HomeDataModel>, response: Response<HomeDataModel>) {
                    homeDataModel = response.body()
                    setCategoryImage()
                }

                override fun onFailure(call: Call<HomeDataModel>, t: Throwable) {}
            }
            RetrofitCallback.getHomePageCall(activity!!, "",
                    RetrofitCustomCallback(homeDataModelCallback, activity), Utils.getScreenWidth(),
                    "")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setCategoryImage() {
        val adapterModels = ArrayList<SubCategoryV3ThemeAdapterModel>()
        for (i in 0 until homeDataModel!!.categories!!.size) {
            adapterModels.add(SubCategoryV3ThemeAdapterModel(
                    homeDataModel?.categories!![i].image,
                    homeDataModel?.categories!![i].name!!,
                    homeDataModel?.categories!![i].path, i,
                    homeDataModel?.categories!![i].dominantColorIcon,
                    homeDataModel?.categories!![i].childStatus))
        }

        val adapter = SubCategoryV3ThemeAdapter(adapterModels, activity!!)
        categoryRecyclerview?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        categoryRecyclerview?.adapter = adapter
    }

}
