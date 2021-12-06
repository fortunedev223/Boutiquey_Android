package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.databinding.FragmentPaymentMethodBinding


interface PaymentMethod {
    fun getPaymentMethod(code: String)
    fun getPaymentBinding(paymentMethodBinding: FragmentPaymentMethodBinding)
}
