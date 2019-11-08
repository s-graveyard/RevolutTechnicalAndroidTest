package np.com.sanjaygubaju.revolut.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import np.com.sanjaygubaju.revolut.R
import np.com.sanjaygubaju.revolut.databinding.ActivityHomeBinding
import np.com.sanjaygubaju.revolut.utils.isOnline
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding

    companion object {
        const val LIST_STATE_KEY = "_list_state_key"
        var listState: Bundle? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initializeCurrencyList()
        initializeSnackBar()
    }

    private fun initializeCurrencyList() {

        binding.activityHomeList?.apply {

            // Currency list adapter
            adapter = CurrencyAdapter(viewModel)

            setHasFixedSize(true)

            // Set layout manger
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun initializeSnackBar() {
        val rootView = findViewById<View>(android.R.id.content)
        viewModel.message.observe(this, Observer<String> { value ->
            Snackbar.make(rootView, value ?: "Unknown Error", Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onPause() {
        super.onPause()

        // Save recycler view state.
        listState = Bundle()
        listState?.putParcelable(
            LIST_STATE_KEY,
            binding.activityHomeList.layoutManager?.onSaveInstanceState()
        )

        // Stop background services.
        viewModel.clear()
    }

    override fun onResume() {
        super.onResume()

        // Restore state
        val state: Parcelable? = listState?.getParcelable(LIST_STATE_KEY)
        if (state != null) {
            binding.activityHomeList.layoutManager?.onRestoreInstanceState(state)
        }

        // Load currency
        loadCurrency()

    }

    private fun loadCurrency() {
        if (!isOnline()) {
            viewModel.connectionNotAvailable()
        } else {
            viewModel.loadCurrency(true)
        }
    }

}
