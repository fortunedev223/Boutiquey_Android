package webkul.opencart.mobikul

sealed class SealedClass {
    data class SearchProduct(val productId:String,val productName:String)
    data class OrderReview(val returnId:String,val orderId:String,val name:String,val status:String,val dateAdded:String)
}