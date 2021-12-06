package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 3/8/17.
 */

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Discount : Parcelable {

    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null

    protected constructor(`in`: Parcel) {
        quantity = `in`.readString()
        price = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(quantity)
        dest.writeString(price)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Discount> = object : Parcelable.Creator<Discount> {
            override fun createFromParcel(`in`: Parcel): Discount {
                return Discount(`in`)
            }

            override fun newArray(size: Int): Array<Discount?> {
                return arrayOfNulls(size)
            }
        }
    }
}