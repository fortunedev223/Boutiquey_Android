package webkul.opencart.mobikul.model.ManufactureInfoModel;

/**
 * Created by manish.choudhary on 14/8/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Manufacturers implements Parcelable {

    @SerializedName("heading_title")
    @Expose
    private String headingTitle;
    @SerializedName("text_empty")
    @Expose
    private String textEmpty;
    @SerializedName("text_quantity")
    @Expose
    private String textQuantity;
    @SerializedName("text_manufacturer")
    @Expose
    private String textManufacturer;
    @SerializedName("text_model")
    @Expose
    private String textModel;
    @SerializedName("text_price")
    @Expose
    private String textPrice;
    @SerializedName("text_tax")
    @Expose
    private String textTax;
    @SerializedName("text_points")
    @Expose
    private String textPoints;
    @SerializedName("text_compare")
    @Expose
    private String textCompare;
    @SerializedName("text_sort")
    @Expose
    private String textSort;
    @SerializedName("text_limit")
    @Expose
    private String textLimit;
    @SerializedName("button_cart")
    @Expose
    private String buttonCart;
    @SerializedName("button_wishlist")
    @Expose
    private String buttonWishlist;
    @SerializedName("button_compare")
    @Expose
    private String buttonCompare;
    @SerializedName("button_continue")
    @Expose
    private String buttonContinue;
    @SerializedName("button_list")
    @Expose
    private String buttonList;
    @SerializedName("button_grid")
    @Expose
    private String buttonGrid;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("sorts")
    @Expose
    private List<Sort> sorts = null;
    @SerializedName("product_total")
    @Expose
    private String productTotal;
    @SerializedName("limits")
    @Expose
    private List<Limit> limits = null;
    @SerializedName("results")
    @Expose
    private String results;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("limit")
    @Expose
    private String limit;

    /**
     * No args constructor for use in serialization
     *
     */
    public Manufacturers() {
    }

    /**
     *
     * @param sort
     * @param productTotal
     * @param textPoints
     * @param textQuantity
     * @param results
     * @param order
     * @param buttonCompare
     * @param buttonGrid
     * @param products
     * @param buttonList
     * @param buttonWishlist
     * @param limit
     * @param limits
     * @param textManufacturer
     * @param textModel
     * @param textTax
     * @param textSort
     * @param textEmpty
     * @param buttonCart
     * @param textPrice
     * @param headingTitle
     * @param textCompare
     * @param buttonContinue
     * @param textLimit
     * @param sorts
     */
    public Manufacturers(String headingTitle, String textEmpty, String textQuantity, String textManufacturer, String textModel, String textPrice, String textTax, String textPoints, String textCompare, String textSort, String textLimit, String buttonCart, String buttonWishlist, String buttonCompare, String buttonContinue, String buttonList, String buttonGrid, List<Product> products, List<Sort> sorts, String productTotal, List<Limit> limits, String results, String sort, String order, String limit) {
        super();
        this.headingTitle = headingTitle;
        this.textEmpty = textEmpty;
        this.textQuantity = textQuantity;
        this.textManufacturer = textManufacturer;
        this.textModel = textModel;
        this.textPrice = textPrice;
        this.textTax = textTax;
        this.textPoints = textPoints;
        this.textCompare = textCompare;
        this.textSort = textSort;
        this.textLimit = textLimit;
        this.buttonCart = buttonCart;
        this.buttonWishlist = buttonWishlist;
        this.buttonCompare = buttonCompare;
        this.buttonContinue = buttonContinue;
        this.buttonList = buttonList;
        this.buttonGrid = buttonGrid;
        this.products = products;
        this.sorts = sorts;
        this.productTotal = productTotal;
        this.limits = limits;
        this.results = results;
        this.sort = sort;
        this.order = order;
        this.limit = limit;
    }

    public String getHeadingTitle() {
        return headingTitle;
    }

    public void setHeadingTitle(String headingTitle) {
        this.headingTitle = headingTitle;
    }

    public String getTextEmpty() {
        return textEmpty;
    }

    public void setTextEmpty(String textEmpty) {
        this.textEmpty = textEmpty;
    }

    public String getTextQuantity() {
        return textQuantity;
    }

    public void setTextQuantity(String textQuantity) {
        this.textQuantity = textQuantity;
    }

    public String getTextManufacturer() {
        return textManufacturer;
    }

    public void setTextManufacturer(String textManufacturer) {
        this.textManufacturer = textManufacturer;
    }

    public String getTextModel() {
        return textModel;
    }

    public void setTextModel(String textModel) {
        this.textModel = textModel;
    }

    public String getTextPrice() {
        return textPrice;
    }

    public void setTextPrice(String textPrice) {
        this.textPrice = textPrice;
    }

    public String getTextTax() {
        return textTax;
    }

    public void setTextTax(String textTax) {
        this.textTax = textTax;
    }

    public String getTextPoints() {
        return textPoints;
    }

    public void setTextPoints(String textPoints) {
        this.textPoints = textPoints;
    }

    public String getTextCompare() {
        return textCompare;
    }

    public void setTextCompare(String textCompare) {
        this.textCompare = textCompare;
    }

    public String getTextSort() {
        return textSort;
    }

    public void setTextSort(String textSort) {
        this.textSort = textSort;
    }

    public String getTextLimit() {
        return textLimit;
    }

    public void setTextLimit(String textLimit) {
        this.textLimit = textLimit;
    }

    public String getButtonCart() {
        return buttonCart;
    }

    public void setButtonCart(String buttonCart) {
        this.buttonCart = buttonCart;
    }

    public String getButtonWishlist() {
        return buttonWishlist;
    }

    public void setButtonWishlist(String buttonWishlist) {
        this.buttonWishlist = buttonWishlist;
    }

    public String getButtonCompare() {
        return buttonCompare;
    }

    public void setButtonCompare(String buttonCompare) {
        this.buttonCompare = buttonCompare;
    }

    public String getButtonContinue() {
        return buttonContinue;
    }

    public void setButtonContinue(String buttonContinue) {
        this.buttonContinue = buttonContinue;
    }

    public String getButtonList() {
        return buttonList;
    }

    public void setButtonList(String buttonList) {
        this.buttonList = buttonList;
    }

    public String getButtonGrid() {
        return buttonGrid;
    }

    public void setButtonGrid(String buttonGrid) {
        this.buttonGrid = buttonGrid;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public String getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
    }

    public List<Limit> getLimits() {
        return limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }


    protected Manufacturers(Parcel in) {
        headingTitle = in.readString();
        textEmpty = in.readString();
        textQuantity = in.readString();
        textManufacturer = in.readString();
        textModel = in.readString();
        textPrice = in.readString();
        textTax = in.readString();
        textPoints = in.readString();
        textCompare = in.readString();
        textSort = in.readString();
        textLimit = in.readString();
        buttonCart = in.readString();
        buttonWishlist = in.readString();
        buttonCompare = in.readString();
        buttonContinue = in.readString();
        buttonList = in.readString();
        buttonGrid = in.readString();
        if (in.readByte() == 0x01) {
            products = new ArrayList<Product>();
            in.readList(products, Product.class.getClassLoader());
        } else {
            products = null;
        }
        if (in.readByte() == 0x01) {
            sorts = new ArrayList<Sort>();
            in.readList(sorts, Sort.class.getClassLoader());
        } else {
            sorts = null;
        }
        productTotal = in.readString();
        if (in.readByte() == 0x01) {
            limits = new ArrayList<Limit>();
            in.readList(limits, Limit.class.getClassLoader());
        } else {
            limits = null;
        }
        results = in.readString();
        sort = in.readString();
        order = in.readString();
        limit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(headingTitle);
        dest.writeString(textEmpty);
        dest.writeString(textQuantity);
        dest.writeString(textManufacturer);
        dest.writeString(textModel);
        dest.writeString(textPrice);
        dest.writeString(textTax);
        dest.writeString(textPoints);
        dest.writeString(textCompare);
        dest.writeString(textSort);
        dest.writeString(textLimit);
        dest.writeString(buttonCart);
        dest.writeString(buttonWishlist);
        dest.writeString(buttonCompare);
        dest.writeString(buttonContinue);
        dest.writeString(buttonList);
        dest.writeString(buttonGrid);
        if (products == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(products);
        }
        if (sorts == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(sorts);
        }
        dest.writeString(productTotal);
        if (limits == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(limits);
        }
        dest.writeString(results);
        dest.writeString(sort);
        dest.writeString(order);
        dest.writeString(limit);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Manufacturers> CREATOR = new Parcelable.Creator<Manufacturers>() {
        @Override
        public Manufacturers createFromParcel(Parcel in) {
            return new Manufacturers(in);
        }

        @Override
        public Manufacturers[] newArray(int size) {
            return new Manufacturers[size];
        }
    };
}