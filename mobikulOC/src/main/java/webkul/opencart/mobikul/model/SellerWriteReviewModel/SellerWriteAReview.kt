package webkul.opencart.mobikul.model.SellerWriteReviewModel

import webkul.opencart.mobikul.model.BaseModel.BaseModel

/**
 * Created by manish.choudhary on 25/9/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SellerWriteAReview(@field:SerializedName("data")
                         @field:Expose
                         var data: String?) : BaseModel()
