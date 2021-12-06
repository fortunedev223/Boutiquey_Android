package webkul.opencart.mobikul.model.ConfirmModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Option {

    @SerializedName("product_option_id")
    @Expose
    var productOptionId: String? = null
    @SerializedName("product_option_value_id")
    @Expose
    var productOptionValueId: String? = null
    @SerializedName("option_id")
    @Expose
    var optionId: String? = null
    @SerializedName("option_value_id")
    @Expose
    var optionValueId: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param optionId
     * @param productOptionId
     * @param optionValueId
     * @param name
     * @param value
     * @param productOptionValueId
     * @param type
     */
    constructor(productOptionId: String, productOptionValueId: String, optionId: String, optionValueId: String, name: String, value: String, type: String) : super() {
        this.productOptionId = productOptionId
        this.productOptionValueId = productOptionValueId
        this.optionId = optionId
        this.optionValueId = optionValueId
        this.name = name
        this.value = value
        this.type = type
    }

}