package np.com.sanjaygubaju.revolut.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import np.com.sanjaygubaju.revolut.MainApp
import np.com.sanjaygubaju.revolut.di.builder.ActivityBuilder
import np.com.sanjaygubaju.revolut.di.module.AppModule
import np.com.sanjaygubaju.revolut.di.module.DataModule
import np.com.sanjaygubaju.revolut.di.module.RxModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DataModule::class,
        RxModule::class,
        ActivityBuilder::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApp)
}