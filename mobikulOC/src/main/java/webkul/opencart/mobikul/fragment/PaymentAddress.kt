package webkul.opencart.mobikul.fragment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.adapterModel.BottomCheckoutAddressModel
import webkul.opencart.mobikul.AddrBookActivity
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.BillingHandler
import webkul.opencart.mobikul.handlers.CheckoutBottomSheetAddressHandler
import webkul.opencart.mobikul.helper.ResponseHelper
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.callback.GetAddressIdBottomSheet
import webkul.opencart.mobikul.NewAddressForm
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.databinding.CheckoutBottomSheetAddressLayoutBinding
import webkul.opencart.mobikul.databinding.CheckoutBottomSheetBinding
import webkul.opencart.mobikul.databinding.FragmentBillingAddressBinding

class PaymentAddress : Fragment(), GetAddressIdBottomSheet {
    var binding: FragmentBillingAddressBinding? = null
        private set
    private var ship_to_address: CheckBox? = null
    private var paymentAddressCallback: Callback<webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress>? = null
    private val function = "paymentAddress"
    private var countrySpinner: Spinner? = null
    private val countryAdapter: ArrayAdapter<*>? = null
    private var addAddress: CardView? = null
    private var paymentAddresslist: ArrayList<String>? = null
    private var newAddress: TextView? = null
    private val newAddressIntent = 101
    private var addressList: List<webkul.opencart.mobikul.model.PaymentAddressModel.Address>? = null
    private var paymentAddress: webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress? = null
    private var bottomSheetBinding: CheckoutBottomSheetBinding? = null
    private var sheetDialog: BottomSheetDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_billing_address, container, false)
        val handler = BillingHandler(activity!!)
        binding?.handler = handler
        ship_to_address = binding?.shipToThisAddress
        val block = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding?.billingAddressImage?.background = block
        countrySpinner = binding?.countrySpinner
        addAddress = binding?.newaddress
        newAddress = binding?.defaultAddress
        bottomSheetBinding = CheckoutBottomSheetBinding.inflate(LayoutInflater.from(activity))
        sheetDialog = BottomSheetDialog(activity!!)
        bottomSheetBinding?.bottomLayout?.setOnClickListener { view -> onClickNewAddress(view) }
        sheetDialog?.setContentView(bottomSheetBinding?.root)
        newAddress?.setOnClickListener {
            if (paymentAddress != null) {
                if (bottomSheetBinding?.addressLayout?.parent != null) {
                    bottomSheetBinding?.addressLayout?.removeAllViews()
                }
                val width = (Utils.getDeviceScreenWidth() / 2.2).toInt()
                for (i in 0 until paymentAddress?.paymentAddress?.addresses?.size!!) {
                    val binding = CheckoutBottomSheetAddressLayoutBinding.inflate(inflater)
                    val params = LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT)
                    binding.root
                    binding.ll.setPadding(10, 10, 10, 10)
                    binding.ll.layoutParams = params
                    params.setMargins(10, 0, 10, 0)
                    binding.data = BottomCheckoutAddressModel(
                            paymentAddress?.paymentAddress?.addresses!![i].address?.firstname + " " +
                                    paymentAddress?.paymentAddress?.addresses!![i].address?.lastname,
                            paymentAddress?.paymentAddress?.addresses!![i].address?.address1 + " ," +
                                    paymentAddress?.paymentAddress?.addresses!![i].address?.city + " ," +
                                    paymentAddress?.paymentAddress?.addresses!![i].address?.zone + " ," +
                                    paymentAddress?.paymentAddress?.addresses!![i].address?.country,
                            paymentAddress?.paymentAddress?.addresses!![i].address?.lastname,
                            PaymentAddress::class.java.simpleName,
                            paymentAddress?.paymentAddress?.addresses!![i].address?.postcode,
                            paymentAddress?.paymentAddress?.addresses!![i].addressId,
                            paymentAddress?.paymentAddress?.addresses!![i].address?.zone
                    )
                    binding.handler = CheckoutBottomSheetAddressHandler(activity!!, sheetDialog!!)
                    binding.root.setPadding(10, 0, 10, 0)
                    bottomSheetBinding?.addressLayout?.addView(binding.root)
                }
                sheetDialog?.show()
            }
        }
        addAddress?.setOnClickListener { startActivity(Intent(activity, AddrBookActivity::class.java)) }
        ship_to_address?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (ship_to_address?.isChecked!! && paymentAddress != null) {
                ship_to_address?.tag = binding?.address?.tag
            }
        }

        paymentAddressCallback = object : Callback<webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress> {
            override fun onResponse(call: Call<webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress>,
                                    response: Response<webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress>) {
                paymentAddress = response.body()
                if (ResponseHelper.isValidResponse(activity, response, false)) {
                    if (paymentAddress?.paymentAddress != null) {
                        handler.shipingRequired(response.body()?.shippingRequired!!)
                        if (paymentAddress?.paymentAddress?.addresses?.size != 0) {
                            setPaymentAddress(paymentAddress?.paymentAddress?.loginData?.firstname,
                                    paymentAddress?.paymentAddress?.loginData?.lastname,
                                    paymentAddress?.paymentAddress?.loginData?.email,
                                    paymentAddress?.paymentAddress?.loginData?.phone,
                                    paymentAddress?.paymentAddress?.addresses!![0].address?.postcode,
                                    paymentAddress?.paymentAddress?.addresses!![0].addressId,
                                    paymentAddress?.paymentAddress?.addresses!![0].address?.address1 + ", " +
                                            paymentAddress?.paymentAddress?.addresses!![0].address?.city + ", " +
                                            paymentAddress?.paymentAddress?.addresses!![0].address?.zone + ", " +
                                            paymentAddress?.paymentAddress?.addresses!![0].address?.country)
                            paymentAddresslist = ArrayList()
                            val len = paymentAddress?.paymentAddress?.addresses?.size
                            addressList = paymentAddress?.paymentAddress?.addresses
                            val defaultAddPosition = 0
                            if (paymentAddresslist?.size == 0) {
                                for (i in 0 until len!!) {
                                    paymentAddresslist?.add(paymentAddress?.paymentAddress?.addresses!![i].formatted!!)
                                }
                                val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, paymentAddresslist)
                                countrySpinner?.adapter = adapter
                            }
                        } else {
                            binding?.errorTv?.visibility = View.VISIBLE
                            binding?.billingCheckoutContinue?.isClickable = false
                        }
                    }
                }
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onFailure(call: Call<webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress>, t: Throwable) {
                SweetAlertBox.dissmissSweetAlertBox()
            }
        }
        Handler().postDelayed(object : Runnable {
            override fun run() {
                SweetAlertBox().showProgressDialog(activity!!)
            }
        }, 100)
        RetrofitCallback.paymentAddressCheckoutCall(activity!!, function, RetrofitCustomCallback(paymentAddressCallback, activity))
        return binding?.root
    }

    fun setPaymentAddress(firstName: String?, lastName: String?, emailAddress: String?, telephone: String?, postCode: String?, tag: String?, address: String) {
        binding?.address?.removeAllViews()
        val heading = TextView(activity)
        val customer_name = TextView(activity)
        val email = TextView(activity)
        val phone_number = TextView(activity)
        val checkoutAddress = TextView(activity)
        checkoutAddress.text = address
        heading.text = getString(R.string.default_address)
        heading.textSize = resources.getDimension(R.dimen.heading_extra_small)
        customer_name.text = "$firstName $lastName"
        email.text = emailAddress
        if (telephone != "") {
            phone_number.text = activity?.resources?.getString(R.string.telephone) + " -" + telephone
        } else if (postCode != "") {
            phone_number.text = activity?.resources?.getString(R.string.zip) + " -" + postCode
        } else {
            phone_number.text = ""
        }
        binding?.address?.addView(heading)
        binding?.address?.addView(customer_name)
        if (!email.equals("")) {
            binding?.address?.addView(email)
        }
        if (address != "") {
            binding?.address?.addView(checkoutAddress)
        }
        binding?.address?.addView(phone_number)
        binding?.address?.tag = tag
        binding?.shipToThisAddress?.tag = tag
    }

    override fun onPause() {
        super.onPause()
        ship_to_address?.isChecked = false
    }


    override fun getAddressIdBottomSheet(firstName: String, lastName: String, emailAddress: String, telephone: String, postCode: String, tag: String) {
        setPaymentAddress(firstName, lastName, emailAddress, telephone, postCode, tag, "")
    }

    fun onClickNewAddress(v: View) {
        val intent = Intent(activity, NewAddressForm::class.java)
        activity?.startActivityForResult(intent, newAddressIntent)
    }

}


