package np.com.sanjaygubaju.revolut.data.remote

import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Get latest currency rates
     * @param countryCode is the code for respective base country.
     */
    @GET("latest")
    fun getCurrencyRates(
        @Query("base") countryCode: String
    ): Single<CurrencyRates>

}