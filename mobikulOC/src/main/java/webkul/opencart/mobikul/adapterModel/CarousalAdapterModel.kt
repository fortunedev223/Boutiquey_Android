package webkul.opencart.mobikul.adapterModel

import android.os.Parcel
import android.os.Parcelable


/**
 * Webkul Software. *
 * @author Webkul
 * @Mobikul
 * @OpencartMobikul
 * @copyright Copyright (c) 2010-2018 Webkul Software Private Limited (https://webkul.com)
 * @license https://store.webkul.com/license.html
 */
class CarousalAdapterModel : Parcelable {
    var title: String? = null
    var imgUrl: String? = null
    var dominantColor: String? = null
    var link = ""

    constructor(title: String, imgUrl: String, link: String, dominantColor: String?) {
        this.title = title
        this.imgUrl = imgUrl
        this.link = link
        this.dominantColor = dominantColor
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        imgUrl = `in`.readString()
        link = `in`.readString()
        dominantColor=`in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(imgUrl)
        dest.writeString(link)
        dest.writeString(dominantColor)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<CarousalAdapterModel> = object : Parcelable.Creator<CarousalAdapterModel> {
            override fun createFromParcel(`in`: Parcel): CarousalAdapterModel {
                return CarousalAdapterModel(`in`)
            }

            override fun newArray(size: Int): Array<CarousalAdapterModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}