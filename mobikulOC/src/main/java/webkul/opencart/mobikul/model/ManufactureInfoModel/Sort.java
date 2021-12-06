package webkul.opencart.mobikul.model.ManufactureInfoModel;

/**
 * Created by manish.choudhary on 14/8/17.
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
     * @param value
     */
    public Sort(String text, String value, String order) {
        super();
        this.text = text;
        this.value = value;
        this.order = order;
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


    protected Sort(Parcel in) {
        text = in.readString();
        value = in.readString();
        order = in.readString();
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

