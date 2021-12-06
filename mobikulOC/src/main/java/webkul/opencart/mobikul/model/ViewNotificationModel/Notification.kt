package webkul.opencart.mobikul.model.ViewNotificationModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Notification {

    @SerializedName("notification_id")
    @Expose
    var notificationId: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("subTitle")
    @Expose
    var subTitle: String? = null
}

