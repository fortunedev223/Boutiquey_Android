package webkul.opencart.mobikul.networkManager

import org.json.JSONObject

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*
import webkul.opencart.mobikul.ApiLoginModel
import webkul.opencart.mobikul.deliveryboy.Model.LocationModel
import webkul.opencart.mobikul.downLoadInfo.DownLoadInfoFile
import webkul.opencart.mobikul.model.AddCustomerModel.AddCustomer
import webkul.opencart.mobikul.model.AddToCart.AddToCartModel
import webkul.opencart.mobikul.model.AddToWishlist.AddtoWishlist
import webkul.opencart.mobikul.model.BaseModel.AddHistory
import webkul.opencart.mobikul.model.BaseModel.BaseModel
import webkul.opencart.mobikul.model.ConfirmModel.ConfirmOrder
import webkul.opencart.mobikul.model.CustomerLogoutModel.CustomerLogout
import webkul.opencart.mobikul.model.DashboardMyOrder.DashboardMyOrder
import webkul.opencart.mobikul.model.DashboardOrderInfo.OrderInfo
import webkul.opencart.mobikul.model.EditAddressBookModel.EditAddressbook
import webkul.opencart.mobikul.model.EmptyCartModel.EmptyCart
import webkul.opencart.mobikul.model.FileUpload.FileUpload
import webkul.opencart.mobikul.model.GDPRStatus.GdprModel
import webkul.opencart.mobikul.model.GetAddressModel.GetAddress
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.model.GetProduct.ProductDetail
import webkul.opencart.mobikul.model.GetSellDataModel.GetSellData
import webkul.opencart.mobikul.model.GetWishlist.GetWishlist
import webkul.opencart.mobikul.model.GuestCheckoutModel.GuestCheckout
import webkul.opencart.mobikul.model.GuestShippingAddressModel.GuestShippingAddress
import webkul.opencart.mobikul.model.LoginModel.LoginModel
import webkul.opencart.mobikul.model.ManufactureInfoModel.Manufacture
import webkul.opencart.mobikul.model.MyAccountModel.MyAccount
import webkul.opencart.mobikul.model.OrderReturnInfoModel.ReturnInfo
import webkul.opencart.mobikul.model.OrderReturnModel.OrderReturn
import webkul.opencart.mobikul.model.PaymentAddressModel.PaymentAddress
import webkul.opencart.mobikul.model.PaymentMethodModel.PaymentMethod
import webkul.opencart.mobikul.model.ProductCategory.ProductCategory
import webkul.opencart.mobikul.model.ProductSearch.ProductSearch
import webkul.opencart.mobikul.model.RegisterModel.RagisterData
import webkul.opencart.mobikul.model.RemoveFromCart.RemoveFromCart
import webkul.opencart.mobikul.model.ReturnOrderRequestModel.ReturnOrderRequest
import webkul.opencart.mobikul.model.RewardDataModels.RewardData
import webkul.opencart.mobikul.model.SearchProductModel.SearchProduct
import webkul.opencart.mobikul.model.SellerDashboardModel.SellerDashboard
import webkul.opencart.mobikul.model.SellerOrderModel.SellerOrder
import webkul.opencart.mobikul.model.SellerOrderModel.SellerOrderHistory
import webkul.opencart.mobikul.model.SellerProfileModel.SellerProfile
import webkul.opencart.mobikul.model.SellerWriteReviewModel.SellerWriteAReview
import webkul.opencart.mobikul.model.ShippingAddressModel.ShippingAddress
import webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod
import webkul.opencart.mobikul.model.SocailLoginModel.SocailLogin
import webkul.opencart.mobikul.model.TransactionInfo.TransactionInfoDataModel
import webkul.opencart.mobikul.model.VIewCartModel.ViewCart
import webkul.opencart.mobikul.model.ViewNotificationModel.ViewNotification
import webkul.opencart.mobikul.ViewMoreDataForLatest
import webkul.opencart.mobikul.model.CmsPageModel.CmsPage
import webkul.opencart.mobikul.model.ConfirmModel.PayTabsResponse
import webkul.opencart.mobikul.model.ReviewListModel.ReviewList
import webkul.opencart.mobikul.model.SubcategoryModel.SubCategoryModel
import webkul.opencart.mobikul.model.ViewMoreFeaturedModel.ViewMoreFeatured


