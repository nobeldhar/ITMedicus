package nobel.dhar.itmdicus.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.local.CartTable
import nobel.dhar.itmdicus.data.repositories.SpliffRepository
import nobel.dhar.itmdicus.ui.login.LoginViewModel
import javax.inject.Inject

class CartViewModel
@Inject constructor(
    private val spliffRepository: SpliffRepository
): ViewModel() {
    private val _uiResponse = Channel<UIResponse>()
    val getUIResponse: Flow<UIResponse>
        get() = _uiResponse.receiveAsFlow()

    var cartEmpty: Boolean = true

    fun getCartItems() = spliffRepository.getCartItems()

    fun onPlaceOrderCalled (){
        if (!cartEmpty){
            viewModelScope.launch (Dispatchers.IO){
                spliffRepository.dropCart()
            }
            viewModelScope.launch {
                _uiResponse.send(UIResponse.OnOrderPlaced)
            }
        }else
            viewModelScope.launch {
                _uiResponse.send(UIResponse.CartEmpty)
            }
    }

    fun updateCartItem(cartTable: CartTable) {
        viewModelScope.launch (Dispatchers.IO){
            spliffRepository.updateCartItem(cartTable)
        }
    }

    sealed class UIResponse {
        object OnOrderPlaced : UIResponse()
        object CartEmpty : UIResponse()
    }
}