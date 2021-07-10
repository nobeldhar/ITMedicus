package nobel.dhar.itmdicus.ui.cart

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.local.CartWithProduct
import nobel.dhar.itmdicus.ui.addtocart.AddToCartDialogViewModel

interface CartAdapterLister {

    fun onAddClicked(cartWithProduct: CartWithProduct)

    fun onMinusClicked(cartWithProduct: CartWithProduct)
}