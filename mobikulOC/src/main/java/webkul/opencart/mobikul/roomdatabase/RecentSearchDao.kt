package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.*

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchWord(recentSearchTable: RecentSearchTable)

    @Query("SELECT * from recentSearch ORDER BY id DESC LIMIT 10")
    fun getRecentSearch(): List<RecentSearchTable>

    @Query(AppDataBaseConstant.DELETE_QUERY)
    fun deleteDatafromResentSearchTable()
}