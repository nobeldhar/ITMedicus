package nobel.dhar.itmdicus.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    val cart_id: Int?,
    @ColumnInfo(name = "product_id")
    val product_id: Int,
    @ColumnInfo(name = "count")
    val count: Int,
    @ColumnInfo(name = "total_price")
    val total_price: Int
){
    val countToString: String
        get() = if (count < 10) "0$count" else count.toString()
}