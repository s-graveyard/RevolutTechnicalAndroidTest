package np.com.sanjaygubaju.revolut

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import np.com.sanjaygubaju.revolut.di.component.DaggerAppComponent
import javax.inject.Inject

class MainApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        // Initialize dagger
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

}