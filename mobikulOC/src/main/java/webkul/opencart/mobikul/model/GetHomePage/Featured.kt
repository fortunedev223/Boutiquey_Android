package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Featured {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("special")
    @Expose
    var special: String? = null
    @SerializedName("formatted_special")
    @Expose
    var formattedSpecial: String? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null

    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("hasOption")
    @Expose
    var hasOption: Boolean? = null
    @SerializedName("wishlist_status")
    @Expose
    var wishlist_status: Boolean? = false
}