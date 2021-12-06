package webkul.opencart.mobikul.roomdatabase

import android.arch.persistence.room.*
import android.content.Context
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration
import webkul.opencart.mobikul.cartdataBase.AddCartTable
import webkul.opencart.mobikul.cartdataBase.AddToCartDao
import webkul.opencart.mobikul.DataConvertor
import webkul.opencart.mobikul.roomtable.HomeDataTable


@Database(entities = [
    (RecentViewedTable::class),
    (HomeDataTable::class),
    (RecentSearchTable::class),
    (AddCartTable::class)], version = 11, exportSchema = false)
@TypeConverters(DataConvertor::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): RoomDao
    abstract fun getHomeDao(): HomeDao
    abstract fun getRecentSearchDao(): RecentSearchDao
    abstract fun getAddToCartDao(): AddToCartDao

    companion object {
        private var mINSTANCE: AppDataBase? = null
        fun getAppDataBaseInstance(mContext: Context): AppDataBase {
            if (mINSTANCE == null) {
                synchronized(AppDataBase::class) {
                    mINSTANCE = Room.databaseBuilder(
                            mContext.applicationContext,
                            AppDataBase::class.java,
                            AppDataBaseConstant.ROOM_DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addMigrations(AppDataBase.MIGRATION_1_2)
                            .build()
                }
            }
            return mINSTANCE!!
        }

        fun destroyDatabase() {
            if (mINSTANCE?.isOpen == true) {
                mINSTANCE?.close()
            }
            mINSTANCE = null
        }

        @JvmField
        val MIGRATION_1_2: Migration = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE " + AppDataBaseConstant.ROOM_DATABASE_RECENTVIEW_TABLE + " ADD formattedSpecial TEXT")
            }
        }
    }

}