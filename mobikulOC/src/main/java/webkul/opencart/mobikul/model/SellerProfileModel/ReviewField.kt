package webkul.opencart.mobikul.model.SellerProfileModel

/**
 * Created by manish.choudhary on 26/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewField {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("average_rating")
    @Expose
    var averageRating: String? = null

}
