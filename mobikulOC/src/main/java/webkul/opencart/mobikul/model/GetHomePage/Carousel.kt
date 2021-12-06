package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Carousel {

    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null

}