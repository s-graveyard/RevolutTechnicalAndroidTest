package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.utils.scheduler.BaseSchedulerProvider
import np.com.sanjaygubaju.revolut.utils.scheduler.SchedulerProvider
import javax.inject.Singleton

@Module
class RxModule {

    @Provides
    @Singleton
    fun providesSchedulerProvider(): BaseSchedulerProvider =
        SchedulerProvider
}