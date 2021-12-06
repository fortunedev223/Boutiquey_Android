package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.databinding.ActivityCreateAccountBinding

/**
 * Created by manish.choudhary on 7/9/17.
 */

interface CreateAccount {
    fun getBinding(createAccountBinding: ActivityCreateAccountBinding)
    fun getSateID(stateId: String)
    fun getCountryId(countryId: String)
    fun getRadioId(radioId: Int)
    fun getGroupId(groupId: Int)
}
