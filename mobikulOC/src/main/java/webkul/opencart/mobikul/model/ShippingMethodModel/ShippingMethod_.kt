package webkul.opencart.mobikul.model.ShippingMethodModel

/**
 * Created by manish.choudhary on 4/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ShippingMethod_ {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("quote")
    @Expose
    var quote: List<Quote>? = null
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null
    @SerializedName("error")
    @Expose
    var error: Boolean? = null

  }
