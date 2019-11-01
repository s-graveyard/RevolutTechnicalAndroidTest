package np.com.sanjaygubaju.revolut.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

}