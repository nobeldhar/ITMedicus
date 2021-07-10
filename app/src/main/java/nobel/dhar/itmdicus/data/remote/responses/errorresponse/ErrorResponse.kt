package nobel.dhar.itmdicus.data.remote.responses.errorresponse


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    var error: Map<String, List<String>>?
)