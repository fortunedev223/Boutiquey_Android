package webkul.opencart.mobikul.callback

import android.content.Context

import webkul.opencart.mobikul.model.SellerDashboardModel.SellerDashboard

/**
 * Created by manish.choudhary on 21/9/17.
 */

interface GetSellerDashdoardData {
    fun getSellerDashboardData(context: Context, sellerDashboard: SellerDashboard)
}
