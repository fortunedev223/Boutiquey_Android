package webkul.opencart.mobikul.model.GetSellDataModel

/**
 * Created by manish.choudhary on 20/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("sell_title")
    @Expose
    var sellTitle: String? = null
    @SerializedName("sell_header")
    @Expose
    var sellHeader: String? = null
    @SerializedName("showpartners")
    @Expose
    var showpartners: String? = null
    @SerializedName("showproducts")
    @Expose
    var showproducts: String? = null
    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_long_time_seller")
    @Expose
    var textLongTimeSeller: String? = null
    @SerializedName("text_latest_product")
    @Expose
    var textLatestProduct: String? = null
    @SerializedName("text_tax")
    @Expose
    var textTax: String? = null
    @SerializedName("text_from")
    @Expose
    var textFrom: String? = null
    @SerializedName("text_seller")
    @Expose
    var textSeller: String? = null
    @SerializedName("text_total_products")
    @Expose
    var textTotalProducts: String? = null
    @SerializedName("button_cart")
    @Expose
    var buttonCart: String? = null
    @SerializedName("button_wishlist")
    @Expose
    var buttonWishlist: String? = null
    @SerializedName("button_compare")
    @Expose
    var buttonCompare: String? = null
    @SerializedName("partners")
    @Expose
    var partners: List<Partner>? = null
    @SerializedName("latest")
    @Expose
    var latest: List<Latest>? = null
    @SerializedName("tabs")
    @Expose
    var tabs: List<Any>? = null

}
