package webkul.opencart.mobikul.fragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import webkul.opencart.mobikul.adapter.ConfirmOrderAdapter
import webkul.opencart.mobikul.adapterModel.ConfirmOrderAdapteModel
import webkul.opencart.mobikul.CheckoutActivity
import webkul.opencart.mobikul.handlers.ConfirmOrderhandler
import webkul.opencart.mobikul.callback.GetPaymentCode
import webkul.opencart.mobikul.R
import webkul.opencart.mobikul.databinding.FragmentConfirmOrderBinding


class ConfirmOrder : Fragment(), webkul.opencart.mobikul.callback.ConfirmOrder, GetPaymentCode {
    private var confirmOrderBinding: FragmentConfirmOrderBinding? = null
    private var billing_layout: LinearLayout? = null
    private var productTotalLl: LinearLayout? = null
    private var ship: CheckBox? = null
    private var recyclerView: RecyclerView? = null
    private var confirmOrderAdapter: ConfirmOrderAdapter? = null
    private var list: ArrayList<ConfirmOrderAdapteModel>? = null
    private var confirmOrderhandler: ConfirmOrderhandler? = null
    private var code: String? = null
    private var confirmOrder: webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        confirmOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_order, container, false)
        ship = confirmOrderBinding!!.shipToThisAddress
        recyclerView = confirmOrderBinding!!.confirmOrderRecyclerview
        billing_layout = confirmOrderBinding!!.shippingAddress
        val step5 = AppCompatResources.getDrawable(activity!!, R.drawable.checkout_selected)
        (activity as CheckoutActivity).binding!!.confirmPipeView.setBackgroundColor(Color.parseColor("#1D89E3"))
        (activity as CheckoutActivity).binding!!.confirmOrderImage.background = step5
        confirmOrderhandler = ConfirmOrderhandler(activity!!)
        confirmOrderBinding!!.handler = confirmOrderhandler
        ship!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (ship!!.isChecked) {
                confirmOrderhandler!!.shipChecked(true)
            } else {
                confirmOrderhandler!!.shipChecked(false)
            }
        }
        productTotalLl = confirmOrderBinding!!.productTotalLl
        if (confirmOrder != null) {
            val payment_customer_name = TextView(activity)
            val payment_address = TextView(activity)
            val payment_city = TextView(activity)
            val payment_contact = TextView(activity)
            payment_customer_name.text = confirmOrder!!.`continue`!!.orderDetails!!.paymentFirstname + " " + confirmOrder!!.`continue`!!.orderDetails!!.paymentLastname
            payment_address.text = confirmOrder!!.`continue`!!.orderDetails!!.paymentAddress1 + "," + confirmOrder!!.`continue`!!.orderDetails!!.paymentAddress2 + "," + confirmOrder!!.`continue`!!.orderDetails!!.paymentCity
            payment_city.text = confirmOrder!!.`continue`!!.orderDetails!!.paymentZone + "-" + confirmOrder!!.`continue`!!.orderDetails!!.paymentPostcode
            payment_contact.text = activity!!.resources.getString(R.string.telephone) + " -" + confirmOrder!!.`continue`!!.orderDetails!!.telephone
            billing_layout!!.addView(payment_customer_name)
            billing_layout!!.addView(payment_address)
            billing_layout!!.addView(payment_city)
            billing_layout!!.addView(payment_contact)
            list = ArrayList()
            for (i in 0 until confirmOrder!!.`continue`!!.orderDetails!!.products!!.size) {
                list!!.add(ConfirmOrderAdapteModel(activity,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].image,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].name,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].quantity!!,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].totalText!!,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].option,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].model,
                        confirmOrder!!.`continue`!!.orderDetails!!.products!![i].priceText
                ))
            }
            confirmOrderhandler!!.paymentMethodType(this!!.code!!)
            confirmOrderAdapter = ConfirmOrderAdapter(activity!!, list!!)
            recyclerView!!.adapter = confirmOrderAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            if (code == APGSENANGPAY) {
                confirmOrderhandler!!.getData(confirmOrder!!, "")
            }

            if (code == PayTabs) {
                confirmOrderhandler!!.getData(confirmOrder!!, "")
            }
            for (i in 0 until confirmOrder!!.`continue`!!.totals!!.size) {
                val view = View.inflate(activity, R.layout.confirm_order_total, null) as LinearLayout
                val title = view.findViewById<View>(R.id.title) as TextView
                val cost = view.findViewById<View>(R.id.cost) as TextView
                title.text = confirmOrder!!.`continue`!!.totals!![i].title
                cost.text = confirmOrder!!.`continue`!!.totals!![i].text
                productTotalLl!!.addView(view)
            }
        }
        return confirmOrderBinding!!.root
    }


    override fun getConfirmOrder(confirmOrder: webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder) {
        this.confirmOrder = confirmOrder
    }

    override fun getPaymentcode(code: String) {
        this.code = code
    }

    companion object {
        private val TAG = "PRoductImage"
        private val APGSENANGPAY = "telr"
        private val PayTabs = "paytabs"
    }
}
