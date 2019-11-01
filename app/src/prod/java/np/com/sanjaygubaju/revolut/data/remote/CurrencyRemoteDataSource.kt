package np.com.sanjaygubaju.revolut.data.remote

import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates

class CurrencyRemoteDataSource(private val apiService: ApiService) : CurrencyDataSource {

    override fun getCurrencyRate(baseCountryCode: String): Single<CurrencyRates> {
        return apiService.getCurrencyRates(baseCountryCode)
    }
}