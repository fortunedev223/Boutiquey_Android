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

public class CategoryData implements Parcelable {

    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("product_total")
    @Expose
    private String productTotal;
    @SerializedName("sorts")
    @Expose
    private List<Sort> sorts = null;

    /**
     * No args constructor for use in serialization
     */
    public CategoryData() {
    }

    /**
     * @param description
     * @param productTotal
     * @param categories
     * @param sorts
     * @param products
     * @param thumb
     */
    public CategoryData(String thumb, String description, List<Category> categories, List<Product> products, String productTotal, List<Sort> sorts) {
        super();
        this.thumb = thumb;
        this.description = description;
        this.categories = categories;
        this.products = products;
        this.productTotal = productTotal;
        this.sorts = sorts;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }


    protected CategoryData(Parcel in) {
        thumb = in.readString();
        description = in.readString();
        if (in.readByte() == 0x01) {
            categories = new ArrayList<Category>();
            in.readList(categories, Category.class.getClassLoader());
        } else {
            categories = null;
        }
        if (in.readByte() == 0x01) {
            products = new ArrayList<Product>();
            in.readList(products, Product.class.getClassLoader());
        } else {
            products = null;
        }
        productTotal = in.readString();
        if (in.readByte() == 0x01) {
            sorts = new ArrayList<Sort>();
            in.readList(sorts, Sort.class.getClassLoader());
        } else {
            sorts = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumb);
        dest.writeString(description);
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        if (products == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(products);
        }
        dest.writeString(productTotal);
        if (sorts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sorts);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CategoryData> CREATOR = new Parcelable.Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel in) {
            return new CategoryData(in);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };
}