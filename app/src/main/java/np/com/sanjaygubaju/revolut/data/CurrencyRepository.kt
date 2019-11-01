package np.com.sanjaygubaju.revolut.data

import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates


class CurrencyRepository(
    private val currencyRemoteSource: CurrencyDataSource
) : CurrencyDataSource {

    override fun getCurrencyRate(baseCountryCode: String): Single<CurrencyRates> {
        return currencyRemoteSource.getCurrencyRate(baseCountryCode)
    }
}