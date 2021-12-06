package webkul.opencart.mobikul.model.ViewMoreFeaturedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;
import webkul.opencart.mobikul.model.GetHomePage.Featured;
import webkul.opencart.mobikul.model.ProductCategory.Sort;

public class ViewMoreFeatured extends BaseModel {
    @SerializedName("sorts")
    @Expose
    private List<Sort> sorts = null;
    @SerializedName("featured")
    @Expose
    private List<Featured> featured = null;
    @SerializedName("product_total")
    @Expose
    private int productTotal;


    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public List<Featured> getFeatured() {
        return featured;
    }

    public void setFeatured(List<Featured> featured) {
        this.featured = featured;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }
}
