package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.jetbrains.annotations.NotNull

@Dao
interface RoomDao {

    @Query(AppDataBaseConstant.RECENT_VIEWED_QUERY)
    fun getRecentViewedProduct(): List<RecentViewedTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentViewedProduct(recentViewedTable: RecentViewedTable): Long

    @Query(AppDataBaseConstant.RECENT_VIEWED_DELETE_QUERY)
    fun deleteRecentViewedProduct(@NotNull productIdVal: String) : Int

    @Query(AppDataBaseConstant.DELETE_QUERY)
    fun deleteDatafromTable()
}