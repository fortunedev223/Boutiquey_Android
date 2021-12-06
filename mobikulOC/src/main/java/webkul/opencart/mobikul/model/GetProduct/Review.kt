package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 14/9/17.
 */
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Review : Parcelable {

    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null

    protected constructor(`in`: Parcel) {
        author = `in`.readString()
        text = `in`.readString()
        rating = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        dateAdded = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(text)
        if (rating == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(rating!!)
        }
        dest.writeString(dateAdded)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(`in`: Parcel): Review {
                return Review(`in`)
            }

            override fun newArray(size: Int): Array<Review?> {
                return arrayOfNulls(size)
            }
        }
    }
}
