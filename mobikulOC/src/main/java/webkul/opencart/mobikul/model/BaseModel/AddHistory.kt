package webkul.opencart.mobikul.model.BaseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddHistory :BaseModel() {

    @SerializedName("data")
    @Expose
    var data: String = ""

}