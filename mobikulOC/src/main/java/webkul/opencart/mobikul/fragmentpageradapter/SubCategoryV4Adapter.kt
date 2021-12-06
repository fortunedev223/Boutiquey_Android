package webkul.opencart.mobikul.fragmentpageradapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

import java.util.ArrayList

/**
 * Created by manish.choudhary on 5/10/17.
 */

class SubCategoryV4Adapter : FragmentStatePagerAdapter {
    private var fragmentArrayList: ArrayList<Fragment>? = null
    private var mContext: Context? = null
    private var titles: ArrayList<String>? = null

    constructor(fm: FragmentManager, fragmentArrayList: ArrayList<Fragment>, mContext: Context, titles: ArrayList<String>) : super(fm) {
        this.fragmentArrayList = fragmentArrayList
        this.mContext = mContext
        this.titles = titles
    }

    constructor(fm: FragmentManager) : super(fm) {
        fragmentArrayList = ArrayList()
        titles = ArrayList()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList!![position]
    }

    //    public void addFragment(Fragment fragment, String title) {
    //        fragmentArrayList.add(fragment);
    //        titles.add(title);
    //    }
    override fun getCount(): Int {
        return fragmentArrayList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        Log.d("SubCategoryV4", "getPageTitle:---------> " + titles!![position])
        return titles!![position]
    }
}
