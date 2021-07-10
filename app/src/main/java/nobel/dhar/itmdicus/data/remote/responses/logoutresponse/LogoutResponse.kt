package nobel.dhar.itmdicus.data.remote.responses.logoutresponse


import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("success")
    var success: String?
)