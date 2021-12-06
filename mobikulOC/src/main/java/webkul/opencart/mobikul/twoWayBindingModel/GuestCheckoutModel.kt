package webkul.opencart.mobikul.twoWayBindingModel

import android.databinding.BaseObservable
import android.databinding.Bindable

import webkul.opencart.mobikul.BR

/**
 * Created by manish.choudhary on 18/8/17.
 */

class GuestCheckoutModel : BaseObservable() {
    @get:Bindable("firstName")
    var firstName = ""
        set(firstName) {
            field = firstName
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable("lastName")
    var lastName = ""
        set(lastName) {
            field = lastName
            notifyPropertyChanged(BR.lastName)
        }

    @get:Bindable("email")
    var email = ""
        set(email) {
            field = email
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable("telephone")
    var telephone = ""
        set(telephone) {
            field = telephone
            notifyPropertyChanged(BR.telephone)

        }
    @get:Bindable("fax")
    var fax = ""
        set(fax) {
            field = fax
            notifyPropertyChanged(BR.fax)

        }
    @get:Bindable("company")
    var company = ""
        set(company) {
            field = company
            notifyPropertyChanged(BR.company)

        }
    @get:Bindable("address1")
    var address1 = ""
        set(address1) {
            field = address1
            notifyPropertyChanged(BR.address1)

        }
    @get:Bindable("address2")
    var address2 = ""
        set(address2) {
            field = address2
            notifyPropertyChanged(BR.address2)

        }
    @get:Bindable("city")
    var city = ""
        set(city) {
            field = city
            notifyPropertyChanged(BR.city)

        }
    @get:Bindable("zip")
    var zip = ""
        set(zip) {
            field = zip
            notifyPropertyChanged(BR.zip)

        }
    @get:Bindable("coutryName")
    var coutryName = ""
        set(coutryName) {
            field = coutryName
            notifyPropertyChanged(BR.coutryName)
        }

    @get:Bindable("stateName")
    var stateName = ""
        set(stateName) {
            field = stateName
            notifyPropertyChanged(BR.stateName)
        }
}
