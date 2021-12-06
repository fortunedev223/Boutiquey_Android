package webkul.opencart.mobikul.model.ManufactureInfoModel;

/**
 * Created by manish.choudhary on 14/8/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Limit implements Parcelable {

    @SerializedName("text")
    @Expose
    private Integer text;
    @SerializedName("value")
    @Expose
    private Integer value;

    /**
     * No args constructor for use in serialization
     *
     */
    public Limit() {
    }

    /**
     *
     * @param text
     * @param value
     */
    public Limit(Integer text, Integer value) {
        super();
        this.text = text;
        this.value = value;
    }

    public Integer getText() {
        return text;
    }

    public void setText(Integer text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    protected Limit(Parcel in) {
        text = in.readByte() == 0x00 ? null : in.readInt();
        value = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (text == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(text);
        }
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Limit> CREATOR = new Parcelable.Creator<Limit>() {
        @Override
        public Limit createFromParcel(Parcel in) {
            return new Limit(in);
        }

        @Override
        public Limit[] newArray(int size) {
            return new Limit[size];
        }
    };
}
