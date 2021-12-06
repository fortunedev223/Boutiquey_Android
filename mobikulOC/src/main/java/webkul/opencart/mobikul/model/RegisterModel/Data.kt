package webkul.opencart.mobikul.model.RegisterModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("information_id")
    @Expose
    var informationId: String? = null
    @SerializedName("bottom")
    @Expose
    var bottom: String? = null
    @SerializedName("sort_order")
    @Expose
    var sortOrder: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("language_id")
    @Expose
    var languageId: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("meta_title")
    @Expose
    var metaTitle: String? = null
    @SerializedName("meta_description")
    @Expose
    var metaDescription: String? = null
    @SerializedName("meta_keyword")
    @Expose
    var metaKeyword: String? = null
    @SerializedName("store_id")
    @Expose
    var storeId: String? = null

}
