package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer_ {

    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("data")
    @Expose
    var data: List<List<Int>>? = null

}
