package webkul.opencart.mobikul.model.SearchProductModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import webkul.opencart.mobikul.model.BaseModel.BaseModel


class SearchProduct:BaseModel() {
    @SerializedName("search_data")
    @Expose
    var searchData: List<SearchDatum>? = null
}