package webkul.opencart.mobikul.model.ProductCategory;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("special")
    @Expose
    private String special;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("dominant_color")
    @Expose
    private String dominantColor;

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }

    public static Creator<Product> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("hasOption")
    @Expose
    private Boolean hasOption;
    @SerializedName("formatted_special")
    @Expose
    String formattedSpecial;

    public String getFormattedSpecial() {
        return formattedSpecial;
    }

    public void setFormattedSpecial(String formattedSpecial) {
        this.formattedSpecial = formattedSpecial;
    }
    @SerializedName("wishlist_status")
    @Expose
    private Boolean wishlist_status;
    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param hasOption
     * @param price
     * @param tax
     * @param minimum
     * @param description
     * @param name
     * @param path
     * @param special
     * @param rating
     * @param thumb
     * @param productId
     */
    public Product(String productId, String thumb, String name, String description, String price, String special, String tax, String minimum, Integer rating, String path, Boolean hasOption,Boolean wishlist_status) {
        super();
        this.productId = productId;
        this.thumb = thumb;
        this.name = name;
        this.description = description;
        this.price = price;
        this.special = special;
        this.tax = tax;
        this.minimum = minimum;
        this.rating = rating;
        this.path = path;
        this.hasOption = hasOption;
        this.wishlist_status = wishlist_status;
    }

    public Boolean getWishlist_status() {
        return wishlist_status;
    }

    public void setWishlist_status(Boolean wishlist_status) {
        this.wishlist_status = wishlist_status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHasOption() {
        return hasOption;
    }

    public void setHasOption(Boolean hasOption) {
        this.hasOption = hasOption;
    }


    protected Product(Parcel in) {
        productId = in.readString();
        thumb = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        special = in.readString();
        tax = in.readString();
        minimum = in.readString();
        rating = in.readByte() == 0x00 ? null : in.readInt();
        path = in.readByte() == 0x00 ? null : in.readString();
        byte hasOptionVal = in.readByte();
        hasOption = hasOptionVal == 0x02 ? null : hasOptionVal != 0x00;
        byte wishlistStatusValue = in.readByte();
        wishlist_status = wishlistStatusValue == 0x02 ? null : wishlistStatusValue != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(thumb);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(special);
        dest.writeString(tax);
        dest.writeString(minimum);
        if (rating == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(rating);
        }
        if (path == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(path);
        }
        if (hasOption == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasOption ? 0x01 : 0x00));
        }
        if (wishlist_status == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (wishlist_status ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
