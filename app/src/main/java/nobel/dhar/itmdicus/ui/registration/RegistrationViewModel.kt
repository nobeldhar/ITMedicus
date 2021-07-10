package nobel.dhar.itmdicus.ui.registration

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nobel.dhar.itmdicus.data.remote.requests.RegistrationRequest
import nobel.dhar.itmdicus.data.repositories.SpliffRepository
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val spliffRepository: SpliffRepository
) : ViewModel() {

    private val _uiResponse = Channel<UIResponse>()
    val getUIResponse: Flow<UIResponse>
        get() = _uiResponse.receiveAsFlow()

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var cPassword: String? = null

    private val _regInfo = MutableLiveData<RegistrationRequest>()


    var getRegistrationResult = Transformations.switchMap(_regInfo) {
        spliffRepository.registerUser(it)
    }

    fun onRegisterClicked() {

        viewModelScope.launch {
            _uiResponse.send(UIResponse.RegisterClicked)
        }

        when {
            name.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Provide Name"))
                }
            }

            email.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Provide Email"))
                }
            }

            password.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Set Password"))
                }
            }

            cPassword.isNullOrBlank() -> {
                viewModelScope.launch {
                    _uiResponse.send(UIResponse.ValidationError("Confirm Password"))
                }
            }


            else -> {
                val registrationRequest = RegistrationRequest(
                    email = email,
                    name = name,
                    pass = password,
                    c_password = cPassword
                )
                _regInfo.value = registrationRequest
            }
        }

    }

    fun onAlreadyMemberClicked() {
        viewModelScope.launch {
            _uiResponse.send(UIResponse.AlreadyMember)
        }
    }

    sealed class UIResponse {
        object AlreadyMember : UIResponse()
        object RegisterClicked : UIResponse()
        data class ValidationError(val error: String) : UIResponse()
    }
}