package drama.github.user.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import drama.github.user.presentation.main.MainViewModel
import drama.github.user.presentation.main.bookmark.BookmarkUserViewModel
import drama.github.user.presentation.main.search.SearchUserViewModel

/**
 * 아래에 명시된 ViewModel에 dagger가 관리하는 객체를 주입하기 위한 모듈
 * dagger가 ViewModel을 구별하기 위해 Map 객체를 만들어 주는데, 키는 ViewModel의 class 타입이 되고,
 * value는 각각의 ViewModel을 가지게 된다.
 * -> ViewModelFactory에서 map[modelClass]?.get()을 통해 원하는 ViewModel을 가져올 수 있다.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchUserViewModel::class)
    abstract fun bindSearchUserViewModel(viewModel: SearchUserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarkUserViewModel::class)
    abstract fun bindBookmarkUserViewModel(viewModel: BookmarkUserViewModel): ViewModel
}