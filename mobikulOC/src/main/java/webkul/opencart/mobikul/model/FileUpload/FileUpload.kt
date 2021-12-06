package webkul.opencart.mobikul.model.FileUpload

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by manish.choudhary on 14/2/18.
 */

class FileUpload {

    @SerializedName("error")
    @Expose
    var error: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("success")
    @Expose
    var success: String? = null
}
