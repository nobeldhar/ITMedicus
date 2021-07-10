package nobel.dhar.itmdicus

import android.app.Application
import androidx.work.Configuration
import nobel.dhar.itmdicus.di.component.DaggerAppComponent
import nobel.dhar.itmdicus.worker.MyWorkerFactory
import nobel.dhar.itmdicus.worker.TokenRefreshWorker

class SpliffApp : Application() , Configuration.Provider{
    val appComponent = DaggerAppComponent.builder().application(this).build()


    override fun getWorkManagerConfiguration(): Configuration {
        val factory: MyWorkerFactory = appComponent.factory()
        return Configuration.Builder().setWorkerFactory(factory).build()
    }
}