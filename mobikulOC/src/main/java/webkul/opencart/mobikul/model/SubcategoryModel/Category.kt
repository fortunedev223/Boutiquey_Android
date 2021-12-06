package webkul.opencart.mobikul.model.SubcategoryModel

/**
 * Webkul Software. *
 *
 * @author Webkul
 * @Mobikul
 * @OpenCartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("child_status")
    @Expose
    var childStatus: Boolean? = null
    @SerializedName("path")
    @Expose
    var path: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null
    @SerializedName("dominant_color_icon")
    @Expose
    var dominantColorIcon: String? = null

}
