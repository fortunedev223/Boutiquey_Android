package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = AppDataBaseConstant.ROOM_DATABASE_RECENTVIEW_TABLE, indices = arrayOf(Index(value = AppDataBaseConstant.PRODUCT_ID, unique = true)))
data class RecentViewedTable(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = AppDataBaseConstant.COUNT_ID) val id: Long,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_ID) val productId: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_NAME) val prouductName: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_IMAGE) val productImage: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_PRICE) val productPrice: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_SPECIAL_PRICE) val productSpecialPrice: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_FORMATTED_PRICE) val formattedSpecial: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_HAS_OPTION) val productHasOption: Boolean,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_WISH_STATUS) val wishlist_status: Boolean?,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_TIME_STAMP) val productTimeStamp: String)

