package webkul.opencart.mobikul.model.ReviewListModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class Review {
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null

}
