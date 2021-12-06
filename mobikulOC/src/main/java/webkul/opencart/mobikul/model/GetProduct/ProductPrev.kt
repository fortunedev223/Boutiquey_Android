package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 3/8/17.
 */

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductPrev : Parcelable {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    protected constructor(`in`: Parcel) {
        productId = `in`.readString()
        name = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productId)
        dest.writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductPrev> = object : Parcelable.Creator<ProductPrev> {
            override fun createFromParcel(`in`: Parcel): ProductPrev {
                return ProductPrev(`in`)
            }

            override fun newArray(size: Int): Array<ProductPrev?> {
                return arrayOfNulls(size)
            }
        }
    }
}