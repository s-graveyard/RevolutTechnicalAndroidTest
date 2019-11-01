package np.com.sanjaygubaju.revolut.di.module

import dagger.Module
import dagger.Provides
import np.com.sanjaygubaju.revolut.utils.BaseSchedulerProvider
import np.com.sanjaygubaju.revolut.utils.SchedulerProvider

@Module
class RxModule {

    @Provides
    fun providesSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}