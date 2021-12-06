package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Languages {

    @SerializedName("text_language")
    @Expose
    var textLanguage: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("languages")
    @Expose
    var languages: List<Language>? = null

}