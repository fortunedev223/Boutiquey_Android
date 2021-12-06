package webkul.opencart.mobikul.model.PaymentAddressModel

/**
 * Created by manish.choudhary on 3/8/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData {

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
    @SerializedName("firstname")
    @Expose
    var firstname: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null

}
