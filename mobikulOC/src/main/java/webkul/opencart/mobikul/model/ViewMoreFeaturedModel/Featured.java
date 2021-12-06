package webkul.opencart.mobikul.model.ViewMoreFeaturedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Featured {
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
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("special")
    @Expose
    private boolean special;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("hasOption")
    @Expose
    private boolean hasOption;
    @SerializedName("wishlist_status")
    @Expose
    private boolean wishlistStatus;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isHasOption() {
        return hasOption;
    }

    public void setHasOption(boolean hasOption) {
        this.hasOption = hasOption;
    }

    public boolean isWishlistStatus() {
        return wishlistStatus;
    }

    public void setWishlistStatus(boolean wishlistStatus) {
        this.wishlistStatus = wishlistStatus;
    }
}
