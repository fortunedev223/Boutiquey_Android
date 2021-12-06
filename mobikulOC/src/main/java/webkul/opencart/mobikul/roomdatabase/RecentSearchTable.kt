package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = AppDataBaseConstant.ROOM_DATABASE_RECENT_SEARCH_TABLE, indices = [(Index(value = [(AppDataBaseConstant.WORD)], unique = true))])
class RecentSearchTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDataBaseConstant.ID)
    var id: Long = 0

    @ColumnInfo(name = AppDataBaseConstant.WORD)
    var word: String = ""
}