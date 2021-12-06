package webkul.opencart.mobikul.model.AddressBookModel

/**
 * Created by manish.choudhary on 18/8/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import webkul.opencart.mobikul.model.BaseModel.BaseModel

class AddressBook : BaseModel() {

    @SerializedName("countryData")
    @Expose
    var countryData: List<CountryDatum>? = null

}
