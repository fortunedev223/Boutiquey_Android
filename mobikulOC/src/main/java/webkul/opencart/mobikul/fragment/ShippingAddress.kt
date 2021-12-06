package webkul.opencart.mobikul.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.CardView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import android.widget.*

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import webkul.opencart.mobikul.*
import webkul.opencart.mobikul.adapterModel.BottomCheckoutAddressModel
import webkul.opencart.mobikul.handlers.CheckoutBottomSheetAddressHandler
import webkul.opencart.mobikul.handlers.ShippingAddressHandler
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.callback.BillingAddressId
import webkul.opencart.mobikul.callback.GetAddressIdBottomSheet
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel
import webkul.opencart.mobikul.model.ShippingAddressModel.Address
import webkul.opencart.mobikul.networkManager.RetrofitCallback
import webkul.opencart.mobikul.networkManager.RetrofitCustomCallback
import webkul.opencart.mobikul.utils.SweetAlertBox
import webkul.opencart.mobikul.databinding.CheckoutBottomSheetAddressLayoutBinding
import webkul.opencart.mobikul.databinding.CheckoutBottomSheetBinding
import webkul.opencart.mobikul.databinding.FragmentShippingAddressBinding


class ShippingAddress : Fragment(), BillingAddressId, GetAddressIdBottomSheet {

