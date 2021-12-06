package webkul.opencart.mobikul.helper

import android.support.v4.view.ViewPager
import android.widget.ImageView
import webkul.opencart.mobikul.R


class DetailOnPageChangeListener(val imageUrls: Array<String?>,val dotList: Array<ImageView?>) : ViewPager.OnPageChangeListener {
    var currentPage: Int = 0
        private set

    override fun onPageSelected(position: Int) {
        currentPage = position
        for (i in imageUrls.indices) {
            if (i == position)
                dotList[i]!!.setBackgroundResource(R.drawable.selected_dot_icon)
            else
                dotList[i]!!.setBackgroundResource(R.drawable.unselected_dot_icon)
        }
    }

    override fun onPageScrollStateChanged(arg0: Int) {}

    override fun onPageScrolled(arg0: Int, Offset: Float, positionOffsetPixels: Int) {
        if (Offset > 0.5f) {
        }
    }
}

