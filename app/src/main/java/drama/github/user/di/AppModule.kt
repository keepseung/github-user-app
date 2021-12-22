package drama.github.user.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 앱 전역적으로 사용할 Context 객체
 */
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }
}