package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.BuildConfig
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.CurrencyRepository
import np.com.sanjaygubaju.revolut.data.remote.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class DataModule {

    companion object {
        const val NAME_COMBINED = "_combined"
        const val NAME_REMOTE = "_remote"
    }

    // ---------------------------------------------------------------------------------------------------------------

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        // Connection timeout
        okHttpBuilder.connectTimeout(1, TimeUnit.MINUTES)

        // Socket timeout
        okHttpBuilder.readTimeout(1, TimeUnit.MINUTES)
        okHttpBuilder.writeTimeout(1, TimeUnit.MINUTES)

        return okHttpBuilder.build()
    }

    @Provides
    fun providesApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    // ---------------------------------------------------------------------------------------------------------------

    @Named(NAME_COMBINED)
    @Provides
    internal fun providesCurrencyRepository(@Named(NAME_REMOTE) remoteSource: CurrencyDataSource): CurrencyDataSource {
        return CurrencyRepository(remoteSource)
    }

    // ---------------------------------------------------------------------------------------------------------------

}