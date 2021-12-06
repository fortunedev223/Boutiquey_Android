package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel

/**
 * Created by manish.choudhary on 5/10/17.
 */

interface GetV4SubCategoryData {
    fun getHomePageModelData(homeDataModel: HomeDataModel, position: Int)
}
