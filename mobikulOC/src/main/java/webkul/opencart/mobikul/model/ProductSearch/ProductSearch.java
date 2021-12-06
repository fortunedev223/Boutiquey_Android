package webkul.opencart.mobikul.model.ProductSearch;

/**
 * Created by manish.choudhary on 15/7/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import webkul.opencart.mobikul.model.BaseModel.BaseModel;

public class ProductSearch extends BaseModel implements Parcelable {

    @SerializedName("heading_title")
    @Expose
    private String headingTitle;
    @SerializedName("text_empty")
    @Expose
    private String textEmpty;
    @SerializedName("text_search")
    @Expose
    private String textSearch;
    @SerializedName("text_keyword")
    @Expose
    private String textKeyword;
    @SerializedName("text_category")
    @Expose
    private String textCategory;
    @SerializedName("text_sub_category")
    @Expose
    private String textSubCategory;
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
    @SerializedName("entry_search")
    @Expose
    private String entrySearch;
    @SerializedName("entry_description")
    @Expose
    private String entryDescription;
    @SerializedName("button_search")
    @Expose
    private String buttonSearch;
    @SerializedName("button_cart")
    @Expose
    private String buttonCart;
    @SerializedName("button_wishlist")
    @Expose
    private String buttonWishlist;
    @SerializedName("button_compare")
    @Expose
    private String buttonCompare;
    @SerializedName("button_list")
    @Expose
    private String buttonList;
    @SerializedName("button_grid")
    @Expose
    private String buttonGrid;
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
    @SerializedName("limits")
    @Expose
    private List<Limit> limits = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductSearch() {
    }

    /**
     *
     * @param productTotal
     * @param textSubCategory
     * @param textPoints
     * @param textQuantity
     * @param textSearch
     * @param entrySearch
     * @param buttonCompare
     * @param buttonGrid
     * @param products
     * @param buttonList
     * @param buttonWishlist
     * @param limits
     * @param buttonSearch
     * @param textManufacturer
     * @param textModel
     * @param textCategory
     * @param textTax
     * @param textSort
     * @param textEmpty
     * @param buttonCart
     * @param textPrice
     * @param headingTitle
     * @param textCompare
     * @param textLimit
     * @param entryDescription
     * @param categories
     * @param sorts
     * @param textKeyword
     */
    public ProductSearch(String headingTitle, String textEmpty, String textSearch, String textKeyword, String textCategory, String textSubCategory, String textQuantity, String textManufacturer, String textModel, String textPrice, String textTax, String textPoints, String textCompare, String textSort, String textLimit, String entrySearch, String entryDescription, String buttonSearch, String buttonCart, String buttonWishlist, String buttonCompare, String buttonList, String buttonGrid, List<Category> categories, List<Product> products, String productTotal, List<Sort> sorts, List<Limit> limits) {
        super();
        this.headingTitle = headingTitle;
        this.textEmpty = textEmpty;
        this.textSearch = textSearch;
        this.textKeyword = textKeyword;
        this.textCategory = textCategory;
        this.textSubCategory = textSubCategory;
        this.textQuantity = textQuantity;
        this.textManufacturer = textManufacturer;
        this.textModel = textModel;
        this.textPrice = textPrice;
        this.textTax = textTax;
        this.textPoints = textPoints;
        this.textCompare = textCompare;
        this.textSort = textSort;
        this.textLimit = textLimit;
        this.entrySearch = entrySearch;
        this.entryDescription = entryDescription;
        this.buttonSearch = buttonSearch;
        this.buttonCart = buttonCart;
        this.buttonWishlist = buttonWishlist;
        this.buttonCompare = buttonCompare;
        this.buttonList = buttonList;
        this.buttonGrid = buttonGrid;
        this.categories = categories;
        this.products = products;
        this.productTotal = productTotal;
        this.sorts = sorts;
        this.limits = limits;
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

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getTextKeyword() {
        return textKeyword;
    }

    public void setTextKeyword(String textKeyword) {
        this.textKeyword = textKeyword;
    }

    public String getTextCategory() {
        return textCategory;
    }

    public void setTextCategory(String textCategory) {
        this.textCategory = textCategory;
    }

    public String getTextSubCategory() {
        return textSubCategory;
    }

    public void setTextSubCategory(String textSubCategory) {
        this.textSubCategory = textSubCategory;
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

    public String getEntrySearch() {
        return entrySearch;
    }

    public void setEntrySearch(String entrySearch) {
        this.entrySearch = entrySearch;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public String getButtonSearch() {
        return buttonSearch;
    }

    public void setButtonSearch(String buttonSearch) {
        this.buttonSearch = buttonSearch;
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

    public List<Limit> getLimits() {
        return limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }



    protected ProductSearch(Parcel in) {
        headingTitle = in.readString();
        textEmpty = in.readString();
        textSearch = in.readString();
        textKeyword = in.readString();
        textCategory = in.readString();
        textSubCategory = in.readString();
        textQuantity = in.readString();
        textManufacturer = in.readString();
        textModel = in.readString();
        textPrice = in.readString();
        textTax = in.readString();
        textPoints = in.readString();
        textCompare = in.readString();
        textSort = in.readString();
        textLimit = in.readString();
        entrySearch = in.readString();
        entryDescription = in.readString();
        buttonSearch = in.readString();
        buttonCart = in.readString();
        buttonWishlist = in.readString();
        buttonCompare = in.readString();
        buttonList = in.readString();
        buttonGrid = in.readString();
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
        if (in.readByte() == 0x01) {
            limits = new ArrayList<Limit>();
            in.readList(limits, Limit.class.getClassLoader());
        } else {
            limits = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(headingTitle);
        dest.writeString(textEmpty);
        dest.writeString(textSearch);
        dest.writeString(textKeyword);
        dest.writeString(textCategory);
        dest.writeString(textSubCategory);
        dest.writeString(textQuantity);
        dest.writeString(textManufacturer);
        dest.writeString(textModel);
        dest.writeString(textPrice);
        dest.writeString(textTax);
        dest.writeString(textPoints);
        dest.writeString(textCompare);
        dest.writeString(textSort);
        dest.writeString(textLimit);
        dest.writeString(entrySearch);
        dest.writeString(entryDescription);
        dest.writeString(buttonSearch);
        dest.writeString(buttonCart);
        dest.writeString(buttonWishlist);
        dest.writeString(buttonCompare);
        dest.writeString(buttonList);
        dest.writeString(buttonGrid);
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
        if (limits == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(limits);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductSearch> CREATOR = new Parcelable.Creator<ProductSearch>() {
        @Override
        public ProductSearch createFromParcel(Parcel in) {
            return new ProductSearch(in);
        }

        @Override
        public ProductSearch[] newArray(int size) {
            return new ProductSearch[size];
        }
    };
}