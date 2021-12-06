package webkul.opencart.mobikul.model.ViewNotificationModel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class ViewNotification : BaseModel() {

    @SerializedName("notifications")
    @Expose
    var notifications: List<Notification>? = null

}
