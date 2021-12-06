package webkul.opencart.mobikul.model.RegisterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomerGroup {

    @SerializedName("customer_group_id")
    @Expose
    var customerGroupId: String? = null
    @SerializedName("approval")
    @Expose
    var approval: String? = null
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null
    @SerializedName("language_id")
    @Expose
    var languageId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null

}
