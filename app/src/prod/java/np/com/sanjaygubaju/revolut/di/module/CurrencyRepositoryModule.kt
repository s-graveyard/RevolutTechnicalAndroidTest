package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.remote.ApiService
import np.com.sanjaygubaju.revolut.data.remote.CurrencyRemoteDataSource
import np.com.sanjaygubaju.revolut.di.module.DataModule.Companion.NAME_REMOTE
import javax.inject.Named


@Module
class CurrencyRepositoryModule {

    // ---------------------------------------------------------------------------------------------------------------

    @Named(NAME_REMOTE)
    @Provides
    internal fun providesRemoteCurrencyRepository(apiService: ApiService): CurrencyDataSource {
        return CurrencyRemoteDataSource(apiService)
    }

    // ---------------------------------------------------------------------------------------------------------------

}