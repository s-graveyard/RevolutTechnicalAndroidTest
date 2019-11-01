package np.com.sanjaygubaju.revolut.di.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerAppCompatActivity

@SuppressLint("Registered")
abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract fun getLayout(): Int
    abstract fun initialize()

    private var snackBarRoot: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        // Set SnackBar
        snackBarRoot = findViewById(android.R.id.content)

        initialize()
    }
}