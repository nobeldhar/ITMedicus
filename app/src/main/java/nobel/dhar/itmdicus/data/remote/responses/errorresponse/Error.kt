package nobel.dhar.itmdicus.data.remote.responses.errorresponse


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("c_password")
    var cPassword: List<String>?
)