    private val function = "shippingAddress"
    private var addressBinding: FragmentShippingAddressBinding? = null
    private var id: String? = null
    private var countrySpinner: Spinner? = null
    private var spinnerList: ArrayList<String>? = null
    private var addAddress: CardView? = null
    private val countryAdapter: ArrayAdapter<*>? = null
    private var list: MutableList<Address>? = null
    private var handler: ShippingAddressHandler? = null
    private var bottomSheetBinding: CheckoutBottomSheetBinding? = null
    private var sheetDialog: BottomSheetDialog? = null
    private var newAddress: TextView? = null
    private val newAddressIntent = 101
    private var shippingAddress: webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress? = null
    private var shippingAddressCallback: Callback<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress>? = null
    private var gdprStatus: Callback<GdprModel>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        addressBinding = FragmentShippingAddressBinding.inflate(inflater, container, false)
        list = ArrayList()
        handler = ShippingAddressHandler(activity!!)
        handler!!.setBinding(addressBinding!!)
        countrySpinner = addressBinding!!.countrySpinner
        addressBinding?.handler = handler
        addAddress = addressBinding?.newaddress
        addAddress?.setOnClickListener { startActivity(Intent(activity, AddrBookActivity::class.java)) }
        val select = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding?.billingPipeView?.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding?.billingAddressImage1?.background = select
        newAddress = addressBinding!!.defaultAddress
        bottomSheetBinding = CheckoutBottomSheetBinding.inflate(LayoutInflater.from(activity))
        sheetDialog = BottomSheetDialog(activity!!)
        sheetDialog?.setContentView(bottomSheetBinding!!.root)
        bottomSheetBinding?.bottomLayout?.setOnClickListener { view -> onClickNewAddress(view) }
        newAddress?.setOnClickListener {
            if (shippingAddress != null) {
                if (bottomSheetBinding!!.addressLayout.parent != null) {
                    bottomSheetBinding!!.addressLayout.removeAllViews()
                }
                val width = (Utils.getDeviceScreenWidth() / 2.2).toInt()
                for (i in 0 until shippingAddress!!.shippingAddress!!.addresses!!.size) {
                    val binding = CheckoutBottomSheetAddressLayoutBinding.inflate(inflater)
                    val params = LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT)
                    binding.root
                    binding.ll.setPadding(10, 10, 10, 10)
                    binding.ll.layoutParams = params
                    params.setMargins(10, 0, 10, 0)
                    binding.data = BottomCheckoutAddressModel(
                            shippingAddress!!.shippingAddress!!.addresses!![i].address!!.firstname + " " +
                                    shippingAddress!!.shippingAddress!!.addresses!![i].address!!.lastname,
                            shippingAddress!!.shippingAddress!!.addresses!![i].address!!.address1 + " ," +
                                    shippingAddress!!.shippingAddress!!.addresses!![i].address!!.city + " ," +
                                    shippingAddress!!.shippingAddress!!.addresses!![i].address!!.zone + " ," +
                                    shippingAddress!!.shippingAddress!!.addresses!![i].address!!.country,
                            shippingAddress!!.shippingAddress!!.addresses!![i].address!!.lastname,
                            ShippingMethod::class.java.simpleName,
                            shippingAddress!!.shippingAddress!!.addresses!![i].address!!.postcode,
                            shippingAddress!!.shippingAddress!!.addresses!![i].addressId,
                            shippingAddress!!.shippingAddress!!.addresses!![i].address!!.zone
                    )
                    binding.handler = CheckoutBottomSheetAddressHandler(activity!!, sheetDialog!!)
                    binding.root.setPadding(10, 0, 10, 0)
                    bottomSheetBinding!!.addressLayout.addView(binding.root)
                }
                sheetDialog!!.show()
            }
        }

        shippingAddressCallback = object : Callback<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress> {
            override fun onResponse(call: Call<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress>, response: Response<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress>) {
                if (activity != null) {
                    spinnerList = ArrayList()
                    shippingAddress = response.body()
                    if (response.body()!!.shippingAddress != null) {
                        if (response.body()!!.shippingAddress!!.addresses != null) {
                            list!!.addAll(response.body()!!.shippingAddress!!.addresses!!)
                            setPaymentAddress(response.body()!!.shippingAddress!!.addresses!![0].address!!.firstname,
                                    response.body()!!.shippingAddress!!.addresses!![0].address!!.lastname,
                                    response.body()!!.shippingAddress!!.addresses!![0].address!!.address1, "",
                                    response.body()!!.shippingAddress!!.addresses!![0].address!!.postcode,
                                    response.body()!!.shippingAddress!!.addresses!![0].address!!.addressId,
                                    response.body()!!.shippingAddress!!.addresses!![0].address!!.city + ", " +
                                            response.body()!!.shippingAddress!!.addresses!![0].address!!.zone + ", " +
                                            response.body()!!.shippingAddress!!.addresses!![0].address!!.country)
                            if (spinnerList!!.size == 0) {
                                for (i in list!!.indices) {
                                    spinnerList!!.add(response.body()!!.shippingAddress!!.addresses!![i].formatted!!)
                                }
                                val adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, spinnerList!!)
                                countrySpinner!!.adapter = adapter
                            }
                        }
                    }
                }
                SweetAlertBox.dissmissSweetAlertBox()
            }

            override fun onFailure(call: Call<webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress>, t: Throwable) {

            }
        }

        if (id != null) {
            SweetAlertBox().showProgressDialog(activity!!)
            RetrofitCallback.shippingAddressCheckoutCall(activity!!, function, id!!, RetrofitCustomCallback(shippingAddressCallback, activity))
        }

        return addressBinding!!.root
    }

    private fun setGdprSpannable(message: String) {
        val gdprValue = resources.getString(R.string.gdpr_check_value)
        val ss = SpannableString(gdprValue)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                val mDialog = Dialog(activity)
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                mDialog.setContentView(R.layout.terms_and_conditions_text)
                val webView = mDialog.findViewById<View>(R.id.webView) as WebView
                webView.settings.displayZoomControls = true
                mDialog.findViewById<View>(R.id.container).layoutParams = RelativeLayout.LayoutParams(
                        webkul.opencart.mobikul.helper.Utils.getDeviceScreenWidth(),
                        webkul.opencart.mobikul.helper.Utils.getDeviceScrenHeight())
                mDialog.findViewById<View>(R.id.close).setOnClickListener { mDialog.dismiss() }
                try {
                    webView.loadData(message, "text/html; charset=UTF-8", null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                mDialog.findViewById<View>(R.id.button3).setOnClickListener { mDialog.dismiss() }
                mDialog.show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        ss.setSpan(clickableSpan, 32, gdprValue.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        addressBinding!!.gdprCheckbox.setText(ss)
        addressBinding!!.gdprCheckbox.setMovementMethod(LinkMovementMethod.getInstance())
    }

    fun setPaymentAddress(firstName: String?, lastName: String?,
                          emailAddress: String?, telephone: String,
                          postCode: String?, tag: String?, address: String) {
        addressBinding!!.address.removeAllViews()
        val heading = TextView(activity)
        val customer_name = TextView(activity)
        val email = TextView(activity)
        val checkoutAddress = TextView(activity)
        checkoutAddress.text = address
        val phone_number = TextView(activity)
        heading.text = getString(R.string.default_address)
        heading.textSize = resources.getDimension(R.dimen.heading_extra_small)
        customer_name.text = "$firstName $lastName"
        email.text = emailAddress
        if (telephone != "") {
            phone_number.text = activity!!.resources.getString(R.string.telephone) + " -" + telephone
        } else {
            phone_number.text = activity!!.resources.getString(R.string.zip) + " -" + postCode
        }
        addressBinding!!.address.addView(heading)
        addressBinding!!.address.addView(customer_name)
        if (!email.equals("")) {
            addressBinding!!.address.addView(email)
        }
        if (address != "") {
            addressBinding!!.address.addView(checkoutAddress)
        }
        addressBinding!!.address.addView(phone_number)
        handler!!.getAddressResponse(tag!!)
    }

    override fun getAddressID(id: String) {
        this.id = id
    }

    override fun getGuestShippingMethod(shippingMethod: webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod) {

    }

    override fun getAddressIdBottomSheet(firstName: String, lastName: String, emailAddress: String, telephone: String, postCode: String, tag: String) {
        setPaymentAddress(firstName, lastName, emailAddress, telephone, postCode, tag, "")
    }

    fun onClickNewAddress(v: View) {
        val intent = Intent(activity, NewAddressForm::class.java)
        activity!!.startActivityForResult(intent, newAddressIntent)
    }

}
