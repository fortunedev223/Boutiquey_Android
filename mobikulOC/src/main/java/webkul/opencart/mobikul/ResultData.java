package webkul.opencart.mobikul;

public class ResultData {
	int optionId;
	int productId;
	String valueInput;
	
	public ResultData(int optionId, int productId, String valueInput) {
		super();
		this.optionId = optionId;
		this.productId = productId;
		this.valueInput = valueInput;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getValueInput() {
		return valueInput;
	}

	public void setValueInput(String valueInput) {
		this.valueInput = valueInput;
	}

}
