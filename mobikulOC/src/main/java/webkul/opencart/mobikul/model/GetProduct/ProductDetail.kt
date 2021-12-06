package webkul.opencart.mobikul.model.GetProduct

import android.os.Parcel
import android.os.Parcelable
import android.text.Html

import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ProductDetail : BaseModel, Parcelable {

    @SerializedName("langArray")
    @Expose
    var langArray: LangArray? = null
    @SerializedName("tab_review")
    @Expose
    var tabReview: String? = null
    @SerializedName("product_id")
    @Expose
    var productId: Int? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("manufacturer")
    @Expose
    var manufacturer: String? = null
    @SerializedName("manufacturer_id")
    @Expose
    var manufacturerId: String? = null
    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("reward")
    @Expose
    var reward: String? = null
    @SerializedName("points")
    @Expose
    var points: String? = null
    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("popup")
    @Expose
    var popup: String? = null
    @SerializedName("thumb")
    @Expose
    var thumb: String? = null
    @SerializedName("wishlist_status")
    @Expose
    var wishlistStatus: Boolean? = null
    @SerializedName("images")
    @Expose
    var images: List<Image>? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("special")
    @Expose
    var special: String? = null
    @SerializedName("formatted_special")
    @Expose
    var formattedSpecial: String? = null
    @SerializedName("tax")
    @Expose
    var tax: String? = null
    @SerializedName("discounts")
    @Expose
    var discounts: List<Discount>? = null
    @SerializedName("options")
    @Expose
    var options: List<Option>? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("minimum")
    @Expose
    var minimum: String? = "1"
    @SerializedName("review_status")
    @Expose
    var reviewStatus: String? = null
    @SerializedName("review_guest")
    @Expose
    var reviewGuest: Boolean? = null
    @SerializedName("customer_name")
    @Expose
    var customerName: String? = null
    @SerializedName("reviews")
    @Expose
    var reviews: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Int = 0
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("attribute_groups")
    @Expose
    var attributeGroups: List<AttributeGroup>? = null
    @SerializedName("relatedProducts")
    @Expose
    var relatedProducts: List<RelatedProduct>? = null
    @SerializedName("tags")
    @Expose
    var tags: List<Any>? = null
    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("text_payment_recurring")
    @Expose
    var textPaymentRecurring: String? = null
    @SerializedName("text_minimum")
    @Expose
    var text_minimum: String? = ""
    @SerializedName("recurrings")
    @Expose
    var recurrings: List<Any>? = null
    @SerializedName("reviewData")
    @Expose
    var reviewData: ReviewData? = null
    @SerializedName("productPrev")
    @Expose
    var productPrev: List<ProductPrev>? = null
    @SerializedName("productNext")
    @Expose
    var productNext: List<ProductNext>? = null

    @SerializedName("gdpr_status")
    @Expose
    var gdprStatus: Int? = 101

    @SerializedName("gdpr_content")
    @Expose
    var gdprContent: String? = null


    fun getName(): String {
        return Html.fromHtml(name).toString()
    }

    fun setName(name: String) {
        this.name = name
    }

    fun showReview(): String {
        if (reviewData != null) {
            if (reviewData!!.reviews != null) {
                return """(${reviewData!!.reviews!!.size}) Reviews |"""
            }
        } else {
            return ""
        }
        return ""
    }

    @SerializedName("dominant_color")
    @Expose
    var dominantColor: String? = null

    protected constructor(`in`: Parcel) {
        langArray = `in`.readValue(LangArray::class.java.classLoader) as LangArray
        tabReview = `in`.readString()
        productId = if (`in`.readByte().toInt() == 0x00) null else `in`.readInt()
        name = `in`.readString()
        dominantColor = `in`.readString()
        manufacturer = `in`.readString()
        manufacturerId = `in`.readString()
        model = `in`.readString()
        reward = `in`.readString()
        points = `in`.readString()
        stock = `in`.readString()
        popup = `in`.readString()
        thumb = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            images = ArrayList()
            `in`.readList(images, Image::class.java.classLoader)
        } else {
            images = null
        }
        price = `in`.readString()
        special = `in`.readString()
        tax = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            discounts = ArrayList()
            `in`.readList(discounts, Discount::class.java.classLoader)
        } else {
            discounts = null
        }
        if (`in`.readByte().toInt() == 0x01) {
            options = ArrayList()
            `in`.readList(options, Option::class.java.classLoader)
        } else {
            options = null
        }
        quantity = `in`.readString()
        minimum = `in`.readString()
        reviewStatus = `in`.readString()
        val reviewGuestVal = `in`.readByte()
        reviewGuest = if (reviewGuestVal.toInt() == 0x02) null else reviewGuestVal.toInt() != 0x00
        customerName = `in`.readString()
        reviews = `in`.readString()
        rating = if (`in`.readByte().toInt() == 0x00) 0 else `in`.readInt()
        description = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            attributeGroups = ArrayList()
            `in`.readList(attributeGroups, AttributeGroup::class.java.classLoader)
        } else {
            attributeGroups = null
        }
        if (`in`.readByte().toInt() == 0x01) {
            relatedProducts = ArrayList()
            `in`.readList(relatedProducts, RelatedProduct::class.java.classLoader)
        } else {
            relatedProducts = null
        }
        if (`in`.readByte().toInt() == 0x01) {
            tags = ArrayList()
            `in`.readList(tags, Any::class.java.classLoader)
        } else {
            tags = null
        }
        href = `in`.readString()
        textPaymentRecurring = `in`.readString()
        text_minimum = `in`.readString()
        if (`in`.readByte().toInt() == 0x01) {
            recurrings = ArrayList()
            `in`.readList(recurrings, Any::class.java.classLoader)
        } else {
            recurrings = null
        }
        reviewData = `in`.readValue(ReviewData::class.java.classLoader) as ReviewData
        if (`in`.readByte().toInt() == 0x01) {
            productPrev = ArrayList()
            `in`.readList(productPrev, ProductPrev::class.java.classLoader)
        } else {
            productPrev = null
        }
        if (`in`.readByte().toInt() == 0x01) {
            productNext = ArrayList()
            `in`.readList(productNext, ProductNext::class.java.classLoader)
        } else {
            productNext = null
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(langArray)
        dest.writeString(tabReview)
        if (productId == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(productId!!)
        }
        dest.writeString(name)
        dest.writeString(dominantColor)
        dest.writeString(manufacturer)
        dest.writeString(manufacturerId)
        dest.writeString(model)
        dest.writeString(reward)
        dest.writeString(points)
        dest.writeString(stock)
        dest.writeString(popup)
        dest.writeString(thumb)
        if (images == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(images)
        }
        dest.writeString(price)
        dest.writeString(special)
        dest.writeString(tax)
        if (discounts == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(discounts)
        }
        if (options == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(options)
        }
        dest.writeString(quantity)
        dest.writeString(minimum)
        dest.writeString(reviewStatus)
        if (reviewGuest == null) {
            dest.writeByte(0x02.toByte())
        } else {
            dest.writeByte((if (reviewGuest as Boolean) 0x01 else 0x00).toByte())
        }
        dest.writeString(customerName)
        dest.writeString(reviews)
        if (rating == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeInt(rating!!)
        }
        dest.writeString(description)
        if (attributeGroups == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(attributeGroups)
        }
        if (relatedProducts == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(relatedProducts)
        }
        if (tags == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(tags)
        }
        dest.writeString(href)
        dest.writeString(textPaymentRecurring)
        dest.writeString(text_minimum)
        if (recurrings == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(recurrings)
        }
        dest.writeValue(reviewData)
        if (productPrev == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(productPrev)
        }
        if (productNext == null) {
            dest.writeByte(0x00.toByte())
        } else {
            dest.writeByte(0x01.toByte())
            dest.writeList(productNext)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProductDetail> = object : Parcelable.Creator<ProductDetail> {
            override fun createFromParcel(`in`: Parcel): ProductDetail {
                return ProductDetail(`in`)
            }

            override fun newArray(size: Int): Array<ProductDetail?> {
                return arrayOfNulls(size)
            }
        }
    }
}