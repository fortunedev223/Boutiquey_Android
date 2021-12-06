package webkul.opencart.mobikul.model.ProductSearch;

/**
 * Created by manish.choudhary on 15/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Child implements Parcelable {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("children")
    @Expose
    private List<Child_> children = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Child() {
    }

    /**
     *
     * @param name
     * @param categoryId
     * @param children
     */
    public Child(String categoryId, String name, List<Child_> children) {
        super();
        this.categoryId = categoryId;
        this.name = name;
        this.children = children;
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

    public List<Child_> getChildren() {
        return children;
    }

    public void setChildren(List<Child_> children) {
        this.children = children;
    }


    protected Child(Parcel in) {
        categoryId = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            children = new ArrayList<Child_>();
            in.readList(children, Child_.class.getClassLoader());
        } else {
            children = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(name);
        if (children == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(children);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Child> CREATOR = new Parcelable.Creator<Child>() {
        @Override
        public Child createFromParcel(Parcel in) {
            return new Child(in);
        }

        @Override
        public Child[] newArray(int size) {
            return new Child[size];
        }
    };
}
