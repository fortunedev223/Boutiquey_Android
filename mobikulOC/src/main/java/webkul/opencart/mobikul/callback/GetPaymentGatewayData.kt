package webkul.opencart.mobikul.callback

import webkul.opencart.mobikul.model.ConfirmModel.Apgsenangpay

/**
 * Created by manish.choudhary on 17/11/17.
 */

interface GetPaymentGatewayData {
    fun getData(apgsenangpay: webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder, orderId: String)
}
