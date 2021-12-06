package webkul.opencart.mobikul.adapterModel

import android.text.Html


class SubCategoryV3ThemeAdapterModel(var imageUrl: String?, categoryName: String, var path: String?, var position: Int,var dominantColor:String?,var childStatus:Boolean?) {
    var categoryName: String? = null
        get() = if (field!!.contains("(")) {
            Html.fromHtml(field!!.substring(0, field!!.indexOf("(") - 1)).toString()
        } else field

    init {
        this.categoryName = categoryName
    }
}
