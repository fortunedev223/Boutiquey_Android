package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel

/**
 * Created by manish.choudhary on 3/10/17.
 */

interface GetHomeProductData {
    fun getHomeProductData(homeDataModel: HomeDataModel?, isReferteshed: Boolean)
}
