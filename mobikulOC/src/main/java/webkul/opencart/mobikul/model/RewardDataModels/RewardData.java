package webkul.opencart.mobikul.model.RewardDataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class RewardData extends BaseModel {

    @SerializedName("rewardData")
    @Expose
    public List<RewardDataModel> rewardData;

    @SerializedName("rewardsTotal")
    @Expose
    public String rewardsTotal;

    @SerializedName("rewardText")
    @Expose
    public String rewardText;

    @SerializedName("totalPoints")
    @Expose
    public String totalPoints;
}

