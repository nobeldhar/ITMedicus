package nobel.dhar.itmdicus.data.remote.responses.registerresponse


import com.google.gson.annotations.SerializedName

data class Success(
    @SerializedName("name")
    var name: String?,
    @SerializedName("token")
    var token: String?,
    @SerializedName("expires_at")
    var expires_at: ExpiresAt?,
)