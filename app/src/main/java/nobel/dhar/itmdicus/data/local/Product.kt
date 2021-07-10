package nobel.dhar.itmdicus.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_table")
data class Product(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int?,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "company")
        val company: String,
        @ColumnInfo(name = "price")
        val price: Int
)