package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 9/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("option")
    @Expose
    var option: List<Option>? = null
    @SerializedName("download")
    @Expose
    var download: List<Any>? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("subtract")
    @Expose
    var subtract: String? = null
    @SerializedName("price")
    @Expose
    var price: Double = 0.toDouble()
    @SerializedName("total")
    @Expose
    var total: Double = 0.toDouble()
    @SerializedName("price_text")
    @Expose
    var priceText: String? = null
    @SerializedName("total_text")
    @Expose
    var totalText: String? = null
    @SerializedName("tax")
    @Expose
    var tax: Double = 0.toDouble()
    @SerializedName("reward")
    @Expose
    var reward: Int? = null
    @SerializedName("image")
    @Expose
    var image: String? = null

}