package webkul.opencart.mobikul.model.GetProduct

/**
 * Created by manish.choudhary on 3/8/17.
 */
import android.os.Parcel
import android.os.Parcelable

import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Option : Parcelable {

    @SerializedName("product_option_id")
    @Expose
    var productOptionId: String? = null
    @SerializedName("product_option_value")
    @Expose
    var productOptionValue: List<ProductOptionValue>? = null
    @SerializedName("option_id")
    @Expose
    var optionId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null
    @SerializedName("required")
    @Expose
    var required: String? = null


    protected constructor(`in`: Parcel) {
        productOptionId = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            productOptionValue = ArrayList()
            `in`.readList(productOptionValue, ProductOptionValue::class.java.classLoader)
        } else {
            productOptionValue = null
        }
        optionId = `in`.readString()
        name = `in`.readString()
        type = `in`.readString()
        value = `in`.readString()
        required = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productOptionId)
        if (productOptionValue == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(productOptionValue)
        }
        dest.writeString(optionId)
        dest.writeString(name)
        dest.writeString(type)
        dest.writeString(value)
        dest.writeString(required)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Option> = object : Parcelable.Creator<Option> {
            override fun createFromParcel(`in`: Parcel): Option {
                return Option(`in`)
            }

            override fun newArray(size: Int): Array<Option?> {
                return arrayOfNulls(size)
            }
        }
    }
}