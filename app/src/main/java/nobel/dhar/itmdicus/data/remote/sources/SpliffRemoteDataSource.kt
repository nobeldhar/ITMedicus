package nobel.dhar.itmdicus.data.remote.sources


import nobel.dhar.itmdicus.data.remote.requests.LoginRequest
import nobel.dhar.itmdicus.data.remote.requests.RegistrationRequest
import nobel.dhar.itmdicus.data.remote.services.SpliffService
import nobel.dhar.itmdicus.data.remote.sources.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpliffRemoteDataSource @Inject constructor(
    private val spliffService: SpliffService
) : BaseDataSource() {
    suspend fun registerUser(request: RegistrationRequest?)  =
            getResult { spliffService.registerUser(request) }

    suspend fun loginUser(request: LoginRequest?) =
        getResult { spliffService.loginUser(request) }

    suspend fun logoutUser() =
        getResult { spliffService.logoutUser() }

    suspend fun refreshToken() =
        getResult { spliffService.refreshToken() }

}