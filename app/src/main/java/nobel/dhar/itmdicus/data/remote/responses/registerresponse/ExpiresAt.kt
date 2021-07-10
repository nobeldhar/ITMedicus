package nobel.dhar.itmdicus.data.remote.responses.registerresponse


import com.google.gson.annotations.SerializedName

data class ExpiresAt(
    @SerializedName("date")
    var date: String?,
    @SerializedName("timezone")
    var timezone: String?,
    @SerializedName("timezone_type")
    var timezoneType: Int?
)