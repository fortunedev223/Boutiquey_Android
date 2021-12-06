package webkul.opencart.mobikul.model.VIewCartModel

/**
 * Created by manish.choudhary on 14/7/17.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Total {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null

}
