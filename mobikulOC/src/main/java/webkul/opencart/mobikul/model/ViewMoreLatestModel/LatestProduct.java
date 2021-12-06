package webkul.opencart.mobikul.model.ViewMoreLatestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestProduct {

    @SerializedName("dominant_color")
    @Expose
    private String dominant_color;

    public String getDominant_color() {
        return dominant_color;
    }

    public void setDominant_color(String dominant_color) {
        this.dominant_color = dominant_color;
    }

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("special")
    @Expose
    private int special;
    @SerializedName("formatted_special")
    @Expose
    private String formattedSpecial;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("reviews")
    @Expose
    private String reviews;
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

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public String getFormattedSpecial() {
        return formattedSpecial;
    }

    public void setFormattedSpecial(String formattedSpecial) {
        this.formattedSpecial = formattedSpecial;
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

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
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
