package webkul.opencart.mobikul.model.VIewCartModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by manish.choudhary on 20/12/17.
 */

class Reward_ {

    @SerializedName("heading_title")
    @Expose
    var headingTitle: String? = null
    @SerializedName("text_loading")
    @Expose
    var textLoading: String? = null
    @SerializedName("entry_reward")
    @Expose
    var entryReward: String? = null
    @SerializedName("button_reward")
    @Expose
    var buttonReward: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("reward")
    @Expose
    var reward: String? = null

}
