package webkul.opencart.mobikul.credentials

import webkul.opencart.mobikul.helper.Constant

object AppCredentials {
    val WEBSITE_ID = "1"
    val NAMESPACE = "urn:opencart"
    val URL:String = Constant.BASE_URL.substring(0, Constant.BASE_URL.length - 1) + "?route=api/wkrestapi/"
    val SOAP_USER_NAME:String = Constant.API_USERNAME
    val SOAP_PASSWORD:String = Constant.API_PASSWORD

}
