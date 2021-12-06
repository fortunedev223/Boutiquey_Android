package webkul.opencart.mobikul.model.SellerProfileModel



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

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
    @SerializedName("formatted_special")
    @Expose
    var formattedSpecial: String? = null
    @SerializedName("special")
    @Expose
    var special: Int? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("hasOption")
    @Expose
    var hasOption: Boolean? = null
    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("href")
    @Expose
    var href: String? = null

}

