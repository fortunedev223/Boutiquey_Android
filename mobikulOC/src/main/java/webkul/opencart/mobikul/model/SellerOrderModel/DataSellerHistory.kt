package webkul.opencart.mobikul.model.SellerOrderModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataSellerHistory {
    @SerializedName("order_status")
    @Expose
    var orderStatus: String? = null
    @SerializedName("chkIsPartner")
    @Expose
    var chkIsPartner: Boolean = false
    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("error_page_order")
    @Expose
    var errorPageOrder: String? = null
    @SerializedName("text_order_detail")
    @Expose
    var textOrderDetail: String? = null
    @SerializedName("text_invoice_no")
    @Expose
    var textInvoiceNo: String? = null
    @SerializedName("text_order_id")
    @Expose
    var textOrderId: String? = null
    @SerializedName("text_date_added")
    @Expose
    var textDateAdded: String? = null
    @SerializedName("text_shipping_method")
    @Expose
    var textShippingMethod: String? = null
    @SerializedName("text_shipping_address")
    @Expose
    var textShippingAddress: String? = null
    @SerializedName("text_payment_method")
    @Expose
    var textPaymentMethod: String? = null
    @SerializedName("text_payment_address")
    @Expose
    var textPaymentAddress: String? = null
    @SerializedName("text_history")
    @Expose
    var textHistory: String? = null
    @SerializedName("text_comment")
    @Expose
    var textComment: String? = null
    @SerializedName("text_wait")
    @Expose
    var textWait: String? = null
    @SerializedName("column_tracking_no")
    @Expose
    var columnTrackingNo: String? = null
    @SerializedName("column_name")
    @Expose
    var columnName: String? = null
    @SerializedName("column_model")
    @Expose
    var columnModel: String? = null
    @SerializedName("column_quantity")
    @Expose
    var columnQuantity: String? = null
    @SerializedName("column_price")
    @Expose
    var columnPrice: String? = null
    @SerializedName("column_total")
    @Expose
    var columnTotal: String? = null
    @SerializedName("column_action")
    @Expose
    var columnAction: String? = null
    @SerializedName("column_date_added")
    @Expose
    var columnDateAdded: String? = null
    @SerializedName("column_status")
    @Expose
    var columnStatus: String? = null
    @SerializedName("column_comment")
    @Expose
    var columnComment: String? = null
    @SerializedName("column_transaction_status")
    @Expose
    var columnTransactionStatus: String? = null
    @SerializedName("entry_order_status")
    @Expose
    var entryOrderStatus: String? = null
    @SerializedName("entry_notify")
    @Expose
    var entryNotify: String? = null
    @SerializedName("entry_comment")
    @Expose
    var entryComment: String? = null
    @SerializedName("entry_notifyadmin")
    @Expose
    var entryNotifyadmin: String? = null
    @SerializedName("wksellerorderstatus")
    @Expose
    var wksellerorderstatus: String? = null
    @SerializedName("invoice_no")
    @Expose
    var invoiceNo: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("payment_address")
    @Expose
    var paymentAddress: String? = null
    @SerializedName("payment_method")
    @Expose
    var paymentMethod: String? = null
    @SerializedName("shipping_address")
    @Expose
    var shippingAddress: String? = null
    @SerializedName("shipping_method")
    @Expose
    var shippingMethod: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null
    @SerializedName("vouchers")
    @Expose
    var vouchers: List<Any>? = null
    @SerializedName("totals")
    @Expose
    var totals: List<Total>? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("histories")
    @Expose
    var histories: List<Any>? = null
    @SerializedName("marketplace_order_status_sequence")
    @Expose
    var marketplaceOrderStatusSequence: List<MarketplaceOrderStatusSequence>? = null
    @SerializedName("order_statuses")
    @Expose
    var orderStatuses: List<OrderStatus>? = null
    @SerializedName("order_status_id")
    @Expose
    var orderStatusId: String? = null
}