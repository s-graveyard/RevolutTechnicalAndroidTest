package np.com.sanjaygubaju.revolut.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import np.com.sanjaygubaju.revolut.R
import np.com.sanjaygubaju.revolut.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding
    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initialize()
    }

    private fun initialize() {

        // Set layout manger
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Currency list adapter
        currencyAdapter = CurrencyAdapter(viewModel, linearLayoutManager)

        binding.activityHomeList.apply {
            adapter = currencyAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }

        // Load currency
        viewModel.loadCurrency(true)
    }
}
