package nobel.dhar.itmdicus.data.remote.responses.registerresponse


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("success")
    var success: Success?,
    @SerializedName("error")
    var error: String?
)