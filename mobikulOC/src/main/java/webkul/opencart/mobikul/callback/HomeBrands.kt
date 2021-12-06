package webkul.opencart.mobikul.callback

import java.util.ArrayList

import webkul.opencart.mobikul.adapterModel.CarousalAdapterModel

/**
 * Created by manish.choudhary on 16/8/17.
 */

interface HomeBrands {
    fun getBrands(carousalAdapterModels: ArrayList<CarousalAdapterModel>)
}
