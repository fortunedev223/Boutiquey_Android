package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LatestProducts {

    @SerializedName("products")
    @Expose
    var products: List<Product>? = null

}