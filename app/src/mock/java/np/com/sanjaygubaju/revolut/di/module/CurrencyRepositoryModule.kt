package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.remote.FakeCurrencyRemoteDataSource
import javax.inject.Named


@Module
class CurrencyRepositoryModule {

    // ---------------------------------------------------------------------------------------------------------------

    @Named("Remote")
    @Provides
    internal fun providesRemoteCurrencyRepository(): CurrencyDataSource {
        return FakeCurrencyRemoteDataSource()
    }

// ---------------------------------------------------------------------------------------------------------------

}