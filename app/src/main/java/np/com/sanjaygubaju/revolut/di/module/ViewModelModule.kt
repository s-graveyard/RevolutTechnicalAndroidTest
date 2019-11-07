package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.ui.home.HomeViewModel
import np.com.sanjaygubaju.revolut.utils.scheduler.BaseSchedulerProvider
import javax.inject.Named
import javax.inject.Singleton

/**
 * Creating a view model in application root because,
 * view model gets recreated every time an activity is rotated.
 * Also, sub module doesnot support scoped binding @Singleton
 **/

@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun providesHomeViewModel(
        @Named(DataModule.NAME_COMBINED) currencyDataSource: CurrencyDataSource,
        schedulerProvider: BaseSchedulerProvider
    ): HomeViewModel {
        return HomeViewModel(currencyDataSource, schedulerProvider)
    }

}