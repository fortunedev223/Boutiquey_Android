package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Currencies {

    @SerializedName("text_currency")
    @Expose
    var textCurrency: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("currencies")
    @Expose
    var currencies: List<Currency>? = null

}