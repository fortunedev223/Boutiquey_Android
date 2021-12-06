package webkul.opencart.mobikul.model.ManufactureInfoModel;

/**
 * Created by manish.choudhary on 14/8/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class Manufacture extends BaseModel implements Parcelable {

    @SerializedName("manufacturers")
    @Expose
    private Manufacturers manufacturers;


    /**
     * No args constructor for use in serialization
     *
     */
    public Manufacture() {
    }

    /**
     *
     * @param manufacturers
     */
    public Manufacture(Manufacturers manufacturers) {
        super();
        this.manufacturers = manufacturers;
    }

    public Manufacturers getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Manufacturers manufacturers) {
        this.manufacturers = manufacturers;
    }


    protected Manufacture(Parcel in) {
        manufacturers = (Manufacturers) in.readValue(Manufacturers.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(manufacturers);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Manufacture> CREATOR = new Parcelable.Creator<Manufacture>() {
        @Override
        public Manufacture createFromParcel(Parcel in) {
            return new Manufacture(in);
        }

        @Override
        public Manufacture[] newArray(int size) {
            return new Manufacture[size];
        }
    };
}
