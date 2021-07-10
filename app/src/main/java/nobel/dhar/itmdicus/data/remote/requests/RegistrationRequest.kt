package nobel.dhar.itmdicus.data.remote.requests

import com.google.gson.annotations.SerializedName



data class RegistrationRequest (
        @SerializedName("email") val email : String? = null,
        @SerializedName("password") val pass : String? = null,
        @SerializedName("name") val name : String? = null,
        @SerializedName("c_password") val c_password : String? = null

)

data class LoginRequest (
        @SerializedName("email") val email : String? = null,
        @SerializedName("password") val pass : String? = null,

)