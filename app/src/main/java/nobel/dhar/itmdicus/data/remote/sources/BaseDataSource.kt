package nobel.dhar.itmdicus.data.remote.sources

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import nobel.dhar.itmdicus.data.remote.responses.errorresponse.ErrorResponse
import nobel.dhar.itmdicus.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.lang.IllegalStateException
import java.net.UnknownHostException

abstract class BaseDataSource {

    private var errorKey: String? = null
    private var errorValue: String? = null

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                Log.d(Companion.TAG, "getResult: isSuccessful true")
                val body = response.body()
                if (body != null){
                    Log.d(TAG, "getResult: body not null")
                    return Resource.success(body)
                }
            }
            Log.d(TAG, "getResult: outside")

            try {
                val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                errorValue = errorResponse.error?.values?.toTypedArray()?.get(0)?.get(0)
                errorKey = errorResponse.error?.keys?.toTypedArray()?.get(0)

            }catch (e: Exception){
                errorKey = null
                errorValue = null
                Log.d(TAG, "getResult: exception $e")
            }

            return when{
                !errorKey.isNullOrBlank() && !errorValue.isNullOrBlank()->
                    Resource.error("$errorKey : $errorValue", data = response.body())
                response.code() == 401 ->
                    Resource.error("Unauthorized", data = response.body())
                else ->
                    Resource.error("Internal Server Error", data = response.body())
            }

        } catch (throwable: Throwable) {
            Log.d(TAG, "getResult: inside catch ${throwable.toString()}")

            return when(throwable){
                is HttpException ->{
                    Resource.error(throwable.message ?: throwable.toString(), false, throwable.code(), throwable.response()?.errorBody())
                }
                is UnknownHostException ->{
                    Resource.error(throwable.message ?: throwable.toString(), true)
                }

                else ->{
                    Log.d(TAG, "getResult: inside else catch ${throwable.toString()}")
                    Resource.error(throwable.message ?: throwable.toString(), true)
                }
            }
        }
    }

    companion object {
        private const val TAG = "BaseDataSource"
    }

}