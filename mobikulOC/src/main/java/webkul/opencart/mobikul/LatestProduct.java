package webkul.opencart.mobikul;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LatestProduct {

    @SerializedName("products")
    @Expose
    private List<Products> products = null;

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
