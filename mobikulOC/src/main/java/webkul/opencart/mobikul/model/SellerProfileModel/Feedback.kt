package webkul.opencart.mobikul.model.SellerProfileModel

/**
 * Created by manish.choudhary on 26/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Feedback {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
    @SerializedName("seller_id")
    @Expose
    var sellerId: String? = null
    @SerializedName("nickname")
    @Expose
    var nickname: String? = null
    @SerializedName("summary")
    @Expose
    var summary: String? = null
    @SerializedName("review")
    @Expose
    var review: String? = null
    @SerializedName("createdate")
    @Expose
    var createdate: String? = null
    @SerializedName("review_attributes")
    @Expose
    var reviewAttributes: List<ReviewAttribute>? = null

}