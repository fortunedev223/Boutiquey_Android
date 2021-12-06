package webkul.opencart.mobikul.model.GetHomePage

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Child {



    @SerializedName("name")
    @Expose
    public var name: String? = null
    @SerializedName("children")
    @Expose
    public var children: List<Child>? = null
    @SerializedName("column")
    @Expose
    public var column: String? = null
    @SerializedName("path")
    @Expose
    public var path: String? = null
    @SerializedName("image")
    @Expose
    public var image: String? = null
    @SerializedName("thumb")
    @Expose
    public var thumb: String? = null
    @SerializedName("icon")
    @Expose
    public var icon: String? = null
}