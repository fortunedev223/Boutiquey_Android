package webkul.opencart.mobikul.model.GetSellDataModel

/**
 * Created by manish.choudhary on 20/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Latest {

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("seller_name")
    @Expose
    var sellerName: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("backgroundcolor")
    @Expose
    var backgroundcolor: String? = null
    @SerializedName("sellerHref")
    @Expose
    var sellerHref: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("formatted_special")
    @Expose
    var formattedPrice: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("special")
    @Expose
    var special: Int? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null
    @SerializedName("hasOption")
    @Expose
    var hasOption: Boolean? = null
    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int = 0
    @SerializedName("href")
    @Expose
    var href: String? = null

}
