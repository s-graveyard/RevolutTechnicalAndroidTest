package np.com.sanjaygubaju.revolut.data.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class Currency(
    private var _code: String,
    private var _name: String,
    private var _amount: Double
) : BaseObservable() {

    var code: String
        @Bindable get() = _code
        set(value) {
            _code = value
        }

    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
        }

    var amount: Double
        @Bindable get() = _amount
        set(value) {
            _amount = value
        }

}