package webkul.opencart.mobikul.model.PaymentAddressModel

/**
 * Created by manish.choudhary on 3/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomField {

    @SerializedName("custom_field_id")
    @Expose
    var customFieldId: String? = null
    @SerializedName("custom_field_value")
    @Expose
    var customFieldValue: List<Any>? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null
    @SerializedName("validation")
    @Expose
    var validation: String? = null
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("required")
    @Expose
    var required: Boolean? = null
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null

}