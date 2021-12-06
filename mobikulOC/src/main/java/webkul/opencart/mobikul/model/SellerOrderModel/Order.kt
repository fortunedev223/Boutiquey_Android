package webkul.opencart.mobikul.model.SellerOrderModel

/**
 * Created by manish.choudhary on 18/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Order {

    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("currency_code")
    @Expose
    var currencyCode: String? = null
    @SerializedName("currency_value")
    @Expose
    var currencyValue: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("orderstatus")
    @Expose
    var orderstatus: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null
    @SerializedName("productname")
    @Expose
    var productname: String? = null
    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("orderidlink")
    @Expose
    var orderidlink: String? = null

   }
