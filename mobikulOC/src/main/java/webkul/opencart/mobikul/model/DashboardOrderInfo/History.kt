package webkul.opencart.mobikul.model.DashboardOrderInfo

/**
 * Created by manish.choudhary on 2/8/17.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class History {

    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null

}