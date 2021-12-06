package webkul.opencart.mobikul.cartdataBase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.jetbrains.annotations.NotNull
import webkul.opencart.mobikul.roomdatabase.AppDataBaseConstant

@Dao
interface AddToCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddToCart(addCartTable: AddCartTable)

    @Query("SELECT * from addCart ORDER BY id DESC LIMIT 10")
    fun getAddToCart(): List<AddCartTable>

    @Query(AppDataBaseConstant.ADD_TO_CART_DELETE_QUERY)
    fun deleteAddToCart(@NotNull productID: String) : Int
}