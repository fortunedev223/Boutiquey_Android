package webkul.opencart.mobikul.pageradapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

/**
 * Created by manish.choudhary on 3/10/17.
 */

class HomeProductPager(fm: FragmentManager, val fragmentArrayList: ArrayList<Fragment>, val title: ArrayList<String>, val mContext: Context) : FragmentPagerAdapter(fm) {
    private var mCurrentPosition = -1
    override fun getItem(position: Int): Fragment {
        return fragmentArrayList[position]
    }

    override fun getCount(): Int {
        return fragmentArrayList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}
