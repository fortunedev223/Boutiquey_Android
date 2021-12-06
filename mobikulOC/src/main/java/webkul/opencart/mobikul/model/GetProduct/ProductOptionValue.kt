package webkul.opencart.mobikul.model.GetProduct

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by manish.choudhary on 3/8/17.
 */

class ProductOptionValue : Parcelable {

    @SerializedName("product_option_value_id")
    @Expose
    var productOptionValueId: String? = null
    @SerializedName("option_value_id")
    @Expose
    var optionValueId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("price_prefix")
    @Expose
    var pricePrefix: String? = null


    protected constructor(`in`: Parcel) {
        productOptionValueId = `in`.readString()
        optionValueId = `in`.readString()
        name = `in`.readString()
        image = `in`.readString()
        price = `in`.readString()
        pricePrefix = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productOptionValueId)
        dest.writeString(optionValueId)
        dest.writeString(name)
        dest.writeString(image)
        dest.writeString(price)
        dest.writeString(pricePrefix)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductOptionValue> = object : Parcelable.Creator<ProductOptionValue> {
            override fun createFromParcel(`in`: Parcel): ProductOptionValue {
                return ProductOptionValue(`in`)
            }

            override fun newArray(size: Int): Array<ProductOptionValue?> {
                return arrayOfNulls(size)
            }
        }
    }
}