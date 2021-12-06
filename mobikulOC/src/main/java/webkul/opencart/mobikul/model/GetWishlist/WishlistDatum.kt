package webkul.opencart.mobikul.model.GetWishlist


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WishlistDatum {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("special")
    @Expose
    var special: String? = null
    @SerializedName("formatted_special")
    @Expose
    var formattedSpecial: String? = null
    @SerializedName("hasOption")
    @Expose
    var hasOption: Boolean? = null

}
