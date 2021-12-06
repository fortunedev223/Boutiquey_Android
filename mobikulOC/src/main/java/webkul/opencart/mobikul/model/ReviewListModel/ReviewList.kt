package webkul.opencart.mobikul.model.ReviewListModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class ReviewList : BaseModel() {
    @SerializedName("total")
    @Expose
    var total: String? = null

    @SerializedName("reviews")
    @Expose
    var reviews: ArrayList<Review>? = null

}
