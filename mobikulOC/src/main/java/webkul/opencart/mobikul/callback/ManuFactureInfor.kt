package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.model.ManufactureInfoModel.Manufacture

/**
 * Created by manish.choudhary on 14/8/17.
 */

interface ManuFactureInfor {
    fun getManufactureInfo(manufacture: Manufacture)
}
