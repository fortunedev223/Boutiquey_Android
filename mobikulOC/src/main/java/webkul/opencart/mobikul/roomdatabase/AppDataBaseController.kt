package webkul.opencart.mobikul.roomdatabase

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.model.GetHomePage.HomeDataModel
import webkul.opencart.mobikul.model.GetProduct.ProductDetail
import webkul.opencart.mobikul.roomtable.HomeDataTable


class AppDataBaseController {
    companion object {
        fun setRecentViewedProduct(mContext: Context, productDetail: ProductDetail?) {
            val option = productDetail!!.options!!.isNotEmpty()
            val l = AppDataBase.getAppDataBaseInstance(mContext).getDao().insertRecentViewedProduct(
                    RecentViewedTable(0,
                            productId = productDetail.productId.toString(),
                            prouductName = productDetail.getName(),
                            productImage = productDetail.images!![0].popup!!,
                            productPrice = productDetail.price!!,
                            productTimeStamp = (System.currentTimeMillis() / 1000).toString(),
                            productSpecialPrice = productDetail.special!!,
                            formattedSpecial = productDetail.formattedSpecial!!,
                            productHasOption = option,
                            wishlist_status = productDetail.wishlistStatus)
            )
            if (AppDataBase.getAppDataBaseInstance(mContext).getDao().getRecentViewedProduct().size == 10) {
                deleteViewedProducts(mContext, AppDataBase.getAppDataBaseInstance(mContext).getDao().getRecentViewedProduct().get(9).id.toString())
            }
        }

        fun getRecentViewedProducts(mContext: Context): List<RecentViewedTable> {
            return AppDataBase.getAppDataBaseInstance(mContext).getDao().getRecentViewedProduct()
        }

        fun setRecentSearchWord(context: Context, recentSearchTable: RecentSearchTable) {
            AppDataBase.getAppDataBaseInstance(context).getRecentSearchDao().insertSearchWord(recentSearchTable)
        }

        fun setAddCartData(context: Context, addCartTable: AddCartTable) {
            AppDataBase.getAppDataBaseInstance(context).getAddToCartDao().insertAddToCart(addCartTable)
        }

        fun getAddCartData(context: Context): List<AddCartTable> {
            return AppDataBase.getAppDataBaseInstance(context).getAddToCartDao().getAddToCart()
        }

        fun getRecentSearchList(context: Context): List<RecentSearchTable> {
            return AppDataBase.getAppDataBaseInstance(context).getRecentSearchDao().getRecentSearch()
        }

        fun deleteViewedProducts(mContext: Context, productId: String): Int {

val l = AppDataBase.getAppDataBaseInstance(mContext).getDao().deleteRecentViewedProduct(productId)
            return l
        }

        fun insertHomeData(mContext: Context, homeDataModel: HomeDataModel) {
            var l = AppDataBase.getAppDataBaseInstance(mContext).getHomeDao().insertHomeProduct(HomeDataTable(0, homeDataModel))
//            System.out.println(" Input index " + l)
        }

        fun getHomeDataModel(mContext: Context): JSONObject? {
            var json: JSONObject?=null
            if (AppDataBase.getAppDataBaseInstance(mContext).getHomeDao().getHomeData() != null) {
                val homeDataModelToString = Gson().toJson(AppDataBase.getAppDataBaseInstance(mContext).getHomeDao().getHomeData(), HomeDataTable::class.java)
                json = JSONObject(homeDataModelToString)
                if (json != null)
                    return json
                else
                    return json
            } else {
                return JSONObject()
            }
        }

        fun upDateHomeData(mContext: Context, homeDataModel: HomeDataModel): Int {
            val homeDataModelToString = Gson().toJson(homeDataModel, HomeDataModel::class.java)
            return AppDataBase.getAppDataBaseInstance(mContext).getHomeDao().upDatehomeData(Gson().fromJson(homeDataModelToString, HomeDataTable::class.java))
        }


        fun deleteAllRecentViewed(mContext: Context) {

            AppDataBase.getAppDataBaseInstance(mContext).getDao().deleteDatafromTable()
        }

        fun deleteAllHomeData(mContext: Context) {

            AppDataBase.getAppDataBaseInstance(mContext).getHomeDao().deleteDatafromTable()
        }

        fun deleteAllResentSearchData(mContext: Context) {

            AppDataBase.getAppDataBaseInstance(mContext).getRecentSearchDao().deleteDatafromResentSearchTable()
        }

        fun deleteAddToCartItem(mContext: Context, productId: String) {
            AppDataBase.getAppDataBaseInstance(mContext).getAddToCartDao().deleteAddToCart(productId)
        }

    }

}




