package np.com.sanjaygubaju.revolut.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import np.com.sanjaygubaju.revolut.ui.home.HomeActivity
import np.com.sanjaygubaju.revolut.ui.home.HomeModule

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [HomeModule::class]
    )
    abstract fun bindHomeActivity(): HomeActivity
}