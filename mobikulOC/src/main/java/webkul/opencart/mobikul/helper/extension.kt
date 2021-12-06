package webkul.opencart.mobikul.helper

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */


fun RecyclerView.setGridLayoutManager(mContext: Context, count: Int) {
    this.isNestedScrollingEnabled = false
    this.layoutManager = GridLayoutManager(mContext, count)
}

fun RecyclerView.setLinearLayoutManager(mContext: Context, origination: Int) {
    this.isNestedScrollingEnabled = false
    this.layoutManager = LinearLayoutManager(mContext, origination, false)
}
