package webkul.opencart.mobikul.model.OrderReturnInfoModel

/**
 * Created by manish.choudhary on 31/5/18.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ReturnInfo:BaseModel() {

    @SerializedName("return_id")
    @Expose
    var returnId: String? = null
    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("date_ordered")
    @Expose
    var dateOrdered: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("telephone")
    @Expose
    var telephone: String? = null
    @SerializedName("product")
    @Expose
    var product: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("reason")
    @Expose
    var reason: String? = null
    @SerializedName("opened")
    @Expose
    var opened: String? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("action")
    @Expose
    var action: Any? = null
    @SerializedName("histories")
    @Expose
    var histories: List<Any>? = null

}