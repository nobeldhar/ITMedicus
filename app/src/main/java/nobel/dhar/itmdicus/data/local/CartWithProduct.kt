package nobel.dhar.itmdicus.data.local

import androidx.room.Embedded

data class CartWithProduct(
    @Embedded
    val cartTable: CartTable,
    @Embedded
    val product: Product
)