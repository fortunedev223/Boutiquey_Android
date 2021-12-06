package webkul.opencart.mobikul.model.ProductCategory;

/**
 * Created by manish.choudhary on 15/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModuleData implements Parcelable {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("filter_category")
    @Expose
    private List<String> filterCategory = null;
    @SerializedName("filter_groups")
    @Expose
    private List<FilterGroup> filterGroups = null;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getFilterCategory() {
        return filterCategory;
    }

    public void setFilterCategory(List<String> filterCategory) {
        this.filterCategory = filterCategory;
    }

    public List<FilterGroup> getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(List<FilterGroup> filterGroups) {
        this.filterGroups = filterGroups;
    }


    protected ModuleData(Parcel in) {
        path = in.readString();
        if (in.readByte() == 0x01) {
            filterCategory = new ArrayList<String>();
            in.readList(filterCategory, String.class.getClassLoader());
        } else {
            filterCategory = null;
        }
        if (in.readByte() == 0x01) {
            filterGroups = new ArrayList<FilterGroup>();
            in.readList(filterGroups, FilterGroup.class.getClassLoader());
        } else {
            filterGroups = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        if (filterCategory == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filterCategory);
        }
        if (filterGroups == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filterGroups);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ModuleData> CREATOR = new Parcelable.Creator<ModuleData>() {
        @Override
        public ModuleData createFromParcel(Parcel in) {
            return new ModuleData(in);
        }

        @Override
        public ModuleData[] newArray(int size) {
            return new ModuleData[size];
        }
    };
}