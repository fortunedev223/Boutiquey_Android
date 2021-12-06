package webkul.opencart.mobikul.model.ConfirmModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class PayTabsResponse extends BaseModel{
    @SerializedName("result")
    @Expose
    public String result;
    @SerializedName("response_code")
    @Expose
    public String response_code;
    @SerializedName("pt_invoice_id")
    @Expose
    public String pt_invoice_id;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("transaction_id")
    @Expose
    public String transaction_id;
    @SerializedName("order_id")
    @Expose
    public String order_id;

    public PayTabsResponse(String result, String response_code, String pt_invoice_id, String amount, String currency, String transaction_id, String order_id) {
        this.result = result;
        this.response_code = response_code;
        this.pt_invoice_id = pt_invoice_id;
        this.amount = amount;
        this.currency = currency;
        this.transaction_id = transaction_id;
        this.order_id = order_id;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getPt_invoice_id() {
        return pt_invoice_id;
    }

    public void setPt_invoice_id(String pt_invoice_id) {
        this.pt_invoice_id = pt_invoice_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
