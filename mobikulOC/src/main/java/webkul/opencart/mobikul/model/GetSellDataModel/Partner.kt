package webkul.opencart.mobikul.model.GetSellDataModel

/**
 * Created by manish.choudhary on 20/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Partner {

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("companyname")
    @Expose
    var companyname: String? = null
    @SerializedName("backgroundcolor")
    @Expose
    var backgroundcolor: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("total_products")
    @Expose
    var totalProducts: Int? = null

}
