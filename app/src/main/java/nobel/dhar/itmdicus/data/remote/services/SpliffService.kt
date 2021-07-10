package nobel.dhar.itmdicus.data.remote.services

import nobel.dhar.itmdicus.data.remote.requests.LoginRequest
import nobel.dhar.itmdicus.data.remote.requests.RegistrationRequest
import nobel.dhar.itmdicus.data.remote.responses.logoutresponse.LogoutResponse
import nobel.dhar.itmdicus.data.remote.responses.registerresponse.RegisterResponse
import retrofit2.Response

import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.POST


interface SpliffService {

    @POST("register")
    suspend fun registerUser(@Body request: RegistrationRequest?)
            : Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest?)
            : Response<RegisterResponse>

    @POST("logout")
    suspend fun logoutUser(): Response<LogoutResponse>

    @POST("refresh")
    suspend fun refreshToken() : Response<RegisterResponse>


    /*@POST("user/sign-in")
    suspend fun signInUser(
        @Body authRequest: AuthRequest
    ): Response<SignInResponse>

    @GET("auth/logout")
    suspend fun logoutUser(): Response<BaseResponse>

    @POST("user/sign-up")
    suspend fun signUpUser(
        @Body authRequest: SignUpRequest
    ): Response<SignUpResponse>*/


}