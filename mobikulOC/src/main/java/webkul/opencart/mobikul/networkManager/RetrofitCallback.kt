package webkul.opencart.mobikul.networkManager

import android.content.Context
import org.json.JSONObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import webkul.opencart.mobikul.helper.Constant
import webkul.opencart.mobikul.helper.Utils
import webkul.opencart.mobikul.model.AddCustomerModel.AddCustomer
import webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder
import webkul.opencart.mobikul.model.CustomerLogoutModel.CustomerLogout
import webkul.opencart.mobikul.model.EditAddressBookModel.EditAddressbook
import webkul.opencart.mobikul.model.EmptyCartModel.EmptyCart
import webkul.opencart.mobikul.model.FileUpload.FileUpload
import webkul.opencart.mobikul.model.GetAddressModel.GetAddress
import webkul.opencart.mobikul.model.GetSellDataModel.GetSellData
import webkul.opencart.mobikul.model.GuestCheckoutModel.GuestCheckout
import webkul.opencart.mobikul.model.GuestShippingAddressModel.GuestShippingAddress
import webkul.opencart.mobikul.model.ManufactureInfoModel.Manufacture
import webkul.opencart.mobikul.model.MyAccountModel.MyAccount
import webkul.opencart.mobikul.model.OrderReturnInfoModel.ReturnInfo
import webkul.opencart.mobikul.model.OrderReturnModel.OrderReturn
import webkul.opencart.mobikul.model.SearchProductModel.SearchProduct
import webkul.opencart.mobikul.model.SellerDashboardModel.SellerDashboard
import webkul.opencart.mobikul.model.SellerOrderModel.SellerOrder
import webkul.opencart.mobikul.model.SellerProfileModel.SellerProfile
import webkul.opencart.mobikul.model.SellerWriteReviewModel.SellerWriteAReview
import webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress
import webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod
import webkul.opencart.mobikul.model.SocailLoginModel.SocailLogin
import webkul.opencart.mobikul.utils.AppSharedPreference
import webkul.opencart.mobikul.ApiLoginModel
import webkul.opencart.mobikul.downLoadInfo.DownLoadInfoFile
import webkul.opencart.mobikul.helper.Utils.md5
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.model.BaseModel.AddHistory
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.DashboardMyOrder.DashboardMyOrder
import webkul.opencart.mobikul.model.DashboardOrderInfo.OrderInfo
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.model.GetProduct.ProductDetail
import webkul.opencart.mobikul.model.GetWishlist.GetWishlist
import webkul.opencart.mobikul.model.LoginModel.LoginModel
import webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress
import webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod
import webkul.opencart.mobikul.model.ProductCategory.ProductCategory
import webkul.opencart.mobikul.model.ProductSearch.ProductSearch
import webkul.opencart.mobikul.model.RegisterModel.RagisterData
import webkul.opencart.mobikul.model.RemoveFromCart.RemoveFromCart
import webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest
import webkul.opencart.mobikul.model.RewardDataModels.RewardData
import webkul.opencart.mobikul.model.SellerOrderModel.SellerOrderHistory
import webkul.opencart.mobikul.model.TransactionInfo.TransactionInfoDataModel
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.model.ViewNotificationModel.ViewNotification
import webkul.opencart.mobikul.ViewMoreDataForLatest
import webkul.opencart.mobikul.model.CmsPageModel.CmsPage
import webkul.opencart.mobikul.model.ConfirmModel.PayTabsResponse
import webkul.opencart.mobikul.model.ReviewListModel.ReviewList
import webkul.opencart.mobikul.model.SubcategoryModel.SubCategoryModel
import webkul.opencart.mobikul.model.ViewMoreFeaturedModel.ViewMoreFeatured

object RetrofitCallback {

