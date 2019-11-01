package np.com.sanjaygubaju.revolut.utils.scheduler

import io.reactivex.schedulers.Schedulers

object SchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
}