package nobel.dhar.itmdicus.ui.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class JoinViewModel @Inject constructor()
    : ViewModel() {

    private val _uiResponse = Channel<UIResponse>()
    val getUIResponse: Flow<UIResponse>
        get() = _uiResponse.receiveAsFlow()

    fun onJoinClicked(){
        viewModelScope.launch {
            _uiResponse.send(UIResponse.Join)
        }
    }

    fun onLoginClicked(){
        viewModelScope.launch {
            _uiResponse.send(UIResponse.Login)
        }
    }

    sealed class UIResponse {
        object Join : UIResponse()
        object Login : UIResponse()
    }


}