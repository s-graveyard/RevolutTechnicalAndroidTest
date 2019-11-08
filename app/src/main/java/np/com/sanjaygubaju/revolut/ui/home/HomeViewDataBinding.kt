package np.com.sanjaygubaju.revolut.ui.home

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import np.com.sanjaygubaju.revolut.R
import np.com.sanjaygubaju.revolut.data.models.Currency
import np.com.sanjaygubaju.revolut.utils.isOnline
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


@BindingAdapter("app:items")
fun RecyclerView.setItems(items: ArrayList<Currency>) {

    // Disable animation - Fix recycler view item blink when updated.
    val animator = itemAnimator
    if (animator is SimpleItemAnimator) {
        animator.supportsChangeAnimations = false
    }

    (adapter as CurrencyAdapter).submitList(items)
}

@BindingAdapter("app:country")
fun ImageView.loadImage(code: String) {

    // Load country flag.
    val name = code.toLowerCase(Locale.getDefault())
    val resourceId = resources.getIdentifier(name, "drawable", context.packageName)

    // Get drawable from resource or use custom gradient drawable if not found.
    val drawable = try {
        ContextCompat.getDrawable(context, resourceId)
    } catch (error: Resources.NotFoundException) {
        GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }

    this.setImageDrawable(drawable)
}

@BindingAdapter("app:currency")
fun EditText.setCurrency(currency: Currency) {

    // Resolves crash when changing locale different from US/UK
    val symbols = DecimalFormatSymbols(Locale.US)

    val value = DecimalFormat("#.##", symbols).format(currency.amount)
    setText(value)

    // Resolves glitch where cursor is on the left most end of the view.
    setSelection(value.length)
}

@BindingAdapter("app:visibility")
fun View.changeVisibility(visibility: Boolean) {
    setVisibility(if (visibility) View.VISIBLE else View.GONE)
}

@BindingAdapter("app:clickItem")
fun Button.handleClick(viewModel: HomeViewModel) {
    this.setOnClickListener {

        if (!isOnline()) {
            viewModel.connectionNotAvailable()
        } else {
            viewModel.loadCurrency(true)
        }
    }
}