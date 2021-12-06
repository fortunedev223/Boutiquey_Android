package webkul.opencart.mobikul.model.VIewCartModel

/**
 * Created by manish.choudhary on 25/8/17.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Option {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

}