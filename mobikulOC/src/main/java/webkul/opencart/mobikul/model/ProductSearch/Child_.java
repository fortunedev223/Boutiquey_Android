package webkul.opencart.mobikul.model.ProductSearch;

/**
 * Created by manish.choudhary on 15/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child_ implements Parcelable {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Child_() {
    }

    /**
     *
     * @param name
     * @param categoryId
     */
    public Child_(String categoryId, String name) {
        super();
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected Child_(Parcel in) {
        categoryId = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Child_> CREATOR = new Parcelable.Creator<Child_>() {
        @Override
        public Child_ createFromParcel(Parcel in) {
            return new Child_(in);
        }

        @Override
        public Child_[] newArray(int size) {
            return new Child_[size];
        }
    };
}
