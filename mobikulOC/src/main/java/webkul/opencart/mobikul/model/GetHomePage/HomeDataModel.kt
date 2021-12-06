package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class HomeDataModel : BaseModel() {
    @SerializedName("gdpr_status")
    @Expose
    var gdprStatus: Int = 1
    @SerializedName("gdpr_notification_status")
    @Expose
    var gdprNotificationStatus: Int = 0
    @SerializedName("gdpr_notification_description")
    @Expose
    var gdprDescription: String? = ""
    @SerializedName("guest_status")
    @Expose
    var isGuestStatus: Boolean = false
    @SerializedName("banners")
    @Expose
    var banners: ArrayList<Banner>? = null
    @SerializedName("modules")
    @Expose
    var modules: ArrayList<Modules>? = null
    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null
    @SerializedName("languages")
    @Expose
    var languages: Languages? = null
    @SerializedName("currencies")
    @Expose
    var currencies: Currencies? = null
    @SerializedName("cart")
    @Expose
    var cart: Int? = 0
    @SerializedName("language")
    @Expose
    var language: String? = null
    @SerializedName("banner_status")
    @Expose
    var bannerStatus: String? = null
    @SerializedName("notification_status")
    @Expose
    var notificationStatus: String? = null
    @SerializedName("carousel_status")
    @Expose
    var carouselStatus: String? = null
    @SerializedName("carousel")
    @Expose
    var carousalList: ArrayList<Carousel>? = null
    @SerializedName("featured_status")
    @Expose
    var featuredStatus: String? = null
    @SerializedName("latestProducts")
    @Expose
    var latestProducts: LatestProducts? = null

    @SerializedName("footerMenu")
    @Expose
    var footerMenu: ArrayList<FooterMenu>? = null

}
