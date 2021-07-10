package nobel.dhar.itmdicus.ui.addtocart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.local.CartTable
import nobel.dhar.itmdicus.data.local.Product
import nobel.dhar.itmdicus.data.repositories.SpliffRepository
import nobel.dhar.itmdicus.ui.login.LoginViewModel
import javax.inject.Inject

class AddToCartDialogViewModel
@Inject constructor(
    private val spliffRepository: SpliffRepository
) : ViewModel() {

    private val _uiResponse = Channel<UIResponse>()
    val getUIResponse: Flow<UIResponse>
        get() = _uiResponse.receiveAsFlow()

    private var _count = 1
    val count: String
        get() = if (_count < 10) "0$_count" else _count.toString()

    var product: Product? = null

    fun onAddClicked() {
        _count++
        viewModelScope.launch {
            _uiResponse.send(UIResponse.OnAddClicked(count))
        }
    }

    fun onMinusClicked() {
        if (_count > 1) {
            _count--
            viewModelScope.launch {
                _uiResponse.send(UIResponse.OnMinusClicked(count))
            }
        }
    }

    fun onAddToCardClicked() {

        product?.let {
            val cartTable =
                it.id?.let { it1 -> CartTable(cart_id = null, product_id = it1, count = _count, total_price = _count*it.price) }

            viewModelScope.launch(Dispatchers.IO){
                cartTable?.let { it1 -> spliffRepository.addToCart(it1) }
            }

            viewModelScope.launch {
                _uiResponse.send(UIResponse.CartUpdated)
            }
        }

    }

    sealed class UIResponse {
        data class OnAddClicked(val count: String) : UIResponse()
        data class OnMinusClicked(val count: String) : UIResponse()
        object CartUpdated : UIResponse()
    }

}