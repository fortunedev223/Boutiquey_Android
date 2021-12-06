package webkul.opencart.mobikul.model.GetWishlist



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class GetWishlist : BaseModel() {

    @SerializedName("wishlistData")
    @Expose
    var wishlistData: List<WishlistDatum>? = null
        get() = if (field == null) {
            null
        } else {
            field

        }
}
