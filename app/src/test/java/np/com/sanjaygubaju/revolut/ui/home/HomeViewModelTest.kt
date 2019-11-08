package np.com.sanjaygubaju.revolut.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import io.reactivex.Single
import np.com.sanjaygubaju.revolut.data.CurrencyRepository
import np.com.sanjaygubaju.revolut.data.models.CurrencyRates
import np.com.sanjaygubaju.revolut.data.remote.FakeCurrencyRemoteDataSource
import np.com.sanjaygubaju.revolut.utils.scheduler.SchedulerProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteSource: FakeCurrencyRemoteDataSource

    @InjectMocks
    private lateinit var scheduler: SchedulerProvider

    lateinit var currencyRepository: CurrencyRepository
    lateinit var viewModel: HomeViewModel

    @Before
    fun setupRepository() {
        MockitoAnnotations.initMocks(this)

        // Initialize view model
        currencyRepository = CurrencyRepository(remoteSource)
        viewModel = HomeViewModel(currencyRepository, scheduler)
    }

    @Test
    fun show_loading_when_repository_is_called() {

        val countryCode = "EUR"

        `when`(remoteSource.getCurrencyRate(countryCode)).thenReturn(Single.never())

        viewModel.loadCurrency()

        // Observe loading status
        assertNotNull(viewModel.loading.value)
        assertTrue(viewModel.loading.value!!)
    }

    @Test
    fun error_when_repository_returns_error() {
        val countryCode = "EUR"
        val msg = "Error"

        `when`(remoteSource.getCurrencyRate(countryCode)).thenReturn(Single.error(Throwable(msg)))

        viewModel.loadCurrency()

        // Observe error status
        assertNotNull(viewModel.error.value)
        assertTrue(viewModel.error.value!!)

        // Observe message value
        assertNotNull(viewModel.message.value)
        assertEquals(viewModel.message.value, msg)
    }

    @Test
    fun should_move_item_to_top() {

        val countryCode = "EUR"
        val positionToMove = 1

        `when`(remoteSource.getCurrencyRate(countryCode)).thenReturn(
            Single.just(
                Gson().fromJson(FakeCurrencyRemoteDataSource.rawResponse, CurrencyRates::class.java)
            )
        )

        viewModel.loadCurrency()

        val currencyList = viewModel.currencyList.value
        assertFalse(viewModel.currencyList.value.isNullOrEmpty())
        val moveCurrency = currencyList!![positionToMove]

        // Move item
        viewModel.moveItemToTop(positionToMove)
        val newCurrencyList = viewModel.currencyList.value!![0]
        assertNotNull(viewModel.currencyList.value)

        assertEquals(newCurrencyList.code, moveCurrency.code)
    }


}