package webkul.opencart.mobikul.model.MyAccountModel

/**
 * Created by manish.choudhary on 11/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class MyAccount : BaseModel() {

    @SerializedName("gdpr_status")
    @Expose
    var gdprStatus: Int? = 101
    @SerializedName("gdpr_content")
    @Expose
    var gdprContent: String? = null
    @SerializedName("gdpr_request_status")
    @Expose
    var gdprRequestStatus: Int? = 101
    @SerializedName("gdpr_password_status")
    @Expose
    var gdprPasswordStatus: Int? = null
    @SerializedName("gdpr_password_content")
    @Expose
    var gdprPasswordContent: String? = null
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
    @SerializedName("fax")
    @Expose
    var fax: String? = null
    @SerializedName("customField")
    @Expose
    var customField: List<Any>? = null
}

