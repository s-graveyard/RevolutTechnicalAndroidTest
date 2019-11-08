package np.com.sanjaygubaju.revolut.ui.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import np.com.sanjaygubaju.revolut.data.models.Currency
import np.com.sanjaygubaju.revolut.databinding.ListItemCurrencyBinding

/**
 * Currency recycler view adapter
 **/
class CurrencyAdapter(
    private val viewModel: HomeViewModel
) : ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemCurrencyBinding.inflate(layoutInflater, parent, false)
        return CurrencyViewHolder(viewModel, binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency, object : CurrencyAdapterListener {
            override fun moveItem(pos: Int) {
                viewModel.moveItemToTop(pos)

                try {
                    notifyItemChanged(pos, viewModel.currencyList)
                } catch (error: IllegalStateException) {
                    error.printStackTrace()
                }
            }
        })
    }

    /**
     * View Holder for currency
     **/
    class CurrencyViewHolder(
        private val viewModel: HomeViewModel,
        private val binding: ListItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: Currency, listener: CurrencyAdapterListener) {
            binding.viewModel = viewModel
            binding.currency = currency
            binding.position = adapterPosition
            binding.executePendingBindings()

            // Item focus
            binding.listItemCurrencyRoot.setOnClickListener {
                binding.listItemCurrencyValue.requestFocus()
            }

            binding.listItemCurrencyValue.setOnFocusChangeListener { _, hasFocus ->

                val textWatcher = object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {}
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (adapterPosition == 0) {
                            viewModel.setNewCurrencyValue(if (p0.isNullOrBlank()) 1.0 else p0.toString().toDouble())
                        }
                    }
                }

                if (hasFocus) {
                    focusItem(currency, listener)
                    binding.listItemCurrencyValue.addTextChangedListener(textWatcher)
                } else {
                    binding.listItemCurrencyValue.removeTextChangedListener(textWatcher)
                }
            }
        }

        private fun focusItem(currency: Currency, listener: CurrencyAdapterListener) {
            if (adapterPosition != 0) {

                listener.moveItem(adapterPosition)

                binding.listItemCurrencyValue.isFocusableInTouchMode = true
                binding.listItemCurrencyValue.requestFocus()

                // Change base currency
                viewModel.changeBaseCurrency(currency)
            }
        }
    }

    interface CurrencyAdapterListener {
        fun moveItem(pos: Int)
    }

}

/**
 * Callback for calculating difference between items
 **/
class CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.code == newItem.code && oldItem.amount == newItem.amount
    }
}