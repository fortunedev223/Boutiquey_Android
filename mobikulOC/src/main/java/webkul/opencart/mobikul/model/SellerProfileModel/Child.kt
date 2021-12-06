package webkul.opencart.mobikul.model.SellerProfileModel

/**
 * Created by manish.choudhary on 23/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Child {

    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
