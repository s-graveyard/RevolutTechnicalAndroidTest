package np.com.sanjaygubaju.revolut.utils.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
}