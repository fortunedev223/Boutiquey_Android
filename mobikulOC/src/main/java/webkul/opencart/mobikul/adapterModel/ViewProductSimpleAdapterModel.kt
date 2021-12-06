package webkul.opencart.mobikul.adapterModel

import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by manish.choudhary on 10/4/17.
 */

class ViewProductSimpleAdapterModel(mainObject: JSONObject) {
    var review: JSONArray? = null
    var features: JSONArray? = null
    var langArray: JSONObject? = null
    var tabReview: String? = null
    var productId: String? = null
    lateinit var options: JSONArray
    var hasoption: Boolean = false
    var productName: String? = null
    var manufacturer: String? = null
    var manufacturerId: String? = null
    var model: String? = null
    var available: String? = null
    var imageUrl: String? = null
    var price: String? = null
    var reviewStatus: String? = null
    var description: String? = null
    var relatedProduct: JSONArray? = null
    var images: JSONArray? = null

    var href: String? = null
    var reviewData: JSONObject? = null

    init {
        try {
            this.langArray = mainObject.getJSONObject("langArray")
            this.tabReview = mainObject.getString("tab_review")
            productId = mainObject.getString("product_id")
            review = mainObject.getJSONObject("reviewData").getJSONArray("reviews")
            features = mainObject.getJSONArray("attribute_groups")
            productName = mainObject.getString("name")
            manufacturer = mainObject.getString("manufacturer")
            manufacturerId = mainObject.getString("manufacturer_id")
            model = mainObject.getString("model")
            available = mainObject.getString("stock")
            imageUrl = mainObject.getString("thumb")
            Log.d("ViewProductSimpe", "Image-----> " + imageUrl!!)
            images = mainObject.getJSONArray("images")
            price = mainObject.getString("price")
            reviewStatus = mainObject.getString("review_status")
            description = mainObject.getString("description")
            Log.d("DataHolder", "ViewProductSimpleAdapterModel------------> " + description!!)
            relatedProduct = mainObject.getJSONArray("relatedProducts")
            this.href = mainObject.getString("href")
            reviewData = mainObject.getJSONObject("reviewData")
            options = mainObject.getJSONArray("options")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun gethasoption(): Boolean {
        return if (options.length() != 0) {
            true
        } else false
    }
}
