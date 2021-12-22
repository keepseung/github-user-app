package drama.github.user.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import drama.github.user.presentation.main.MainActivity

/**
 * - activity에 대한 서브 컴포넌트 생성해준다.
 * - activity에서 객체 주입을 위해 컴포넌트를 생성하고,
 * inject 함수를 호출안해도 된다.
 */
@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}