package webkul.opencart.mobikul

import java.util.ArrayList

class CustomoptionData(var title: String?, var type: String?, var isRequired: String?,
                       var unformatedDefaultPrice: Double?, var formatedDefaultPrice: String?,
                       var priceType: String?, var optionId: String?, var productId: Int, var defaultPrice: Double?, var productOptionId: String?) {
    var id: Int = 0 // id of custom option view mot the multioption that it contains
    // productprice

    var associatedId: Int = 0 // in case of date_time
    // in case of

    lateinit var multiOptionListList: ArrayList<MultiOptionData>
    var isAffectingPrice: Boolean? = null// if it is adding some price to actual price
    var isChanged: Boolean? = null
    var isValueAddedToPrice: Boolean? = false// In case price of option is added to

}

class MultiOptionData {
    var id: Int = 0// in case of checkbox since they donot have any relation to each
    // other
    var option_id: Int = 0 // to categorize view distinguish checkboxes

    var optionTypeId: Int = 0
    var defaultPrice: Double? = null
    var priceType: String?

    constructor(id: Int, option_id: Int, optionTypeId: Int,
                defaultPrice: Double?, priceType: String?) : super() {
        this.id = id
        this.option_id = option_id
        this.optionTypeId = optionTypeId
        this.defaultPrice = defaultPrice
        this.priceType = priceType
    }

    fun getOptionId(): Int {
        return optionTypeId
    }

    fun setOptionId(optionTypeId: Int) {
        this.optionTypeId = optionTypeId
    }

    constructor(optionTypeId: Int, defaultPrice: Double?,
                priceType: String) : super() {
        this.optionTypeId = optionTypeId
        this.defaultPrice = defaultPrice
        this.priceType = priceType
    }

    constructor(id: Int, optionTypeId: Int, defaultPrice: Double?,
                priceType: String) : super() { // in
        this.id = id
        this.optionTypeId = optionTypeId
        this.defaultPrice = defaultPrice
        this.priceType = priceType
    }// case
    // of
    // checkbox

}
