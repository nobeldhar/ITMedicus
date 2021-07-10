package nobel.dhar.itmdicus.di.module

import androidx.work.ListenableWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import nobel.dhar.itmdicus.worker.ChildWorkerFactory
import nobel.dhar.itmdicus.worker.TokenRefreshWorker
import kotlin.reflect.KClass


@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(TokenRefreshWorker::class)
    fun bindHelloWorldWorker(factory: TokenRefreshWorker.Factory): ChildWorkerFactory
}