interface ApiInteface {


    @FormUrlEncoded
    @POST(RETURN_INFO)
    fun returnInfo(
            @Field("return_id") returnId: String,
            @Field("wk_token") wkToken: String): Call<ReturnInfo>

    @FormUrlEncoded
    @POST(RETURN_VIEW)
    fun orderReturnView(
            @Field("wk_token") wkToken: String
    ): Call<OrderReturn>


    @FormUrlEncoded
    @POST(SEARCH_PRODUCT)
    fun searchProduct(
            @Field("search") search: String,
            @Field("wk_token") wkToken: String
    ): Call<SearchProduct>

    @FormUrlEncoded
    @POST(CHECK_EMAIL)
    fun checkEmail(
            @Field("wk_token") wk_token: String,
            @Field("email") email: String
    ): Call<BaseModel>

    @Multipart
    @POST("?route=tool/upload")
    fun fileUploadCall(
            @Part("type") requestBody: RequestBody,
            @Part file: MultipartBody.Part): Call<FileUpload>


    @FormUrlEncoded
    @POST(REWARD_POINTS)
    fun getRewardPoint(
            @Field("reward") reward: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(HOME_PAGE_API)
    fun gethomedata(
            @Field("width") width: String,
            @Field("count") count: String,
            @Field("mfactor") mfactor: String,
            @Field("wk_token") wk_token: String): Call<HomeDataModel>


    @FormUrlEncoded
    @POST(API_Login)
    fun apiLogin(
            @Field("apiKey") key: String,
            @Field("apiPassword") password: String,
            @Field("customer_id") id: String,
            @Field("language") language: String,
            @Field("currency") currency: String): Call<ApiLoginModel>


    @FormUrlEncoded
    @POST(Product_Detail_Api)
    fun getProduct(
            @Field("width") width: String,
            @Field("product_id") product_id: String,
            @Field("wk_token") wk_token: String): Call<ProductDetail>

    @FormUrlEncoded
    @POST(Add_To_Cart)
    fun addToCart(
            @Field("product_id") product_id: String,
            @Field("quantity") quantity: String,
            @Field("option") option: JSONObject,
            @Field("wk_token") wk_token: String): Call<AddToCartModel>

    @FormUrlEncoded
    @POST(Add_To_Cart)
    fun addToCartWithoutOption(
            @Field("product_id") product_id: String,
            @Field("quantity") quantity: String,
            @Field("wk_token") wk_token: String): Call<AddToCartModel>

    @FormUrlEncoded
    @POST(User_Login)
    fun userLogin(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("wk_token") wk_token: String): Call<LoginModel>


    @FormUrlEncoded
    @POST(FORGOT_PASSWORD)
    fun forgotPassword(
            @Field("email") email: String,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(View_Cart)
    fun viewCart(
            @Field("width") width: String,
            @Field("wk_token") wk_token: String): Call<ViewCart>

    @FormUrlEncoded
    @POST(Remove_From_Cart)
    fun removeFromCart(
            @Field("key") key: String,
            @Field("wk_token") wk_token: String): Call<RemoveFromCart>

    @FormUrlEncoded
    @POST(Updata_Cart)
    fun updateCart(
            //            @Field("key") String key,
            @Field("quantity") quantity: JSONObject,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(Product_Category)
    fun productCategory(
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("path") path: String,
            @Field("sort") sort: String,
            @Field("order") order: String,
            @Field("filter") filter: String?,
            @Field("wk_token") wk_token: String): Call<ProductCategory>
    //    filter=4,1,2,6

    @FormUrlEncoded
    @POST(Product_Search)
    fun productSearch(
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("search") search: String,
            @Field("sort") sort: String,
            @Field("order") order: String,
            @Field("wk_token") wk_token: String): Call<ProductSearch>

    @FormUrlEncoded
    @POST(Dashboard_MyOrder)
    fun dashboardMyorder(
            @Field("page") page: String,
            @Field("wk_token") wk_token: String): Call<DashboardMyOrder>

    @FormUrlEncoded
    @POST(HOME_PAGE_LANGUAGE)
    fun homePageLanguage(
            @Field("code") code: String,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(HOME_PAGE_CURRENCY)
    fun homePageCurrency(
            @Field("code") code: String,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(DASHBOARD_ORDER_INFO)
    fun dashboarOrderInfo(
            @Field("order_id") order_id: String,
            @Field("wk_token") wk_token: String): Call<OrderInfo>

    @FormUrlEncoded
    @POST(ADD_TO_WISHLIST)
    fun addToWishlist(
            @Field("product_id") productId: String,
            @Field("wk_token") wk_token: String): Call<AddtoWishlist>


    @FormUrlEncoded
    @POST(GET_WISHLIST)
    fun getWishlist(
            @Field("width") width: String,
            @Field("wk_token") wk_token: String): Call<GetWishlist>

    @FormUrlEncoded
    @POST(REMOVE_FROM_WISHLIST)
    fun removeFromWishlist(
            @Field("product_id") productId: String,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(VIEW_NOTIFICATION)
    fun viewNotification(
            @Field("width") width: String,
            @Field("wk_token") wk_token: String): Call<ViewNotification>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun paymentAddressCheckout(
            @Field("function") function: String,
            @Field("wk_token") wk_token: String): Call<PaymentAddress>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun shippingAddressCheckout(
            @Field("function") function: String,
            @Field("address_id") address: String,
            @Field("payment_address") shippingAddress: String,
            @Field("wk_token") wk_token: String): Call<ShippingAddress>


    @FormUrlEncoded
    @POST(CHECKOUT)
    fun shippingAddressForPaymentMethodCheckout(
            @Field("function") function: String,
            @Field("address_id") address: String,
            @Field("payment_address") shippingAddress: String,
            @Field("wk_token") wk_token: String): Call<PaymentMethod>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun shippingMethodCheckout(
            @Field("function") function: String,
            @Field("address_id") address: String,
            @Field("shipping_address") shippingAddress: String,
            @Field("wk_token") wk_token: String): Call<webkul.opencart.mobikul.model.ShippingMethodModel.ShippingMethod>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun paymentMethodCheckout(
            @Field("function") function: String,
            @Field("shipping_method") shippingMethod: String,
            @Field("comment") comment: String,
            @Field("wk_token") wk_token: String): Call<PaymentMethod>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun confirmOrderCheckout(
            @Field("width") width: String,
            @Field("function") function: String,
            @Field("payment_method") paymentMethod: String,
            @Field("comment") comment: String,
            @Field("agree") agree: String,
            @Field("wk_token") wk_token: String): Call<ConfirmOrder>


    @FormUrlEncoded
    @POST(NEW_CHECKOUT)
    fun newCheckout(@FieldMap(encoded = true) a: Map<String, Object>,
                    @Field("wk_token") wk_token: String): Call<BaseModel>


    @FormUrlEncoded
    @POST(ADD_CUSTOMER)
    fun addCustomer(
            @Field("customer_group_id") customer_group_id: String,
            @Field("firstname") firstName: String,
            @Field("lastname") lastName: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("fax") fax: String,
            @Field("company") company: String,
            @Field("address_1") address_1: String,
            @Field("address_2") address_2: String,
            @Field("city") city: String,
            @Field("postcode") postCode: String,
            @Field("country_id") country_Id: String,
            @Field("zone_id") zoneID: String,
            @Field("password") password: String,
            @Field("isSubscribe") isSubscribe: String,
            @Field("agree") agree: String,
            @Field("tobecomepartner") tobecomepartner: String?,
            @Field("shoppartner") shoppartner: String?,
            @Field("wk_token") wk_token: String
    ): Call<AddCustomer>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun guestCheckout(
            @Field("function") function: String,
            @Field("wk_token") wk_token: String
    ): Call<GuestCheckout>

    @FormUrlEncoded
    @POST(CONFIRM_ORDER)
    fun confirmOrder(
            @Field("wk_token") wk_token: String,
            @Field("state") state: String?): Call<webkul.opencart.mobikul.model.ConfirmOrderModel.ConfirmOrder>

    @FormUrlEncoded
    @POST(MANUFACTURE)
    fun manufactureInfo(
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("manufacturer_id") manufacturer_id: String,
            @Field("sort") sort: String,
            @Field("order") order: String,
            @Field("wk_token") wk_token: String): Call<Manufacture>


    @FormUrlEncoded
    @POST(CHECKOUT)
    fun guestWithoutShipping(
            @Field("shipping_address") shipping_address: String,
            @Field("function") function: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("fax") fax: String,
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("company") company: String,
            @Field("address_1") address_1: String,
            @Field("address_2") address_2: String,
            @Field("city") city: String,
            @Field("postcode") postCode: String,
            @Field("country_id") country_Id: String,
            @Field("zone_id") zoneID: String,
            @Field("wk_token") wk_token: String
    ): Call<ShippingMethod>

    @FormUrlEncoded
    @POST(CHECKOUT)
    fun guestWithShipping(
            @Field("shipping_address") shipping_address: String,
            @Field("function") function: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("fax") fax: String,
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("company") company: String,
            @Field("address_1") address_1: String,
            @Field("address_2") address_2: String,
            @Field("city") city: String,
            @Field("postcode") postCode: String,
            @Field("country_id") country_Id: String,
            @Field("zone_id") zoneID: String,
            @Field("wk_token") wk_token: String
    ): Call<GuestShippingAddress>

    @FormUrlEncoded
    @POST(ADDADDRESSBOOK)
    fun addAddressBook(
            @Field("address_id") addressId: String?,
            @Field("wk_token") wk_token: String): Call<EditAddressbook>

    @FormUrlEncoded
    @POST(WRITE_REVIEW)
    fun writeReview(
            @Field("name") name: String,
            @Field("text") text: String,
            @Field("rating") rating: String,
            @Field("product_id") product_id: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(CUSTOMER_LOGOUT)
    fun customerLogout(
            @Field("wk_token") wk_token: String
    ): Call<CustomerLogout>

    @FormUrlEncoded
    @POST(MY_ACCOUNT)
    fun myAccount(
            @Field("wk_token") wk_token: String
    ): Call<MyAccount>

    @FormUrlEncoded
    @POST(ADD_SOCAIL_LOGIN)
    fun addSocailLogin(
            @Field("firstname") firstname: String,
            @Field("email") email: String,
            @Field("lastname") lastname: String,
            @Field("personId") personId: String,
            @Field("wk_token") wk_token: String
    ): Call<SocailLogin>

    //{"page":1,"filter_order":"123","filter_date":"2017-10-23","filter_name":"test","filter_status":"Pending","wk_token":"4t9lreb8m9c94fdlqddgldqk66"}

    @FormUrlEncoded
    @POST(SELLAR_ORDER)
    fun getSellerOrder(
            @Field("page") page: String,
            @Field("filter_order") filterOrder: String,
            @Field("filter_date") filter_date: String,
            @Field("filter_name") filter_name: String,
            @Field("filter_status") filter_status: String,
            @Field("wk_token") wk_token: String
    ): Call<SellerOrder>


    @FormUrlEncoded
    @POST(SELLAR_ORDER)
    fun getSellerOrderNew(
            @Field("id") order_id: String,
            @Field("wk_token") wk_token: String
    ): Call<SellerOrderHistory>

    @FormUrlEncoded
    @POST(SELL_DATA)
    fun getSellData(
            @Field("width") width: String,
            @Field("wk_token") wk_token: String
    ): Call<GetSellData>

    @FormUrlEncoded
    @POST(GET_SELLER_DASHBOARD_DATA)
    fun getSellerDashboar(
            @Field("range") range: String,
            @Field("wk_token") wk_token: String
    ): Call<SellerDashboard>

    @FormUrlEncoded
    @POST(SELLER_PROFILE)
    fun getSellerProfile(
            @Field("width") width: String,
            @Field("id") id: String,
            @Field("wk_token") wk_token: String
    ): Call<SellerProfile>

    @FormUrlEncoded
    @POST(SELLER_WRITE_A_REVIEW)
    fun getSellerReview(
            @Field("seller_id") seller_id: String,
            @Field("name") name: String,
            @Field("text") text: String,
            @Field("price_rating") price_rating: String,
            @Field("value_rating") value_rating: String,
            @Field("quality_rating") quality_rating: String,
            @Field("wk_token") wk_token: String
    ): Call<SellerWriteAReview>

    @FormUrlEncoded
    @POST(EMPTY_CART)
    fun getEmptyCart(
            @Field("wk_token") wk_token: String
    ): Call<EmptyCart>

    @FormUrlEncoded
    @POST(GET_ADDRESS)
    fun getAdress(
            @Field("wk_token") wk_token: String
    ): Call<GetAddress>

    @FormUrlEncoded
    @POST(DELETE_ADDRESS)
    fun deleteAddress(
            @Field("address_id") addressId: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>


    @FormUrlEncoded
    @POST(ACCOUNTINFO)
    fun accountInfo(
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("fax") fax: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(CONTACT_SELLER)
    fun contactSaeller(
            @Field("seller_id") seller_id: String,
            @Field("subject") subject: String,
            @Field("message") message: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>


    @FormUrlEncoded
    @POST(APPLY_COUPAN)
    fun applyCoupan(
            @Field("coupon") coupon: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>


    @FormUrlEncoded
    @POST(APPLY_VOUCHER)
    fun applyVoucher(
            @Field("voucher") voucher: String,
            @Field("wk_token") wk_token: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(REMOVE_POINTS)
    fun removePoints(
            @Field("wk_token") wkToken: String
    ): Call<BaseModel>


    @FormUrlEncoded
    @POST(EDIT_PASSWORD)
    fun editPassword(
            @Field("password") voucher: String,
            @Field("wk_token") wkToken: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(ADD_HISTORY)
    fun addHistory(
            @Field("order_id") orderId: String,
            @Field("order_status_id") selected_order_status_id: String,
            @Field("notifyAdmin") notifyAdmin: String,
            @Field("notify") notify: String,
            @Field("comment") commentToAdmin: String,
            @Field("wk_token") wkToken: String
    ): Call<AddHistory>


    @FormUrlEncoded
    @POST(GET_TRANSACTION_INFO)
    fun getTransactionInfo(
            @Field("page") page: String,
            @Field("wk_token") wkToken: String
    ): Call<TransactionInfoDataModel>

    @FormUrlEncoded
    @POST(SET_NEWS_LETTER)
    fun setNewLetter(
            @Field("newsletter") newsletter: String,
            @Field("wk_token") wkToken: String
    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(GET_REWARD_POINT)
    fun getRewardPoints(
            @Field("page") page: String,
            @Field("wk_token") wkToken: String
    ): Call<RewardData>


    @FormUrlEncoded
    @POST(DOWNLOAD_INFO)
    fun getDownloadInfo(
            @Field("page") page: String,
            @Field("wk_token") wkToken: String
    ): Call<DownLoadInfoFile>


    @FormUrlEncoded
    @POST(REGISTER_API)
    fun registerUser(
            @Field("wk_token") wkToken: String
    ): Call<RagisterData>

//    @FormUrlEncoded
//    @POST(ACCOUNTINFO)
//    fun writeProductReview(
//            @Field("userName") userName: String,
//            @Field("text") cmt: String,
//            @Field("rating") currentRating: String,
//            @Field("product_id") product_id: String,
//            @Field("wk_token") wk_token: String
//    ): Call<BaseModel>

    @FormUrlEncoded
    @POST(RETURN_REQUEST_DATA)
    fun retrunDataRequest(@Field("order_id") orderId: String,
                          @Field("product_id") productId: String,
                          @Field("wk_token") wk_token: String): Call<ReturnOrderRequest>

    @FormUrlEncoded
    @POST(HOME_PAGE_FEATURED)
    fun getHomePageFeatureProdMore(
            @Field("wk_token") wk_token: String,
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("order") order: String,
            @Field("sort") sort: String): Call<ViewMoreFeatured>

    @FormUrlEncoded
    @POST(HOME_PAGE_LATEST_PRODUCT)
    fun getHomePageLatestProdMore(
            @Field("wk_token") wk_token: String,
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("order") order: String,
            @Field("sort") sort: String): Call<ViewMoreDataForLatest>

    @FormUrlEncoded
    @POST(GDPR_STATUS)
    fun getGdprStatus(
            @Field("wk_token") wk_token: String
    ): Call<GdprModel>

    @FormUrlEncoded
    @POST(ADDADDRESS)
    fun addAddress(@Field("customer_id") customer_id: String,
                   @Field("address_id") addressId: String?,
                   @Field("firstname") firstname: String,
                   @Field("lastname") lastname: String,
                   @Field("company") company: String,
                   @Field("address_1") address_1: String,
                   @Field("address_2") address_2: String,
                   @Field("city") city: String,
                   @Field("zone_id") zone_id: String,
                   @Field("postcode") postcode: String,
                   @Field("country_id") country_id: String,
                   @Field("default") default: String,
                   @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(ADD_RETURN_REQUEST)
    fun addReturnDataRequest(
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("email") email: String,
            @Field("telephone") telephone: String,
            @Field("order_id") order_id: String,
            @Field("date_ordered") dateOrder: String,
            @Field("product") productName: String,
            @Field("model") model: String,
            @Field("quantity") quantity: String,
            @Field("return_reason_id") returnRequestId: String,
            @Field("opened") opened: String,
            @Field("comment") comment: String,
            @Field("wk_token") wk_token: String): Call<BaseModel>

    @FormUrlEncoded
    @POST(REORDER)
    fun reorderProduct(
            @Field("wk_token") wk_token: String,
            @Field("order_id") orderId: String,
            @Field("order_product_id") orderProductId: String): Call<AddToCartModel>


    @Multipart
    @POST("?route=api/wkrestapi/marketplace/upload")
    fun fileUploadCallNew(
            @Part("type") requestBody: RequestBody, @Part("wk_token") requestBody1: RequestBody,
            @Part file: MultipartBody.Part): Call<FileUpload>

    @FormUrlEncoded
    @POST(GET_DBOY_LOCATION)
    fun getDboyLocation(
            @Field("wk_token") wkToken: String,
            @Field("id") id: String
    ): Call<LocationModel>

    @FormUrlEncoded
    @POST(Companion.SUBCATEGORY_API)
    fun getSubCategoryApi(
            @Field("width") width: String,
            @Field("wk_token") wkToken: String,
            @Field("category_id") id: String): Call<SubCategoryModel>


    @FormUrlEncoded
    @POST
    fun getHomepageCarousal(
            @Url url: String,
            @Field("wk_token") wkToken: String,
            @Field("page") page: String,
            @Field("limit") limit: String,
            @Field("width") width: String,
            @Field("order") order: String?,
            @Field("sort") sort: String?
    ): Call<ViewMoreDataForLatest>


    @FormUrlEncoded
    @POST(REVIEW_LIST)
    fun getReviewList(
            @Field("wk_token") wkToken: String,
            @Field("product_id") productId: String,
            @Field("page") page: String,
            @Field("limit") limit: String
    ): Call<ReviewList>


    @FormUrlEncoded
    @POST(FOOTER_MENU)
    fun getInfo(
            @Field("wk_token") wkToken: String,
            @Field("information_id") informationId: String
    ): Call<CmsPage>


    @FormUrlEncoded
    @POST(PAYTABS_TRANS_VERIFICATION)
    fun verifyTransaction(
            @Field("merchant_email") merchant_email: String,
            @Field("secret_key") secret_key: String,
            @Field("transaction_id") transaction_id: String,
            @Field("order_id") order_id: String): Call<PayTabsResponse>


    companion object {
        const val PAYTABS_TRANS_VERIFICATION = "apiv2/verify_payment_transaction"
        const val API_Login = "index.php/?route=api/wkrestapi/common/apiLogin"
        const val HOME_PAGE_API = "index.php/?route=api/wkrestapi/common/getHomepage"
        const val SUBCATEGORY_API = "index.php/?route=api/wkrestapi/common/getSubCategory"
        const val REMOVE_POINTS = "index.php/?route=api/wkrestapi/common/removePoints"
        const val Product_Detail_Api = "index.php/?route=api/wkrestapi/catalog/getProduct"
        const val Add_To_Cart = "index.php/?route=api/wkrestapi/cart/addToCart"
        const val User_Login = "index.php/?route=api/wkrestapi/customer/customerLogin"
        const val View_Cart = "index.php/?route=api/wkrestapi/cart/viewCart"
        const val APPLY_COUPAN = "index.php/?route=api/wkrestapi/cart/applyCoupon"
        const val APPLY_VOUCHER = "index.php/?route=api/wkrestapi/cart/applyVoucher"
        const val Remove_From_Cart = "index.php/?route=api/wkrestapi/cart/removeFromCart"
        const val Updata_Cart = "index.php/?route=api/wkrestapi/cart/updateCart"
        const val Product_Category = "index.php/?route=api/wkrestapi/catalog/productCategory"
        const val Product_Search = "index.php/?route=api/wkrestapi/catalog/productSearch"
        const val Dashboard_MyOrder = "index.php/?route=api/wkrestapi/customer/getOrders"
        const val HOME_PAGE_LANGUAGE = "index.php/?route=api/wkrestapi/common/language"
        const val HOME_PAGE_CURRENCY = "index.php/?route=api/wkrestapi/common/currency"
        const val HOME_PAGE_FEATURED = "index.php/?route=api/wkrestapi/common/featured"
        const val GDPR_STATUS = "index.php/?route=api/wkrestapi/common/getGDPR"
        const val HOME_PAGE_LATEST_PRODUCT = "index.php/?route=api/wkrestapi/common/latestProduct"
        const val DASHBOARD_ORDER_INFO = "index.php/?route=api/wkrestapi/customer/getOrderInfo"
        const val DOWNLOAD_INFO = "index.php/?route=api/wkrestapi/customer/getDownloadInfo"
        const val ADD_TO_WISHLIST = "index.php/?route=api/wkrestapi/catalog/addToWishlist"
        const val GET_WISHLIST = "index.php/?route=api/wkrestapi/customer/getWishlist"
        const val REGISTER_API = "index.php/?route=api/wkrestapi/customer/registerAccount"
        const val VIEW_NOTIFICATION = "index.php/?route=api/wkrestapi/catalog/viewNotifications"
        const val CHECKOUT = "index.php/?route=api/wkrestapi/checkout/checkout"
        const val NEW_CHECKOUT = "index.php/?route=api/wkrestapi/checkout/checkout"
        const val REMOVE_FROM_WISHLIST = "index.php/?route=api/wkrestapi/customer/removeFromWishlist"
        const val CONFIRM_ORDER = "index.php/?route=api/wkrestapi/checkout/confirmOrder"
        const val MANUFACTURE = "index.php/?route=api/wkrestapi/catalog/manufacturerInfo"
        const val ADDADDRESSBOOK = "index.php/?route=api/wkrestapi/customer/getAddress"
        const val ADDADDRESS = "index.php/?route=api/wkrestapi/customer/addAddress"
        const val WRITE_REVIEW = "index.php/?route=api/wkrestapi/catalog/writeProductReview"
        const val CUSTOMER_LOGOUT = "index.php/?route=api/wkrestapi/customer/customerLogout"
        const val GET_TRANSACTION_INFO = "index.php/?route=api/wkrestapi/customer/getTransactionInfo"
        const val GET_REWARD_POINT = "index.php/?route=api/wkrestapi/customer/getRewardInfo"
        const val SET_NEWS_LETTER = "index.php/?route=api/wkrestapi/customer/setNewsletter"
        const val EDIT_PASSWORD = "index.php/?route=api/wkrestapi/customer/editPassword"
        const val ADD_CUSTOMER = "index.php/?route=api/wkrestapi/customer/addCustomer"
        const val ADD_SOCAIL_LOGIN = "index.php/?route=api/wkrestapi/customer/addSocialCustomer"
        const val MY_ACCOUNT = "index.php/?route=api/wkrestapi/customer/myAccount"
        const val FORGOT_PASSWORD = "index.php/?route=api/wkrestapi/customer/forgotPassword"
        const val MY_WALLET = "index.php/?route=api/wkrestapi/customer/getWalletHistory"
        const val SELLAR_ORDER = "index.php/?route=api/wkrestapi/marketplace/getSellerOrders"
        const val ADD_HISTORY = "index.php/?route=api/wkrestapi/marketplace/addHistory"
        const val SELL_DATA = "index.php/?route=api/wkrestapi/marketplace/getSellData"
        const val GET_SELLER_DASHBOARD_DATA = "index.php/?route=api/wkrestapi/marketplace/getDashbordData"
        const val SELLER_PROFILE = "index.php/?route=api/wkrestapi/marketplace/getSellerProfile"
        const val CONTACT_SELLER = "index.php/?route=api/wkrestapi/marketplace/contactSeller"
        const val SELLER_WRITE_A_REVIEW = "index.php/?route=api/wkrestapi/marketplace/writeReview"
        const val EMPTY_CART = "index.php/?route=api/wkrestapi/cart/clearCart"
        const val GET_ADDRESS = "index.php/?route=api/wkrestapi/customer/getAddresses"
        const val DELETE_ADDRESS = "index.php/?route=api/wkrestapi/customer/deleteAddress"
        const val ACCOUNTINFO = "index.php/?route=api/wkrestapi/customer/editCustomer"
        const val REWARD_POINTS = "index.php/?route=api/wkrestapi/cart/applyPoints"
        const val CHECK_EMAIL = "index.php/?route=api/wkrestapi/customer/checkEmail"
        const val SEARCH_PRODUCT = "index.php/?route=api/wkrestapi/catalog/searchSuggest"
        const val RETURN_VIEW = "index.php/?route=api/wkrestapi/customer/getReturns"
        const val RETURN_INFO = "index.php/?route=api/wkrestapi/customer/getReturnInfo"
        const val RETURN_REQUEST_DATA = "index.php/?route=api/wkrestapi/customer/addReturnData"
        const val ADD_RETURN_REQUEST = "index.php/?route=api/wkrestapi/customer/addReturn"
        const val REORDER = "index.php/?route=api/wkrestapi/customer/reorder"
        const val GET_DBOY_LOCATION = "index.php/?route=api/wkrestapi/deliveryboy/getTrackData"
        const val REVIEW_LIST = "index.php/?route=api/wkrestapi/catalog/getReviews"
        const val FOOTER_MENU = "index.php/?route=api/wkrestapi/common/getInformationPage"
    }

}
