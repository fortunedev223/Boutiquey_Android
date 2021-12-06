package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.*
import webkul.opencart.mobikul.roomtable.HomeDataTable

@Dao
interface HomeDao {
    @Query(AppDataBaseConstant.HOME_DATA_QUERY)
    fun getHomeData(): HomeDataTable?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHomeProduct(homeDataTable: HomeDataTable): Long

    @Update
    fun upDatehomeData(homeDataTable: HomeDataTable): Int

    @Query(AppDataBaseConstant.DELETE_QUERY_HOMEMODEL)
    fun deleteDatafromTable()

}