package webkul.opencart.mobikul.roomtable

import android.arch.persistence.room.*
import webkul.opencart.mobikul.DataConvertor
import webkul.opencart.mobikul.model.GetHomePage.*
import webkul.opencart.mobikul.roomdatabase.AppDataBaseConstant

@Entity(tableName = AppDataBaseConstant.ROOM_DATABASE_HOMEDATA_TABLE/*,indices = arrayOf(Index(value = AppDataBaseConstant.HOME_MODEL_DATA, unique = true))*/)
data class HomeDataTable(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = AppDataBaseConstant.COUNT_ID) val id: Long,
        @ColumnInfo(name = "homeData") @TypeConverters(DataConvertor::class) val homeDataModel: HomeDataModel
)