package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 3/8/17.
 */

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Attribute : Parcelable {

    @SerializedName("attribute_id")
    @Expose
    var attributeId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null


    protected constructor(`in`: Parcel) {
        attributeId = `in`.readString()
        name = `in`.readString()
        text = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(attributeId)
        dest.writeString(name)
        dest.writeString(text)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Attribute> = object : Parcelable.Creator<Attribute> {
            override fun createFromParcel(`in`: Parcel): Attribute {
                return Attribute(`in`)
            }

            override fun newArray(size: Int): Array<Attribute?> {
                return arrayOfNulls(size)
            }
        }
    }
}
