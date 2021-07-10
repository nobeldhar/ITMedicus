package nobel.dhar.itmdicus.data.remote

import nobel.dhar.itmdicus.utils.SharedPrefsHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class
RequestInterceptor @Inject constructor(
    private val preferencesHelper: SharedPrefsHelper,
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request.Builder = chain.request().newBuilder()

        newRequest.header("Accept", "application/json")
        preferencesHelper.fetchAuthToken()?.let {
            newRequest.header("Authorization", "Bearer $it")
        }

        return chain.proceed(newRequest.build())

    }
}

