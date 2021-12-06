package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 3/8/17.
 */

import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AttributeGroup : Parcelable {

    @SerializedName("attribute_group_id")
    @Expose
    var attributeGroupId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("attribute")
    @Expose
    var attribute: List<Attribute>? = null

    protected constructor(`in`: Parcel) {
        attributeGroupId = `in`.readString()
        name = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            attribute = ArrayList()
            `in`.readList(attribute, Attribute::class.java.classLoader)
        } else {
            attribute = null
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(attributeGroupId)
        dest.writeString(name)
        if (attribute == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(attribute)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AttributeGroup> = object : Parcelable.Creator<AttributeGroup> {
            override fun createFromParcel(`in`: Parcel): AttributeGroup {
                return AttributeGroup(`in`)
            }

            override fun newArray(size: Int): Array<AttributeGroup?> {
                return arrayOfNulls(size)
            }
        }
    }
}