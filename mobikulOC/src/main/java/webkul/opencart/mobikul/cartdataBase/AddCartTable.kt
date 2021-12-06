package webkul.opencart.mobikul.cartdataBase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import webkul.opencart.mobikul.roomdatabase.AppDataBaseConstant

@Entity(tableName = AppDataBaseConstant.ROOM_DATABASE_ADD_CART_TABLE, indices = [(Index(value = [(AppDataBaseConstant.PRODUCT_ID)], unique = true))])
data class AddCartTable(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = AppDataBaseConstant.COUNT_ID) val id: Long,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_ID) val productId: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_QUANTITY) val qty: String,
        @ColumnInfo(name = AppDataBaseConstant.PRODUCT_JSON_DATA) val jsonObject: String)