package webkul.opencart.mobikul.model.CmsPageModel

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
class CmsPage : BaseModel() {
    @SerializedName("information_id")
    @Expose
    var informId: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}
