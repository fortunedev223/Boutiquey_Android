package webkul.opencart.mobikul.model.SearchProductModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by manish.choudhary on 16/5/18.
 */
class SearchDatum {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}