package webkul.opencart.mobikul.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.GuestCheckoutHandler
import webkul.opencart.mobikul.helper.ResponseHelper
import webkul.opencart.mobikul.model.GuestCheckoutModel.GuestCheckout
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.twoWayBindingModel.GuestCheckoutModel
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentGuestBinding


class GuestFragment : Fragment() {
    var guestBinding: FragmentGuestBinding? = null
    private var guestCheckoutCallback: Callback<GuestCheckout>? = null
    private val function = "guest"
    private var guestCheckout: GuestCheckout? = null
    private var statePosition: Int = 0
    private val stateName: String? = null
    private var stateDropdown: Spinner? = null
    private val stateDropdownShip: Spinner? = null
    private var countryPosition: Int = 0
    private var country_id: String? = null
    private var state_id: String? = null
    private var group: RadioGroup? = null
    private var model: GuestCheckoutModel? = null
    private var guestCheckoutHandler: GuestCheckoutHandler? = null
    private var countryId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        guestBinding = FragmentGuestBinding.inflate(LayoutInflater.from(activity), container, false)
        group = guestBinding!!.isSubscribed
        guestCheckoutHandler = GuestCheckoutHandler(activity!!)
        guestBinding!!.handler = guestCheckoutHandler
        val select = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding!!.billingAddressImage.background = select
        model = GuestCheckoutModel()
        guestBinding!!.data = model
        guestCheckoutHandler!!.getBinding(guestBinding!!)
        group!!.setOnCheckedChangeListener { group, checkedId ->
            if (group.checkedRadioButtonId == guestBinding!!.yes.id) {
                guestCheckoutHandler!!.getShippingAddress("1")
            } else if (group.checkedRadioButtonId == guestBinding!!.no.id) {
                guestCheckoutHandler!!.getShippingAddress("0")
            }
        }

        if (group!!.checkedRadioButtonId == guestBinding!!.yes.id) {
            guestCheckoutHandler!!.getShippingAddress("1")
        }
        guestCheckoutCallback = object : Callback<GuestCheckout> {
            override fun onResponse(call: Call<GuestCheckout>, response: Response<GuestCheckout>) {
                SweetAlertBox.dissmissSweetAlertBox()
                guestCheckout = response.body()
                if (ResponseHelper.isValidResponse(activity, response, false)) {
                    val countries = arrayOfNulls<String>(response.body()!!.guest!!.countryData!!.size)
                    if (response.body()!!.guest!!.countryId != null) {
                        countryId = response.body()!!.guest!!.countryId
                    }
                    for (i in 0 until response.body()!!.guest!!.countryData!!.size) {
                        countries[i] = response.body()!!.guest!!.countryData!![i].name
                        if (countryId != null) {
                            if (countryId == response.body()!!.guest!!.countryData!![i].countryId) {
                                countryPosition = i
                            }
                        }
                    }
                    val dropdown = guestBinding!!.countrySpinner
                    stateDropdown = guestBinding!!.statesSpinner
                    val adapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_dropdown_item, countries)
                    dropdown.adapter = adapter
                    dropdown.setSelection(countryPosition)
                    dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
                            try {
                                country_id = guestCheckout!!.guest!!.countryId
                                guestCheckoutHandler!!.getCountryId(country_id!!)
                                println(" Zone Size data === " + guestCheckout!!.guest!!.countryData!![position].zone!!.size)
                                if (guestCheckout!!.guest!!.countryData!![position].zone!!.size != 0) {
                                    val states = arrayOfNulls<String>(guestCheckout!!.guest!!.countryData!![position].zone!!.size)
                                    for (i in 0..guestCheckout!!.guest!!.countryData!![position].zone!!.size - 1) {
                                        states[i] = guestCheckout!!.guest!!.countryData!![position].zone!![i].name
                                        println(" State data === " + i + "  " + states[i])
                                        if (stateName != null && states[i].equals(stateName!!, ignoreCase = true))
                                            statePosition = i
                                    }
                                    println(" State data Size === " + states.size)
                                    val temp = position
                                    stateDropdown!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
                                            state_id = guestCheckout!!.guest!!.countryData!![temp].zone!![position].zoneId
                                            guestCheckoutHandler!!.getZoneId(state_id!!)
                                        }

                                        override fun onNothingSelected(
                                                parent: AdapterView<*>) {
                                        }
                                    }
                                    val adapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_dropdown_item, states)
                                    stateDropdown!!.adapter = adapter
                                    stateDropdown!!.setSelection(statePosition)
                                    statePosition = 0
                                } else {
                                    val states = arrayOf("None")
                                    val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, states)
                                    stateDropdown!!.adapter = adapter
                                    state_id = "0"
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.d("Error", "Inside dropDown$e")
                            }

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // TODO Auto-generated method stub
                        }
                    }
                }


            }

            override fun onFailure(call: Call<GuestCheckout>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
        }
        SweetAlertBox().showProgressDialog(activity!!)
        RetrofitCallback.guestCheckoutCall(activity!!, function, RetrofitCustomCallback(guestCheckoutCallback, activity))
        return guestBinding?.root
    }

}
