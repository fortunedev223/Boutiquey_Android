package webkul.opencart.mobikul.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.HomePageBannerAdapterModel
import webkul.opencart.mobikul.BaseActivity
import webkul.opencart.mobikul.handlers.HomePagePagerHandler
import webkul.opencart.mobikul.MainActivity
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.ItemViewPagerBannerBinding


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class ImagePagerAdapter<A : BaseActivity>(private val list: ArrayList<HomePageBannerAdapterModel>,
                                          private val mContext: Context) : PagerAdapter() {
    private val inflater: LayoutInflater

    init {
        inflater = (mContext as A).layoutInflater
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun finishUpdate(container: View) {

    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(view: View, position: Int): Any {
        val imageLayout = DataBindingUtil.inflate<ItemViewPagerBannerBinding>(inflater,
                R.layout.item_view_pager_banner, view as ViewGroup, false)
        if (MainActivity.homeDataModel?.languages?.code == "ar") {
            imageLayout.root.rotationY = 180f
        }
        imageLayout.image.setBackgroundColor(Color.parseColor(list[position].dominantColor))
        imageLayout.handler = HomePagePagerHandler(mContext)
        imageLayout.data = list[position]
        (view as ViewPager).addView(imageLayout.root, 0)
        return imageLayout.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

    override fun startUpdate(container: View) {

    }
}
