package webkul.opencart.mobikul.model.GetProduct


import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedProduct : Parcelable {

    @SerializedName("product_id")
    @Expose
    var productId: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("formatted_special")
    @Expose
    var formattedSpecial: String? = null
    @SerializedName("special")
    @Expose
    var special: String? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null
    @SerializedName("hasOption")
    @Expose
    var hasOption: Boolean? = null
    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("minimum")
    @Expose
    var minimum: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null
    @SerializedName("wishlist_status")
    @Expose
    var wishlistStatus: Boolean? = false

    protected constructor(`in`: Parcel) {
        productId = `in`.readString()
        thumb = `in`.readString()
        name = `in`.readString()
        description = `in`.readString()
        price = `in`.readString()
        special = `in`.readString()
        tax = `in`.readString()
        val hasOptionVal = `in`.readByte()
        hasOption = if (hasOptionVal.toInt() == 0x02) null else hasOptionVal.toInt() != 0x00
        val wishlistStatusVal = `in`.readByte()
        wishlistStatus = if (wishlistStatusVal.toInt() == 0x02) null else wishlistStatusVal.toInt() != 0x00
        stock = `in`.readString()
        minimum = `in`.readString()
        rating = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        dominantColor = `in`.readString()
        href = `in`.readString()

    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productId)
        dest.writeString(thumb)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(price)
        dest.writeString(special)
        dest.writeString(tax)
        if (hasOption == null) {
            dest.writeByte(0x02.toByte())
        } else {
            dest.writeByte((if (hasOption as Boolean) 0x01 else 0x00).toByte())
        }

        if (wishlistStatus == null) {
            dest.writeByte(0x02.toByte())
        } else {
            dest.writeByte((if (wishlistStatus as Boolean) 0x01 else 0x00).toByte())
        }
        dest.writeString(stock)
        dest.writeString(minimum)
        if (rating == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(rating!!)
        }
        dest.writeString(dominantColor)
        dest.writeString(href)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RelatedProduct> = object : Parcelable.Creator<RelatedProduct> {
            override fun createFromParcel(`in`: Parcel): RelatedProduct {
                return RelatedProduct(`in`)
            }

            override fun newArray(size: Int): Array<RelatedProduct?> {
                return arrayOfNulls(size)
            }
        }
    }
}