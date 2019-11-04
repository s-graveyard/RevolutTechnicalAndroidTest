package np.com.sanjaygubaju.revolut.data

import np.com.sanjaygubaju.revolut.data.remote.FakeCurrencyRemoteDataSource
import np.com.sanjaygubaju.revolut.utils.scheduler.SchedulerProvider
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class CurrencyRepositoryTest {

    @InjectMocks
    private lateinit var currencyRemoteDataSource: FakeCurrencyRemoteDataSource

    @InjectMocks
    private lateinit var scheduler: SchedulerProvider

    private lateinit var currencyRepository: CurrencyDataSource

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)

        // Initialize repository
        currencyRepository = CurrencyRepository(currencyRemoteDataSource)
    }

    @Test
    fun getCurrency_requestsCurrencyFromRemoteDataSource() {

        val countryCode = "EUR";

        val testObserver = currencyRepository.getCurrencyRate(countryCode)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .test()

        testObserver.assertNoErrors()
            .dispose()

        val response = testObserver.values()[0]
        assertThat(response.base, `is`(countryCode))
    }


}