    fun getInfo(mContext: Context, infoId: String, callback: Callback<CmsPage>) {
        ApiClient.getClient(mContext)
                ?.create(ApiInteface::class.java)
                ?.getInfo(
                        AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME),
                        infoId)
                ?.enqueue(callback)
    }

    fun getReviewList(mContext: Context, productId: String, page: String,
                      limit: String, callback: Callback<ReviewList>) {

        ApiClient.getClient(mContext)
                ?.create(ApiInteface::class.java)
                ?.getReviewList(
                        AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME),
                        productId,
                        page,
                        limit)
                ?.enqueue(callback)
    }

    fun getProductCarousal(mContext: Context, path: String, page: String, limit: String, order: String?,
                           sort: String?, callback: Callback<ViewMoreDataForLatest>) {
        ApiClient.getClient(mContext)
                ?.create(ApiInteface::class.java)
                ?.getHomepageCarousal("?route=api/wkrestapi/common/" + path,
                        AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME),
                        page,
                        limit,
                        Utils.getScreenWidth(),
                        order,
                        sort)
                ?.enqueue(callback)
    }


    fun reOrderProduct(mContext: Context, orderId: String, orderProductId: String, callback: Callback<AddToCartModel>) {
        ApiClient.getClient(mContext)
                ?.create(ApiInteface::class.java)
                ?.reorderProduct(AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME),
                        orderId,
                        orderProductId)
                ?.enqueue(callback)
    }

    fun addReturnDataRequest(mContext: Context, firstName: String, lastName: String,
                             email: String, telephone: String, orderId: String,
                             dateOrder: String, productName: String, model: String, quantity:
                             String, returnResonId: String, opend: String, comment: String, callback: Callback<BaseModel>) {

        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java)
                .addReturnDataRequest(firstName, lastName, email, telephone, orderId, dateOrder,
                        productName, model, quantity, returnResonId, opend, comment,
                        AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun returnDataRequest(mContext: Context, orderId: String, productId: String, callback: Callback<ReturnOrderRequest>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java)
                .retrunDataRequest(orderId = orderId,
                        productId = productId,
                        wk_token = AppSharedPreference.getWkToken(mContext,
                                Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun returnInfoCall(mContext: Context, returnId: String, callback: Callback<ReturnInfo>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java)
                .returnInfo(returnId, AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun orderReturnViewCall(mContext: Context, callback: Callback<OrderReturn>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java)
                .orderReturnView(AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun shippingAddressForPaymentMethodCheckout(mContext: Context, function: String, addressId: String, paymentAddress: String,
                                                callback: Callback<PaymentMethod>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java).shippingAddressForPaymentMethodCheckout(function, addressId,
                paymentAddress, AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun searchProduct(mContext: Context, search: String, baseModelCallback: Callback<SearchProduct>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java).searchProduct(search,
                AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(baseModelCallback)
    }


    fun checkEmail(mContext: Context, email: String, callback: Callback<BaseModel>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java).checkEmail(
                AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME), email)
        call.enqueue(callback)
    }

    fun getMultipartFile(mContext: Context, requestBody: RequestBody, part: MultipartBody.Part, callback: Callback<FileUpload>) {
        val call = ApiClient.getClient(mContext)!!.create(ApiInteface::class.java).fileUploadCall(requestBody, part)
        call.enqueue(callback)
    }

    fun getRewardPointCall(context: Context, reward: String, callback: Callback<BaseModel>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getRewardPoint(reward,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun getHomePageCall(context: Context, count: String, homeDataModelCallback: Callback<HomeDataModel>, screen_width: String, densityDpi: String) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).gethomedata(Utils.getScreenWidth(),
                if (count.equals("")) "10" else count, "1",
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(homeDataModelCallback)
    }

    fun apiLoginCall(context: Context, callback: Callback<ApiLoginModel>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).apiLogin(
                webkul.opencart.mobikul.credentials.AppCredentials.SOAP_USER_NAME,
                md5(webkul.opencart.mobikul.credentials.AppCredentials.SOAP_PASSWORD),
                AppSharedPreference.getCustomerId(context, Constant.CONFIGUREVIEW_SHARED_PREFERENCE_NAME),
                AppSharedPreference.getStoreCode(context, Constant.CONFIGUREVIEW_SHARED_PREFERENCE_NAME),
                AppSharedPreference.getCurrencyCode(context, Constant.CONFIGUREVIEW_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun getProductCall(context: Context, productId: String, callback: Callback<ProductDetail>) {
        val call = ApiClient.getClient(context)?.create(ApiInteface::class.java)?.getProduct(
                Utils.getScreenWidth(),
                productId,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call?.enqueue(callback)
    }

    fun addToCartCall(context: Context, productId: String, quantity: String, option: JSONObject, cartCallback: Callback<AddToCartModel>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addToCart(productId, quantity, option,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun addToCartWithoutOptionCall(context: Context, productId: String, quantity: String, cartCallback: Callback<AddToCartModel>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addToCartWithoutOption(productId, quantity,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }


    fun userLoginCall(context: Context, username: String, password: String, cartCallback: Callback<LoginModel>) {
        val cartCall = ApiClient.getClient(context)?.create(ApiInteface::class.java)?.userLogin(username, password,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall?.enqueue(cartCallback)
    }


    fun forgotPasswordCall(context: Context, username: String, cartCallback: Callback<BaseModel>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).forgotPassword(username,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun viewCartCall(context: Context, width: String, cartCallback: Callback<ViewCart>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).viewCart(width, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun removeFromCartCall(context: Context, key: String, cartCallback: Callback<RemoveFromCart>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).removeFromCart(key, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun updateCartCall(context: Context, `object`: JSONObject, cartCallback: Callback<BaseModel>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).updateCart(`object`, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun productCategoryCall(context: Context, path: String, pageNumber: String, screen_width: String, limit: String, sort: String,
                            order: String, filter: String?, cartCallback: Callback<ProductCategory>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).productCategory(pageNumber, limit,
                Utils.getScreenWidth(), path, sort, order, filter, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun productSearchCall(context: Context, search: String, pageNumber: String, screen_width: String, limit: String, sort: String, order: String, cartCallback: Callback<ProductSearch>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).productSearch(pageNumber, limit, Utils.getScreenWidth(), search, sort, order, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun dashboardMyOrderCall(context: Context, page: String, cartCallback: Callback<DashboardMyOrder>) {
        val cartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).dashboardMyorder(page, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        cartCall.enqueue(cartCallback)
    }

    fun languageCall(context: Context, code: String, baseModelCallback: Callback<BaseModel>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).homePageLanguage(code, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(baseModelCallback)
    }

    fun currencyCall(context: Context, code: String, baseModelCallback: Callback<BaseModel>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).homePageCurrency(code, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(baseModelCallback)
    }

    fun dashboardOrderInfoCall(context: Context, orderID: String, orderInfoCallback: Callback<OrderInfo>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).dashboarOrderInfo(orderID, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(orderInfoCallback)
    }

    fun addToWishlistCall(context: Context, productId: String, wishlistCallback: Callback<AddtoWishlist>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addToWishlist(productId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(wishlistCallback)
    }

    fun getWishlistCall(context: Context, callback: Callback<GetWishlist>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getWishlist(Utils.getScreenWidth(), AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun viewNotificationCall(context: Context, callback: Callback<ViewNotification>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).viewNotification(Utils.getScreenWidth(), AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(callback)
    }

    fun paymentAddressCheckoutCall(context: Context, function: String, paymentAddressCallback: Callback<PaymentAddress>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).paymentAddressCheckout(function, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(paymentAddressCallback)
    }

    fun shippingAddressCheckoutCall(context: Context, function: String, addressId: String, shippingAddressCallback: Callback<ShippingAddress>) {
        val shippingAddressCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).shippingAddressCheckout(function, addressId, "existing", AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        shippingAddressCall.enqueue(shippingAddressCallback)

    }

    fun shippingMethodCheckoutCall(context: Context, function: String, addressId: String, paymentAddressCallback: Callback<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).shippingMethodCheckout(function, addressId, "existing", AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(paymentAddressCallback)
    }

    fun paymentMethodCheckoutCall(context: Context, function: String, comment: String, shippingMethod: String, paymentMethodCallback: Callback<PaymentMethod>) {
        val paymentMethodCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).paymentMethodCheckout(function, shippingMethod, comment, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        paymentMethodCall.enqueue(paymentMethodCallback)
    }

    fun removeFromWishlist(context: Context, productId: String, baseModelCallback: Callback<BaseModel>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).removeFromWishlist(productId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(baseModelCallback)
    }

    fun addCustomerCall(context: Context, customer_group_id: String, firstName: String, lastName: String, email: String,
                        telephone: String, fax: String, company: String, address_1: String, address_2: String, city: String,
                        postCode: String, country_Id: String, zoneID: String, password: String, isSubscribe: String, agree: String,
                        check: String, storeName: String, customerLoginCallback: Callback<AddCustomer>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addCustomer(customer_group_id, firstName, lastName,
                email, telephone, fax, company, address_1, address_2,
                city, postCode, country_Id, zoneID, password, isSubscribe, agree, check, storeName,
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(customerLoginCallback)
    }

    fun confirmCheckoutCall(context: Context, function: String, width: String, paymentMethod: String, comment: String, agree: String, confirmOrderCallback: Callback<ConfirmOrder>) {
        val confirmOrderCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).confirmOrderCheckout(width, function,
                paymentMethod, comment, agree, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        confirmOrderCall.enqueue(confirmOrderCallback)
    }

    fun confirmOrderCall(context: Context, state: String?, confirmOrderCallback: Callback<webkul.opencart.mobikul.model.ConfirmOrderModel.ConfirmOrder>) {
        val confirmOrderCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java)
                .confirmOrder(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME), state)
        confirmOrderCall.enqueue(confirmOrderCallback)
    }

    fun manufactureInfoCall(context: Context, page: String, limit: String, width: String, manufacture: String, sort: String, order: String, manufactureCallback: Callback<Manufacture>) {
        val manufactureCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).manufactureInfo(page, limit, width, manufacture, sort, order, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        manufactureCall.enqueue(manufactureCallback)
    }

    fun guestCheckoutCall(context: Context, function: String, checkoutCallback: Callback<GuestCheckout>) {
        val guestCheckoutCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).guestCheckout(function, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        guestCheckoutCall.enqueue(checkoutCallback)
    }

    fun guestCheckoutForShippingMethod(context: Context, shipping: String, function: String, email: String, telephone: String, fax: String, firstName: String, lastName: String,
                                       company: String, address1: String, address2: String, city: String, postcode: String, country_id: String, zoneId: String, shippingMethodCallback: Callback<ShippingMethod>) {
        val shippingMethodCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).guestWithoutShipping(shipping, function, email, telephone,
                fax, firstName, lastName, company, address1, address2, city, postcode, country_id, zoneId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        shippingMethodCall.enqueue(shippingMethodCallback)
    }

    fun guestCheckoutForShippingAddress(context: Context, shipping: String, function: String, email: String, telephone: String, fax: String, firstName: String, lastName: String,
                                        company: String, address1: String, address2: String, city: String, postcode: String, country_id: String, zoneId: String, shippingMethodCallback: Callback<GuestShippingAddress>) {
        val shippingMethodCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).guestWithShipping(shipping, function, email, telephone,
                fax, firstName, lastName, company, address1, address2, city, postcode, country_id, zoneId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        shippingMethodCall.enqueue(shippingMethodCallback)
    }

    fun addAddressBook(context: Context, addressId: String?, addressBookCallback: Callback<EditAddressbook>) {
        val call = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addAddressBook(addressId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        call.enqueue(addressBookCallback)
    }

    fun getSubCategory(mContext: Context, id: String, callback: Callback<SubCategoryModel>) {
        ApiClient.getClient(mContext)
                ?.create(ApiInteface::class.java)
                ?.getSubCategoryApi(Utils.getScreenWidth(),
                        AppSharedPreference.getWkToken(mContext, Constant.CUSTOMER_SHARED_PREFERENCE_NAME),
                        id)
                ?.enqueue(callback)
    }

    fun writeReviewCall(context: Context, name: String, text: String, rating: String, productID: String, callback: Callback<BaseModel>) {
        val baseModelCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).writeReview(name, text, rating, productID, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        baseModelCall.enqueue(callback)
    }

    fun customerLogoutCall(context: Context, logoutCallback: Callback<CustomerLogout>) {
        val logoutCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).customerLogout(AppSharedPreference.getWkToken(context,
                Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        logoutCall.enqueue(logoutCallback)
    }

    fun myAccountCall(context: Context, myAccountCallback: Callback<MyAccount>) {
        val accountCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).myAccount(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountCall.enqueue(myAccountCallback)
    }

    fun addSocailLogin(context: Context, firstName: String, lastName: String, email: String, personId: String, socailLoginCallback: Callback<SocailLogin>) {
        val socailLoginCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addSocailLogin(firstName, email, lastName, personId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        socailLoginCall.enqueue(socailLoginCallback)
    }

    fun getSellerOrderCall(context: Context, page: String, filterOrder: String, filterDate: String, filterName: String, filterStatus: String, sellerOrderCallback: Callback<SellerOrder>) {
        val sellerOrderCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellerOrder(page, filterOrder, filterDate, filterName, filterStatus, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellerOrderCall.enqueue(sellerOrderCallback)
    }

    fun getSellerOrderNewCall(context: Context, orderId: String, sellerOrderCallback: Callback<SellerOrderHistory>) {
        val sellerOrderCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellerOrderNew(orderId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellerOrderCall.enqueue(sellerOrderCallback)
    }

    fun getSellerDashboardCall(context: Context, range: String, sellerDashboardCallback: Callback<SellerDashboard>) {
        val sellerDashboardCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellerDashboar(range, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellerDashboardCall.enqueue(sellerDashboardCallback)
    }

    fun getSellDataCall(context: Context, sellDataCallback: Callback<GetSellData>) {
        val sellDataCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellData(Utils.getScreenWidth(), AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellDataCall.enqueue(sellDataCallback)
    }

    fun getSellerProfileCall(context: Context, id: String, sellerProfileCallback: Callback<SellerProfile>) {
        val sellerProfileCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellerProfile(Utils.getScreenWidth(), id, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellerProfileCall.enqueue(sellerProfileCallback)
    }

    fun getSellerWriteAReviewCall(context: Context, seller_id: String, name: String, text: String, price_rating: String, value_rating: String, quality_rating: String, sellerWriteAReviewCallback: Callback<SellerWriteAReview>) {
        val sellerWriteAReviewCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getSellerReview(seller_id, name, text, price_rating, value_rating, quality_rating, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        sellerWriteAReviewCall.enqueue(sellerWriteAReviewCallback)
    }

    fun getEmptyCartCall(context: Context, emptyCartCallback: Callback<EmptyCart>) {
        val emptyCartCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getEmptyCart(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        emptyCartCall.enqueue(emptyCartCallback)
    }

    fun getAddressCall(context: Context, getAddressCallback: Callback<GetAddress>) {
        val getAddressCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getAdress(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        getAddressCall.enqueue(getAddressCallback)
    }

    fun deleteAddressCall(context: Context, addressId: String, baseModelCallback: Callback<BaseModel>) {
        val baseModelCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).deleteAddress(addressId, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        baseModelCall.enqueue(baseModelCallback)
    }


    /* Implementing changes from vlooey to retrofit*/

    fun accountInfo(context: Context, firstName: String, lastName: String, emailAddr: String, accinfoPhoneValue: String, accinfoFaxValue: String, baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).accountInfo(firstName, lastName, emailAddr, accinfoPhoneValue, accinfoFaxValue, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }


    fun contactSeller(context: Context, seller_id: String, subject: String, message: String, baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).contactSaeller(seller_id, subject, message, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }

    fun applyCoupanCode(context: Context, coupan: String, baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).applyCoupan(coupan, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }


    fun applyVoucherCode(context: Context, coupan: String, baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).applyVoucher(coupan, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }

    fun editPassword(context: Context, newPassword: String, baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).editPassword(newPassword, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }

    fun getTransactionInfo(context: Context, page: String, baseModelCallback: RetrofitCustomCallback<TransactionInfoDataModel>) {
        val getTransactionInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getTransactionInfo(page, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        getTransactionInfoCall.enqueue(baseModelCallback)
    }


    fun getRewardPoint(context: Context, page: String, baseModelCallback: RetrofitCustomCallback<RewardData>) {
        val getTransactionInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getRewardPoints(page, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        getTransactionInfoCall.enqueue(baseModelCallback)
    }


    fun addHistory(context: Context, orderId: String, selected_order_status_id: String, notifyAdmin: String, notify: String, commentToAdmin: String, baseModelCallback: RetrofitCustomCallback<AddHistory>) {
        val accountInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addHistory(orderId, selected_order_status_id, notifyAdmin, notify, commentToAdmin, AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        accountInfoCall.enqueue(baseModelCallback)
    }

    fun addAddress(context: Context, customerId: String, addressId: String?, firstName: String, lastName: String, company: String, streetAdd1: String,
                   streetAdd2: String, city: String, state: String, zip: String, country_id: String, defaultAddress: Int,
                   baseModelCallback: RetrofitCustomCallback<BaseModel>) {
        val setNewsletterCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).addAddress(customerId, addressId, firstName, lastName,
                company, streetAdd1, streetAdd2, city, state, zip, country_id, defaultAddress.toString(),
                AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        setNewsletterCall.enqueue(baseModelCallback)
    }


    fun registerCalling(context: Context, baseModelCallback: RetrofitCustomCallback<RagisterData>) {
        val getTransactionInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).registerUser(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        getTransactionInfoCall.enqueue(baseModelCallback)
    }


    fun getGdprStatusCalling(context: Context, baseModelCallback: RetrofitCustomCallback<GdprModel>) {
        val getTransactionInfoCall = ApiClient.getClient(context)!!.create(ApiInteface::class.java).getGdprStatus(AppSharedPreference.getWkToken(context, Constant.CUSTOMER_SHARED_PREFERENCE_NAME))
        getTransactionInfoCall.enqueue(baseModelCallback)
    }

    fun verifyTransactionCall(context: Context, merchant_email: String, secret_key: String, transaction_id: String, order_id: String, confirmOrderCallback: Callback<PayTabsResponse>) {
        val confirmOrderCall = ApiClient.getClientNew(context)!!.create(ApiInteface::class.java).verifyTransaction(merchant_email, secret_key, transaction_id, order_id)
        confirmOrderCall.enqueue(confirmOrderCallback)
    }


}
