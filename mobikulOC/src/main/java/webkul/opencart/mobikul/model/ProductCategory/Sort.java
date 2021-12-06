package webkul.opencart.mobikul.model.ProductCategory;

/**
 * Created by manish.choudhary on 15/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sort implements Parcelable {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("path")
    @Expose
    private String path;

    /**
     * No args constructor for use in serialization
     *
     */
    public Sort() {
    }

    /**
     *
     * @param text
     * @param order
     * @param path
     * @param value
     */
    public Sort(String text, String value, String order, String path) {
        super();
        this.text = text;
        this.value = value;
        this.order = order;
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    protected Sort(Parcel in) {
        text = in.readString();
        value = in.readString();
        order = in.readString();
        path =in.readByte() == 0x00 ? null : in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(value);
        dest.writeString(order);
        if (path == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(path);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sort> CREATOR = new Parcelable.Creator<Sort>() {
        @Override
        public Sort createFromParcel(Parcel in) {
            return new Sort(in);
        }

        @Override
        public Sort[] newArray(int size) {
            return new Sort[size];
        }
    };
}