package webkul.opencart.mobikul.model.SellerDashboardModel

/**
 * Created by manish.choudhary on 21/9/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chart {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_day")
    @Expose
    var textDay: String? = null
    @SerializedName("text_week")
    @Expose
    var textWeek: String? = null
    @SerializedName("text_month")
    @Expose
    var textMonth: String? = null
    @SerializedName("text_year")
    @Expose
    var textYear: String? = null
    @SerializedName("text_view")
    @Expose
    var textView: String? = null

}
