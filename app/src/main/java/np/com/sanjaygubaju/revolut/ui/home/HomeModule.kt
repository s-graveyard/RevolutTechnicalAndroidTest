package np.com.sanjaygubaju.revolut.ui.home

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.di.module.DataModule.Companion.NAME_COMBINED
import np.com.sanjaygubaju.revolut.utils.scheduler.BaseSchedulerProvider
import javax.inject.Named

@Module
class HomeModule {

    @Provides
    fun providesHomeViewModel(
        @Named(NAME_COMBINED) currencyDataSource: CurrencyDataSource,
        schedulerProvider: BaseSchedulerProvider
    ): HomeViewModel {
        return HomeViewModel(currencyDataSource, schedulerProvider)
    }
}