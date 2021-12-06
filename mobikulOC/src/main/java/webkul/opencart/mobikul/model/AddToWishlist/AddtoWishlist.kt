package webkul.opencart.mobikul.model.AddToWishlist

/**
 * Created by manish.choudhary on 2/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class AddtoWishlist : BaseModel() {

    @SerializedName("total")
    @Expose
    var total: String? = null
    @SerializedName("wishlist_status")
    @Expose
    val status: Boolean? = null

}
