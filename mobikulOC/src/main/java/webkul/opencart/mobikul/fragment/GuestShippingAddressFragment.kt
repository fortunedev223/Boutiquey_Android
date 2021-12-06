package webkul.opencart.mobikul.fragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.GuestShippingAddressHandler
import webkul.opencart.mobikul.helper.ResponseHelper
import webkul.opencart.mobikul.callback.GetGuestCheckoutModel
import webkul.opencart.mobikul.model.GuestShippingAddressModel.GuestShippingAddress
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.twoWayBindingModel.GuestCheckoutModel
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.FragmentGuestShippingAddressBinding

class GuestShippingAddressFragment : Fragment(), GetGuestCheckoutModel {

    private var guestShippingAddressBinding: FragmentGuestShippingAddressBinding? = null
    private var guestShippingAddressCallback: Callback<GuestShippingAddress>? = null
    private var model: GuestCheckoutModel? = null
    private var data: GuestCheckoutModel? = null
    private var shippingaddress: String? = null
    private var countryID: String? = null
    private var zoneID: String? = null
    private var stateDropdown: Spinner? = null
    private val stateDropdownShip: Spinner? = null
    private var guestShippingAddress: GuestShippingAddress? = null
    private val function = "saveGuest"
    private var countryPosition: Int = 0
    private var state_id: String? = null
    private var statePosition: Int = 0
    private var country_id: String? = null
    private val stateName: String? = null
    private var handler: GuestShippingAddressHandler? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        guestShippingAddressBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_guest_shipping_address, container, false)
        handler = GuestShippingAddressHandler(activity!!)
        data = GuestCheckoutModel()
        guestShippingAddressBinding!!.handler = handler
        guestShippingAddressBinding!!.data = data
        val select = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        val step5 = AppCompatResources.getDrawable(activity!!, R.drawable.ic_checkout_step_befor_5)
        (activity as CheckoutActivity).binding!!.billingAddressImage1.background = select
        (activity as CheckoutActivity).binding!!.billingPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
        handler!!.getGuestShippingFragmentBinding(guestShippingAddressBinding!!)
        guestShippingAddressCallback = object : Callback<GuestShippingAddress> {
            override fun onResponse(call: Call<GuestShippingAddress>, response: Response<GuestShippingAddress>) {
                guestShippingAddress = response.body()
                SweetAlertBox.dissmissSweetAlertBox()
                if (ResponseHelper.isValidResponse(activity, response, false) && response.body()!!.shippingAddress!!.countryData != null) {
                    val countries = arrayOfNulls<String>(guestShippingAddress!!.shippingAddress!!.countryData!!.size)
                    if (guestShippingAddress!!.shippingAddress!!.countryId != null) {
                        country_id = guestShippingAddress!!.shippingAddress!!.countryId
                    }
                    for (i in 0 until guestShippingAddress!!.shippingAddress!!.countryData!!.size) {
                        countries[i] = guestShippingAddress!!.shippingAddress!!.countryData!![i].name
                        if (country_id != null) {
                            if (country_id == guestShippingAddress!!.shippingAddress!!.countryData!![i].countryId) {
                                countryPosition = i
                            }
                        }
                    }
                    val dropdown = guestShippingAddressBinding!!.countrySpinner
                    stateDropdown = guestShippingAddressBinding!!.statesSpinner
                    val adapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_dropdown_item, countries)
                    dropdown.adapter = adapter
                    dropdown.setSelection(countryPosition)
                    dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
                            try {
                                country_id = guestShippingAddress!!.shippingAddress!!.countryId
                                handler?.getCountryId(country_id!!)
                                if (guestShippingAddress!!.shippingAddress!!.countryData!![position].zone!!.size != 0) {
                                    val states = arrayOfNulls<String>(guestShippingAddress!!.shippingAddress!!.countryData!![position].zone!!.size)
                                    for (i in 0 until guestShippingAddress!!.shippingAddress!!.countryData!![position].zone!!.size) {
                                        states[i] = guestShippingAddress!!.shippingAddress!!.countryData!![position].zone!![i].name
                                        if (stateName != null && states[i].equals(stateName!!, ignoreCase = true))
                                            statePosition = i
                                    }

                                    val temp = position
                                    stateDropdown!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
                                            state_id = guestShippingAddress!!.shippingAddress!!.countryData!![temp].zone!![position].zoneId
                                            handler!!.getZoneId(state_id!!)
                                        }

                                        override fun onNothingSelected(
                                                parent: AdapterView<*>) {
                                            Log.d("jsonErrorStates", "Inside state dropDown")
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

            override fun onFailure(call: Call<GuestShippingAddress>, t: Throwable) {

            }
        }

        if (model != null && shippingaddress != null && countryID != null && zoneID != null) {
            SweetAlertBox().showProgressDialog(activity!!)
            RetrofitCallback.guestCheckoutForShippingAddress(activity!!, shippingaddress!!, function, model!!.email, model!!.telephone,
                    model!!.fax, model!!.firstName, model!!.lastName, model!!.company, model!!.address1, model!!.address2,
                    model!!.city, model!!.zip, countryID!!, zoneID!!, RetrofitCustomCallback(guestShippingAddressCallback, activity))
        }
        return guestShippingAddressBinding!!.root
    }

    override fun getGuestCheckoutModel(model: GuestCheckoutModel) {
        this.model = model
    }

    override fun getShippingAddress(id: String, countryID: String, zoneID: String) {
        shippingaddress = id
        this.countryID = countryID
        this.zoneID = zoneID
    }

}
