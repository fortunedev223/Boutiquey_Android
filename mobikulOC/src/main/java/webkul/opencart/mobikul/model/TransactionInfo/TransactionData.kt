package webkul.opencart.mobikul.model.TransactionInfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TransactionData {

    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("date_added")
    @Expose
    var date_added: String? = null

}
