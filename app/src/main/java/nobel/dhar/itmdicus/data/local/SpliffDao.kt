package nobel.dhar.itmdicus.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SpliffDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(item: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(item: CartTable)


    @Query("SELECT * from product_table WHERE name LIKE '%' || :searchQuery || '%'")
    fun getProducts(searchQuery: String): LiveData<List<Product>>

    @Query("SELECT cart_table.*, product_table.* FROM cart_table INNER JOIN product_table ON cart_table.product_id = product_table.id")
    fun findAllCartProducts(): LiveData<List<CartWithProduct>>

    @Query("DELETE FROM cart_table ")
    suspend fun deleteAllCart()

    @Update
    suspend fun updateCart(item : CartTable)


}