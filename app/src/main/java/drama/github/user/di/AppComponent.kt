package drama.github.user.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import drama.github.user.MyGithubUserApplication
import javax.inject.Singleton

/**
 * 연결된 Module을 이용하여 의존성 객체를 생성하고,
 * 의존성을 요청받고 주입하는 역할을 하는 컴포넌트
 */
@Singleton
@Component(
    modules =
    [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetModule::class,
        RepositoryModule::class,
        LocalDatabaseModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<MyGithubUserApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}