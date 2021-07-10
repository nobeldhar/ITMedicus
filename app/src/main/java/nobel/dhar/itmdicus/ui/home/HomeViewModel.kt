package nobel.dhar.itmdicus.ui.home

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.repositories.SpliffRepository
import javax.inject.Inject

class HomeViewModel@Inject constructor(
    private val spliffRepository: SpliffRepository,

) :  ViewModel() {

    private val _searchQuery = MutableLiveData("")

    val productsResult = Transformations.switchMap(_searchQuery) {
        spliffRepository.getProducts(it)
    }


    fun onSearchQueryChanged(newText: String?) {

        newText?.let {
            _searchQuery.value = it
        }
    }

    fun logoutUser() = spliffRepository.logoutUser()

    fun dropCartTable() = viewModelScope.launch (Dispatchers.IO){
        spliffRepository.dropCart()
    }


}