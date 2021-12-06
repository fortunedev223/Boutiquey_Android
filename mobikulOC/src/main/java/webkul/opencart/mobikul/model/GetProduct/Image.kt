package webkul.opencart.mobikul.model.GetProduct

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image : Parcelable {

    @SerializedName("popup")
    @Expose
    var popup: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null

    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null

    protected constructor(`in`: Parcel) {
        popup = `in`.readString()
        thumb = `in`.readString()
        dominantColor=`in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(popup)
        dest.writeString(thumb)
        dest.writeString(dominantColor)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(`in`: Parcel): Image {
                return Image(`in`)
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }
        }
    }
}