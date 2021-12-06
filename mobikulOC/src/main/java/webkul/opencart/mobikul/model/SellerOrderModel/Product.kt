package webkul.opencart.mobikul.model.SellerOrderModel

/**
 * Created by manish.choudhary on 18/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("order_product_id")
    @Expose
    var orderProductId: String? = null
    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null
    @SerializedName("reward")
    @Expose
    var reward: String? = null
    @SerializedName("c2oprice")
    @Expose
    var c2oprice: String? = null
    @SerializedName("paid_status")
    @Expose
    var paidStatus: String? = null
    @SerializedName("order_product_status")
    @Expose
    var orderProductStatus: String? = null
}
