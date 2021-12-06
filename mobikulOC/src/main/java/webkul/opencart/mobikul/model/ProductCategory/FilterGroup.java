package webkul.opencart.mobikul.model.ProductCategory;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish.choudhary on 14/6/18.
 */

public class FilterGroup implements Parcelable {

    @SerializedName("filter_group_id")
    @Expose
    private String filterGroupId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("filter")
    @Expose
    private List<Filter> filter = null;

    public String getFilterGroupId() {
        return filterGroupId;
    }

    public void setFilterGroupId(String filterGroupId) {
        this.filterGroupId = filterGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Filter> getFilter() {
        return filter;
    }

    public void setFilter(List<Filter> filter) {
        this.filter = filter;
    }


    protected FilterGroup(Parcel in) {
        filterGroupId = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            filter = new ArrayList<Filter>();
            in.readList(filter, Filter.class.getClassLoader());
        } else {
            filter = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filterGroupId);
        dest.writeString(name);
        if (filter == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filter);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FilterGroup> CREATOR = new Parcelable.Creator<FilterGroup>() {
        @Override
        public FilterGroup createFromParcel(Parcel in) {
            return new FilterGroup(in);
        }

        @Override
        public FilterGroup[] newArray(int size) {
            return new FilterGroup[size];
        }
    };
}