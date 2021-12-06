package webkul.opencart.mobikul.downLoadInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadDatum {

    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("date_added")
    @Expose
    public String dateAdded;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("download_id")
    @Expose
    public String downloadId;
    @SerializedName("file")
    @Expose
    public String file;
    @SerializedName("extension")
    @Expose
    public String extension;
    @SerializedName("url")
    @Expose
    public String url;

}
