package np.com.sanjaygubaju.revolut.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import np.com.sanjaygubaju.revolut.data.CurrencyDataSource
import np.com.sanjaygubaju.revolut.data.models.Currency
import np.com.sanjaygubaju.revolut.utils.scheduler.BaseSchedulerProvider
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class HomeViewModel(
    private val currencyDataSource: CurrencyDataSource,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    // RxJavaDisposable
    val disposables = CompositeDisposable()

    // Currency
    private val _currencyList = MutableLiveData<ArrayList<Currency>>().apply { value = ArrayList() }
    val currencyList: LiveData<ArrayList<Currency>>
        get() = _currencyList

    // Loading
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // Error
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    // Message
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // Default currency
    private var selectedCurrency: Currency = Currency("EUR", "EUR", 1.0)

    // Load currency
    fun loadCurrency(forceUpdate: Boolean = true) {

        if (forceUpdate) {
            _loading.value = true
        }

        _error.value = false
        disposables.clear()

        disposables.add(
            currencyDataSource.getCurrencyRate(selectedCurrency.code)
                .subscribeOn(schedulerProvider.io())
                .repeatWhen {
                    // Request new rate every 1 seconds.
                    it.delay(1, TimeUnit.SECONDS)
                }
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { data ->

                        // Default currency
                        val tempDefaultCurrency = selectedCurrency

                        // ----------
                        val tempCurrencyList: ArrayList<Currency> = ArrayList()

                        // Add default currency to list.
                        tempCurrencyList.add(tempDefaultCurrency)

                        // Add all remaining currency
                        tempCurrencyList.addAll(data.currencies.map {
                            Currency(it.key, it.key, it.value * tempDefaultCurrency.amount)
                        })

                        // Update.
                        _currencyList.value = tempCurrencyList

                        // Not loading
                        _loading.value = false
                        _error.value = false
                    },
                    { throwable ->

                        // Error Message
                        _message.value = throwable.localizedMessage

                        // Not loading
                        _loading.value = false
                        _error.value = true
                    }
                )
        )
    }

    fun moveItemToTop(position: Int) {
        val currencyList: ArrayList<Currency> = ArrayList(_currencyList.value!!.toList())
        try {

            if (currencyList.isEmpty() || currencyList.size <= position) {
                return
            }

            val tempCurrency = currencyList[position]

            // Remove item from selected position
            currencyList.removeAt(position)

            // Sort - Resolves notify item move glitch.
            currencyList.sortWith(Comparator { old, new ->
                old.code.compareTo(new.code)
            })

            // Add item to first index.
            currencyList.add(0, tempCurrency)
        } catch (error: IndexOutOfBoundsException) {
            error.printStackTrace()
        }
        _currencyList.value = currencyList
    }

    fun changeBaseCurrency(currency: Currency) {
        selectedCurrency = Currency(currency.code, currency.name, currency.amount)

        // Reload currency.
        loadCurrency(false)
    }

    fun setNewCurrencyValue(newValue: Double) {
        selectedCurrency.apply {
            selectedCurrency = Currency(code, name, newValue)
        }

        // Reload currency.
        loadCurrency(false)
    }

    fun connectionNotAvailable() {
        _loading.value = false
        _error.value = true
        _message.value = "Internet connection not available"
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}