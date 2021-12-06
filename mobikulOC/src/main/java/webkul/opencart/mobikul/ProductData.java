package webkul.opencart.mobikul;

public class ProductData {
	String productName;
	String description;
	String shortDescription;
	String availability;
	Double price;
	int msrpEnabled;
	int isInRange;
	PriceFormat priceFormat;
	int msrpDisplayActualPriceType;
	String formatedPrice;
	Double currentPrice;//price after adding custion options
	String specialPrice;
	
	

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public ProductData(String productName, String description,
			String shortDescription, String availability, Double price,
			int msrpEnabled, int isInRange, PriceFormat priceFormat,
			int msrpDisplayActualPriceType, String formatedPrice, String specialPrice) {
		super();
		this.productName = productName;
		this.description = description;
		this.shortDescription = shortDescription;
		this.availability = availability;
		this.price = price;
		this.msrpEnabled = msrpEnabled;
		this.isInRange = isInRange;
		this.priceFormat = priceFormat;
		this.msrpDisplayActualPriceType = msrpDisplayActualPriceType;
		this.formatedPrice = formatedPrice;
		this.specialPrice = specialPrice;
	}

	public String getFormatedPrice() {
		return formatedPrice;
	}

	public void setFormatedPrice(String formatedPrice) {
		this.formatedPrice = formatedPrice;
	}

	public int getMsrpDisplayActualPriceType() {
		return msrpDisplayActualPriceType;
	}

	public void setMsrpDisplayActualPriceType(int msrpDisplayActualPriceType) {
		this.msrpDisplayActualPriceType = msrpDisplayActualPriceType;
	}


	public PriceFormat getPriceFormat() {
		return priceFormat;
	}

	public void setPriceFormat(PriceFormat priceFormat) {
		this.priceFormat = priceFormat;
	}

	

	public int getIsInRange() {
		return isInRange;
	}

	public void setIsInRange(int isInRange) {
		this.isInRange = isInRange;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getMsrpEnabled() {
		return msrpEnabled;
	}

	public void setMsrpEnabled(int msrpEnabled) {
		this.msrpEnabled = msrpEnabled;
	}

}

class PriceFormat {
	String pattern;
	int precision;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public PriceFormat(String pattern, int precision) {
		super();
		this.pattern = pattern;
		this.precision = precision;
	}

}
