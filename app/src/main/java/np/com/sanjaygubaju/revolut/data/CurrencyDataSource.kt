package np.com.sanjaygubaju.revolut.data

import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates

interface CurrencyDataSource {
    fun getCurrencyRate(baseCountryCode: String): Single<CurrencyRates>
}