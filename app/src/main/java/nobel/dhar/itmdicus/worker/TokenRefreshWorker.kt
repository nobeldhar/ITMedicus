package nobel.dhar.itmdicus.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nobel.dhar.itmdicus.data.remote.sources.SpliffRemoteDataSource
import nobel.dhar.itmdicus.utils.Resource
import nobel.dhar.itmdicus.utils.SharedPrefsHelper
import javax.inject.Inject
import javax.inject.Provider


class TokenRefreshWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val remoteDataSource: SpliffRemoteDataSource,
    private val prefsHelper: SharedPrefsHelper
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {

            val responseStatus = remoteDataSource.refreshToken()

            if (responseStatus.status == Resource.Status.SUCCESS) {
                if (responseStatus.data?.success?.token != null) {
                    prefsHelper.saveUser(responseStatus.data.success!!)
                    Log.d(TAG, "doWork: inside")
                }
            }

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    class Factory @Inject constructor(
        private val spliffRemoteDataSource: SpliffRemoteDataSource,
        private val prefsHelper: SharedPrefsHelper
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return TokenRefreshWorker(
                appContext,
                params,
                spliffRemoteDataSource,
                prefsHelper
            )
        }
    }

    companion object {
        private const val TAG = "TokenRefreshWorker"
    }

}