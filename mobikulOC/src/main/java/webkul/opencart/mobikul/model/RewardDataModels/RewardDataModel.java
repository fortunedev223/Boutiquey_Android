package webkul.opencart.mobikul.model.RewardDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardDataModel {
    @SerializedName("order_id")
    @Expose
    public String order_id;

    @SerializedName("points")
    @Expose
    public String points;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("date_added")
    @Expose
    public String date_added;

}
