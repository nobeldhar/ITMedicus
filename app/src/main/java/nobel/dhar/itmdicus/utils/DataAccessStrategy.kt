package nobel.dhar.itmdicus.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

private const val TAG = "DataAccessStrategy"
fun <T, A> performGetOperation(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)
            Log.d(TAG, "performGetOperation: emitted count 1 ")
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
                val source1 = databaseQuery.invoke().map { Resource.success(it) }
                emitSource(source1)
                Log.d(TAG, "performGetOperation: emitted count 2 ")
            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!, isNetworkError = responseStatus.isNetworkError))
                emitSource(source)
            }
        }



fun <T> performAuthOperation(
    networkCall: suspend () -> Resource<T>
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus  = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(responseStatus)
        }
    }