package webkul.opencart.mobikul;

import android.graphics.drawable.ColorDrawable;
import android.text.Html;

public class Product {
    private String typeId;
    public String id;
    public String productName;
    public String img_url;
    public String price;
    public String btnTitle;
    public String discount;
    public String wishlist;

    public String getShortDescription() {
        return String.valueOf(webkul.opencart.mobikul.helper.Utils.fromHtml(shortDescription));
    }

    public String shortDescription;
    public String max;
    public String min;
    public String priceView;
    public String range;
    public String model;
    public String productId;
    public String inStock;
    public String linksPurchasedSeparately;
    public String sellerString = "";
    private boolean hasOptions;
    private boolean wishlist_status;
    private String formatedSpecialPrice;
    private String formatedSpecial;

    public String getFormatedSpecial() {
        return formatedSpecial;
    }

    public void setFormatedSpecial(String formatedSpecial) {
        this.formatedSpecial = formatedSpecial;
    }

    public boolean isWishlist_status() {
        return wishlist_status;
    }

    public void setWishlist_status(boolean wishlist_status) {
        this.wishlist_status = wishlist_status;
    }

    public String getSellerString() {
        return sellerString;
    }

    public String isFormatedSpecialPrice() {
        return formatedSpecialPrice;
    }

    public void setFormatedSpecialPrice(String formatedSpecialPrice) {
        this.formatedSpecialPrice = formatedSpecialPrice;
    }

    private int rating = 0;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSellerString(String sellerString) {
        this.sellerString = sellerString;
    }

    ColorDrawable img_bitmap;

    public String getLinksPurchasedSeparately() {
        return linksPurchasedSeparately;
    }

    public void setLinksPurchasedSeparately(String linksPurchasedSeparately) {
        this.linksPurchasedSeparately = linksPurchasedSeparately;
    }

    public ColorDrawable getImg_bitmap() {
        return img_bitmap;
    }

    public void setImg_bitmap(ColorDrawable img_bitmap) {
        this.img_bitmap = img_bitmap;
    }

    String dominantColor;

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }

    public Product(String p_productName, String p_img_url, String p_price,
                   String p_shortDescription, int p_rating, String p_btnTitle,
                   String p_wishlist, String p_discount, String p_min, String p_max,
                   String p_priceView, String p_formatedSpecialPrice, String p_range,
                   String p_model, String p_productId,
                   boolean p_hasOptions, boolean wishlist_status,
                   String p_isStock, String formatedSpecial,
                   String dominantColor) {

        this.dominantColor = dominantColor;
        productName = p_productName;
        img_url = p_img_url;
        price = p_price;
        shortDescription = p_shortDescription;
        rating = p_rating;
        btnTitle = p_btnTitle;
        wishlist = p_wishlist;
        discount = p_discount;
        min = p_min;
        max = p_max;
        priceView = p_priceView;
        formatedSpecialPrice = p_formatedSpecialPrice;
        range = p_range;
        model = p_model;
        productId = p_productId;
        hasOptions = p_hasOptions;
        this.wishlist_status = wishlist_status;
        inStock = p_isStock;
        this.formatedSpecial = formatedSpecial;
    }

    public boolean isHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    public String getProductName() {
        return String.valueOf(Html.fromHtml(productName));
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}


