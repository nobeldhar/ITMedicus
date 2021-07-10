package nobel.dhar.itmdicus.data.repositories


import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import nobel.dhar.itmdicus.data.local.CartTable
import nobel.dhar.itmdicus.data.local.SpliffDao
import nobel.dhar.itmdicus.data.remote.requests.LoginRequest
import nobel.dhar.itmdicus.data.remote.requests.RegistrationRequest
import nobel.dhar.itmdicus.data.remote.sources.SpliffRemoteDataSource
import nobel.dhar.itmdicus.utils.Resource
import nobel.dhar.itmdicus.utils.SharedPrefsHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpliffRepository @Inject constructor(
    private val remoteDataSource: SpliffRemoteDataSource,
    private val prefsHelper: SharedPrefsHelper,
    private val localDataSource : SpliffDao
) {
    fun registerUser(request: RegistrationRequest?)=
        liveData(Dispatchers.IO) {

            emit(Resource.loading())
            val responseStatus = remoteDataSource.registerUser(request)

            if (responseStatus.status == Resource.Status.SUCCESS) {
                emit(responseStatus)

                if (responseStatus.data?.success?.token != null) {
                    prefsHelper.saveUser(responseStatus.data.success!!)
                }

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(responseStatus)
            }
        }

    fun loginUser(request: LoginRequest?) =
        liveData(Dispatchers.IO) {

            emit(Resource.loading())
            val responseStatus = remoteDataSource.loginUser(request)

            if (responseStatus.status == Resource.Status.SUCCESS) {
                emit(responseStatus)

                if (responseStatus.data?.success?.token != null) {
                    prefsHelper.saveUser(responseStatus.data.success!!)
                }

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(responseStatus)
            }
        }

    fun getProducts(query: String) =
        liveData(Dispatchers.IO) {

            emit(Resource.loading())

            val source = localDataSource.getProducts(query).map { Resource.success(it) }
            emitSource(source)

        }

    suspend fun addToCart(cartTable: CartTable)=
        localDataSource.insertCartProduct(cartTable)

    fun getCartItems() =
        localDataSource.findAllCartProducts()

    suspend fun dropCart() =
        localDataSource.deleteAllCart()

    suspend fun updateCartItem(cartTable: CartTable)  =
        localDataSource.updateCart(cartTable)

    fun logoutUser() =
        liveData(Dispatchers.IO) {

            emit(Resource.loading())
            val responseStatus = remoteDataSource.logoutUser()

            if (responseStatus.status == Resource.Status.SUCCESS) {
                emit(responseStatus)

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(responseStatus)
            }

        }

}