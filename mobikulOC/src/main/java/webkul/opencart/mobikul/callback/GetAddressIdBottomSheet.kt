package webkul.opencart.mobikul.callback

/**
 * Created by manish.choudhary on 17/10/17.
 */

interface GetAddressIdBottomSheet {
    fun getAddressIdBottomSheet(firstName: String, lastName: String, emailAddress: String, telephone: String, postCode: String, tag: String)
}
