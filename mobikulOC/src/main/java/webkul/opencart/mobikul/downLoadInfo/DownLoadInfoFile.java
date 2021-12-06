package webkul.opencart.mobikul.downLoadInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class DownLoadInfoFile extends BaseModel {

    @SerializedName("downloadData")
    @Expose
    public List<DownloadDatum> downloadData = null;

    @SerializedName("downloadsTotal")
    @Expose
    public String downloadsTotal;

}
