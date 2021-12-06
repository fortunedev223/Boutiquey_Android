package webkul.opencart.mobikul.roomdatabase

object AppDataBaseConstant {
    /* Constants for recentViewed Table*/
    const val ROOM_DATABASE_ADD_CART_TABLE = "addCart"
    const val ROOM_DATABASE_RECENTVIEW_TABLE = "recentViewed"
    const val ROOM_HOME_DATA_MODEL = "homedatamodel"
    const val ROOM_DATABASE_NAME = "roomDataBase"
    const val RECENT_VIEWED_QUERY = "Select * FROM " + AppDataBaseConstant.ROOM_DATABASE_RECENTVIEW_TABLE + " ORDER BY time_stamp DESC LIMIT 10 "
    const val ADD_TO_CART_DELETE_QUERY = "DELETE FROM " + AppDataBaseConstant.ROOM_DATABASE_ADD_CART_TABLE + " WHERE id == :productID"
    const val RECENT_VIEWED_DELETE_QUERY = "DELETE FROM " + AppDataBaseConstant.ROOM_DATABASE_RECENTVIEW_TABLE + " WHERE count_id < :productIdVal"
    const val HOME_DATA_MODEL_QUERY = "Select * FROM " + AppDataBaseConstant.ROOM_DATABASE_NAME
    const val PRODUCT_ID = "id"
    const val COUNT_ID = "count_id"
    const val PRODUCT_NAME = "name"
    const val PRODUCT_IMAGE = "image"
    const val PRODUCT_PRICE = "price"
    const val PRODUCT_QUANTITY = "qty"
    const val PRODUCT_JSON_DATA = "json"
    const val PRODUCT_SPECIAL_PRICE = "specialprice"
    const val PRODUCT_HAS_OPTION = "hasoption"
    const val PRODUCT_WISH_STATUS = "wishlist_status"
    const val PRODUCT_TIME_STAMP = "time_stamp"
    const val DELETE_QUERY = "DELETE FROM recentViewed"
    const val DELETE_RECENT_SEARCH = "DELETE FROM recentSearch"
    const val DELETE_QUERY_HOMEMODEL = "DELETE FROM homeData"
    const val PRODUCT_FORMATTED_PRICE = "formattedSpecial"
    /* Constants for homeData Table*/
    const val ROOM_DATABASE_HOMEDATA_TABLE = "homeData"
    const val HOME_DATA_QUERY = "Select * FROM " + AppDataBaseConstant.ROOM_DATABASE_HOMEDATA_TABLE + " ORDER BY count_id DESC"
    /*Constants for recent searched table*/
    const val ROOM_DATABASE_RECENT_SEARCH_TABLE = "recentSearch"
    const val ID = "id"
    const val WORD = "word"

}