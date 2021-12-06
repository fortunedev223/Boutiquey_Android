package webkul.opencart.mobikul.utils

import android.app.Activity
import android.databinding.BindingAdapter
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.adapter.*
import webkul.opencart.mobikul.adapterModel.*
import webkul.opencart.mobikul.helper.setGridLayoutManager
import webkul.opencart.mobikul.helper.setLinearLayoutManager
import webkul.opencart.mobikul.model.GetHomePage.*
import java.util.*

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */

object HomePageLayoutBinder {

    @JvmStatic
    @BindingAdapter("setPager")
    fun bindPager(pager: ViewPager, banners: ArrayList<Banner>?) {
        if (banners != null && banners.size != 0) {
            val list = ArrayList<HomePageBannerAdapterModel>()
            banners.map {
                list.add(HomePageBannerAdapterModel(
                        it.image,
                        it.type,
                        it.title,
                        it.link,
                        it.dominantColor))
            }
            pager.adapter = ImagePagerAdapter<MainActivity>(list, pager.context)
            val layoutParams = LinearLayout.LayoutParams(
                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                    webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth() / 2)
            pager.layoutParams = layoutParams
            pager.clipToPadding = false
            pager.setPadding(40, 0, 40, 0)
            pager.pageMargin = 20
        }
    }

    @JvmStatic
    @BindingAdapter("setCarousal")
    fun bindProductCarousal(recyclerview: RecyclerView, modules: ArrayList<Modules>?) {
        if (modules != null && modules.size != 0) {
            recyclerview.adapter = HomePageCarousalAdapter(recyclerview.context, modules)
            recyclerview.setLinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL)
        }
    }

    @JvmStatic
    @BindingAdapter("setRecyclerview")
    fun bindHomeView(recyclerview: RecyclerView, productList: ArrayList<Featured>) {
        if (productList.size != 0) {
            val model = ArrayList<HomePageAdapteModel>()
            productList.map {
                model.add(HomePageAdapteModel(
                        it.thumb,
                        it.price,
                        it.name!!,
                        it.productId,
                        it.special,
                        it.formattedSpecial!!,
                        it.hasOption!!,
                        it.wishlist_status!!,
                        it.dominantColor))
            }
            recyclerview.adapter = MainAcitivityAdapter(recyclerview.context!!, model, 2)
            recyclerview.setGridLayoutManager(recyclerview.context, 2)
        }
    }


    @JvmStatic
    @BindingAdapter("setLanguage")
    fun bindLanguageView(recyclerview: RecyclerView, languages: Languages) {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val languageDataHolderArrayList = ArrayList<LanguageAdapterModel>()
                languages.languages?.map {
                    languageDataHolderArrayList.add(LanguageAdapterModel(
                            languages.code!!,
                            it.name,
                            it.code,
                            it.image))
                }
                recyclerview.adapter = HomePageLanguageAdapter(recyclerview.context, languageDataHolderArrayList)
                recyclerview.setLinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL)
            }
        }, 200)
    }

    @JvmStatic
    @BindingAdapter("setCurrencey")
    fun bindCurrencyView(recyclerview: RecyclerView, currencies: Currencies) {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val currencyDataHolderArrayList = ArrayList<CurrencyAdapterModel>()
                currencies.currencies?.map {
                    currencyDataHolderArrayList.add(CurrencyAdapterModel(
                            currencies.code!!,
                            it.title,
                            it.code))
                }
                recyclerview.adapter = HomePageCurrencyAdapter(recyclerview.context, currencyDataHolderArrayList)
                recyclerview.setLinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL)
            }
        }, 300)

    }


    @JvmStatic
    @BindingAdapter("setNavData")
    fun bindNavigationView(recyclerview: RecyclerView, categories: List<Category>) {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val dataholders = ArrayList<RightNavAdapterModel>()
                categories.map {
                    dataholders.add(RightNavAdapterModel(
                            it.name!!,
                            it.path,
                            it.image,
                            it.icon,
                            it.dominantColorIcon,
                            it.childStatus))
                }
                recyclerview.adapter = LeftNavAdapter(recyclerview.context, dataholders)
                recyclerview.setLinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL)
            }
        }, 400)
    }

    @JvmStatic
    @BindingAdapter("setSubCatData")
    fun bindSubCategoryView(recyclerview: RecyclerView, categories: List<Category>) {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val dataholders = ArrayList<RightNavAdapterModel>()
                categories.map {
                    dataholders.add(RightNavAdapterModel(
                            it.name!!,
                            it.path,
                            it.image,
                            it.icon,
                            it.dominantColor,
                            it.childStatus))
                }
                recyclerview.adapter = SubcategoryAdapter(dataholders, recyclerview.context)
                recyclerview.setLinearLayoutManager(recyclerview.context, LinearLayoutManager.HORIZONTAL)
            }
        }, 100)
    }

    @JvmStatic
    @BindingAdapter("setBrands")
    fun bindBrandsView(recyclerview: RecyclerView, carousalList: ArrayList<Carousel>?) {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                if (carousalList != null && carousalList.size != 0) {
                    val carousalAdapterModels = ArrayList<CarousalAdapterModel>()
                    carousalList.map {
                        carousalAdapterModels.add(CarousalAdapterModel(
                                it.title!!,
                                it.image!!,
                                it.link!!,
                                it.dominantColor))
                    }
                    recyclerview.adapter = CarousalAdapter(recyclerview.context, carousalAdapterModels)
                    recyclerview.setGridLayoutManager(recyclerview.context, 2)
                }
            }
        }, 100)
    }

    @JvmStatic
    @BindingAdapter("setAbout")
    fun bindAbout(recylcerView: RecyclerView, footerMenu: ArrayList<FooterMenu>) {
        if (footerMenu.size != 0) {
            recylcerView.adapter = FooterMenuAdapter(recylcerView.context, footerMenu)
            recylcerView.setLinearLayoutManager(recylcerView.context, LinearLayoutManager.VERTICAL)
        }
    }

}
