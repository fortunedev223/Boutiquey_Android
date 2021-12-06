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
import webkul.opencart.mobikul.model.BaseModel.BaseModel

class SubCategoryModel : BaseModel() {
    @SerializedName("categories")
    @Expose
    var categories: ArrayList<Category>? = null

}
