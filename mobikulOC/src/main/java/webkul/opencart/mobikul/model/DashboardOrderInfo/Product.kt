package webkul.opencart.mobikul.model.DashboardOrderInfo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("order_product_id")
    @Expose
    var order_product_id: String? = null
    @SerializedName("product_id")
    @Expose
    var productiId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("option")
    @Expose
    var option: List<Option>? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null


}
