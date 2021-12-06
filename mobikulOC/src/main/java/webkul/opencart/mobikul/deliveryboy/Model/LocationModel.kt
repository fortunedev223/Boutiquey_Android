package webkul.opencart.mobikul.deliveryboy.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

import java.util.ArrayList

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class LocationModel : BaseModel() {
    @SerializedName("track")
    @Expose
    var tracks: ArrayList<Track>? = null
}
