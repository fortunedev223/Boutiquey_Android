package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("child_status")
    @Expose
    var childStatus: Boolean? = null
    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("dominant_color_icon")
    @Expose
    var dominantColorIcon: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("children")
    @Expose
    var children: List<Child>? = null
    @SerializedName("column")
    @Expose
    var column: String? = null
    @SerializedName("path")
    @Expose
    var path: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null

}