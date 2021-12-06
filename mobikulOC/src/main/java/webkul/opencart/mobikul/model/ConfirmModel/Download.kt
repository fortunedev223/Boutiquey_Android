package webkul.opencart.mobikul.model.ConfirmModel

/**
 * Created by manish.choudhary on 11/9/17.
 */


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Download {

    @SerializedName("download_id")
    @Expose
    var downloadId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("filename")
    @Expose
    var filename: String? = null
    @SerializedName("mask")
    @Expose
    var mask: String? = null

}
