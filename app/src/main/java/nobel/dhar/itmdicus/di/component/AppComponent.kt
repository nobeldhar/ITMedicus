package nobel.dhar.itmdicus.di.component

import android.app.Application


import dagger.BindsInstance
import dagger.Component
import nobel.dhar.itmdicus.di.module.DatabaseModule
import nobel.dhar.itmdicus.di.module.NetworkModule
import nobel.dhar.itmdicus.di.module.WorkerBindingModule
import nobel.dhar.itmdicus.ui.addtocart.AddToCartDialog
import nobel.dhar.itmdicus.ui.cart.CartFragment
import nobel.dhar.itmdicus.ui.home.HomeFragment
import nobel.dhar.itmdicus.ui.join.JoinFragment
import nobel.dhar.itmdicus.ui.login.LoginFragment
import nobel.dhar.itmdicus.ui.registration.RegistrationFragment
import nobel.dhar.itmdicus.ui.splash.SplashFragment
import nobel.dhar.itmdicus.worker.MyWorkerFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        WorkerBindingModule::class
    ]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun factory(): MyWorkerFactory

    fun inject(fragment: HomeFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: JoinFragment)
    fun inject(fragment: SplashFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: AddToCartDialog)
    fun inject(fragment: CartFragment)


}