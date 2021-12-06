package webkul.opencart.mobikul.model.TransactionInfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class TransactionInfoDataModel : BaseModel() {
    @SerializedName("transactionData")
    @Expose
    var transactionData: List<TransactionData>? = null

    @SerializedName("transactionsTotal")
    @Expose
    var transactionsTotal: String? = null

    @SerializedName("transactionText")
    @Expose
    var transactionText: String? = null

    @SerializedName("totalBalance")
    @Expose
    var totalBalance: String? = null

}
