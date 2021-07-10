package nobel.dhar.itmdicus.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.remote.requests.LoginRequest
import nobel.dhar.itmdicus.data.remote.requests.RegistrationRequest
import nobel.dhar.itmdicus.data.repositories.SpliffRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val spliffRepository: SpliffRepository
) : ViewModel() {

    private val _uiResponse = Channel<UIResponse>()
    val getUIResponse: Flow<UIResponse>
        get() = _uiResponse.receiveAsFlow()

    var email: String? = null
    var password: String? = null

    private val _loginInfo = MutableLiveData<LoginRequest>()


    var getLoginResult = Transformations.switchMap(_loginInfo) {
        spliffRepository.loginUser(it)
    }

    fun onLoginClicked() {

        viewModelScope.launch {
            _uiResponse.send(UIResponse.LoginClicked)
        }

        when {
            email.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Provide Email"))
                }
            }

            password.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Provide Password"))
                }
            }

            else -> {
                val loginRequest = LoginRequest(
                    email = email,
                    pass = password,
                )
                _loginInfo.value = loginRequest
            }
        }

    }

    sealed class UIResponse {
        object LoginClicked : UIResponse()
        data class ValidationError(val error: String) : UIResponse()
    }
}