package webkul.opencart.mobikul.model.VIewCartModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null

    @SerializedName("key")
    @Expose
    var key: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("option")
    @Expose
    var option: List<Option>? = null
    @SerializedName("recurring")
    @Expose
    var recurring: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("stock")
    @Expose
    var stock: Boolean? = null
    @SerializedName("reward")
    @Expose
    var reward: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("product_id")
    @Expose
    var productId: String? = null
}
