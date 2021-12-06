package webkul.opencart.mobikul

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.ViewMoreLatestModel.LatestProduct
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.ProductCategory.Sort

class ViewMoreDataForLatest : BaseModel() {
    @SerializedName("products")
    @Expose
    var latestProduct: List<LatestProduct>? = null
    @SerializedName("product_total")
    @Expose
    var productTotal: Int = 0
    @SerializedName("sorts")
    @Expose
    var sorts: List<Sort>? = null
}
