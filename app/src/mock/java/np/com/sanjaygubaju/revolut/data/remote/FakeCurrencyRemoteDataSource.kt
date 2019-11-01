package np.com.sanjaygubaju.revolut.data.remote

import com.google.gson.Gson
import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates

class FakeCurrencyRemoteDataSource() : CurrencyDataSource {

    val rawResponse =
        "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.6119,\"BGN\":1.9504,\"BRL\":4.7785,\"CAD\":1.5295,\"CHF\":1.1244,\"CNY\":7.923,\"CZK\":25.643,\"DKK\":7.436,\"GBP\":0.89574,\"HKD\":9.107,\"HRK\":7.4134,\"HUF\":325.58,\"IDR\":17275.0,\"ILS\":4.159,\"INR\":83.485,\"ISK\":127.44,\"JPY\":129.19,\"KRW\":1301.1,\"MXN\":22.303,\"MYR\":4.7986,\"NOK\":9.7488,\"NZD\":1.7584,\"PHP\":62.418,\"PLN\":4.3063,\"RON\":4.6256,\"RUB\":79.353,\"SEK\":10.561,\"SGD\":1.5955,\"THB\":38.024,\"TRY\":7.607,\"USD\":1.1602,\"ZAR\":17.774}}"

    override fun getCurrencyRate(baseCountryCode: String): Single<CurrencyRates> {
        val apiResponse = Gson().fromJson(rawResponse, CurrencyRates::class.java)
        return Single.just(apiResponse)
    }
}