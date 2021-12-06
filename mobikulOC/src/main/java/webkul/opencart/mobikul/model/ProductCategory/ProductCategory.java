package webkul.opencart.mobikul.model.ProductCategory;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class ProductCategory extends BaseModel implements Parcelable {

    @SerializedName("categoryData")
    @Expose
    private CategoryData categoryData;
    @SerializedName("moduleData")
    @Expose
    private ModuleData moduleData;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductCategory() {
    }

    /**
     *
     * @param categoryData
     * @param moduleData
     */
    public ProductCategory(CategoryData categoryData, ModuleData moduleData) {
        super();
        this.categoryData = categoryData;
        this.moduleData = moduleData;
    }

    public CategoryData getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(CategoryData categoryData) {
        this.categoryData = categoryData;
    }

    public ModuleData getModuleData() {
        return moduleData;
    }

    public void setModuleData(ModuleData moduleData) {
        this.moduleData = moduleData;
    }


    protected ProductCategory(Parcel in) {
        moduleData = (ModuleData) in.readValue(ModuleData.class.getClassLoader());
        categoryData = (CategoryData) in.readValue(CategoryData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(moduleData);
        dest.writeValue(categoryData);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductCategory> CREATOR = new Parcelable.Creator<ProductCategory>() {
        @Override
        public ProductCategory createFromParcel(Parcel in) {
            return new ProductCategory(in);
        }

        @Override
        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };
}