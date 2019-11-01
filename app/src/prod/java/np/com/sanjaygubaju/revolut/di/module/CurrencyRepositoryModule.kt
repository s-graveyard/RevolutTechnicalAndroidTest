package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.CurrencyRepository
import np.com.sanjaygubaju.revolut.data.remote.ApiService
import np.com.sanjaygubaju.revolut.data.remote.CurrencyRemoteDataSource
import javax.inject.Named


@Module
class CurrencyRepositoryModule {

    // ---------------------------------------------------------------------------------------------------------------

    @Named("Remote")
    @Provides
    internal fun providesRemoteCurrencyRepository(apiService: ApiService): CurrencyDataSource {
        return CurrencyRemoteDataSource(apiService)
    }

// ---------------------------------------------------------------------------------------------------------------

}