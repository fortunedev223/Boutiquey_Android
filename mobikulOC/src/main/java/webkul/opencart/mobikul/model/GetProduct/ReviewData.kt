package webkul.opencart.mobikul.model.GetProduct

import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReviewData : Parcelable {

    @SerializedName("text_no_reviews")
    @Expose
    var textNoReviews: String? = null
    @SerializedName("reviews")
    @Expose
    var reviews: List<Review>? = null

    protected constructor(`in`: Parcel) {
        textNoReviews = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            reviews = ArrayList()
            `in`.readList(reviews, Review::class.java.classLoader)
        } else {
            reviews = null
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(textNoReviews)
        if (reviews == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(reviews)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReviewData> = object : Parcelable.Creator<ReviewData> {
            override fun createFromParcel(`in`: Parcel): ReviewData {
                return ReviewData(`in`)
            }

            override fun newArray(size: Int): Array<ReviewData?> {
                return arrayOfNulls(size)
            }
        }
    }
}


