package webkul.opencart.mobikul.model.ReturnOrderRequestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ReturnOrderRequest : BaseModel() {

    @SerializedName("order_id")
    @Expose
    var orderId: String? = null
    @SerializedName("date_ordered")
    @Expose
    var dateOrdered: String? = null
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
    @SerializedName("return_reasons")
    @Expose
    var returnReasons: List<ReturnReason>? = null
    @SerializedName("captcha")
    @Expose
    var captcha: String? = null
//    @SerializedName("agree")
//    @Expose
//    var agree: String? = null

}