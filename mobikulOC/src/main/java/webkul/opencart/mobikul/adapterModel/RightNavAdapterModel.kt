package webkul.opencart.mobikul.adapterModel

import android.text.Html
import android.util.Log


class RightNavAdapterModel(categoryName: String, var path: String?, var imageUrl: String?,
                           var iconUrl: String?, var dominantColor: String?, var childStatus: Boolean?) {
    var categoryName: String? = null
        get() {
            var name = ""
            if (field!!.contains("(")) {
                name = field!!.substring(0, field!!.indexOf("("))
            } else {
                name = Html.fromHtml(field).toString()
            }
            return if (name.contains("&amp;")) {
                name.replace("&amp;", "")
            } else name
        }

    init {
        this.categoryName = categoryName
        Log.d("Nav", "RightNavAdapterModel:----------> $categoryName")
    }
}
