package drama.github.user.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import drama.github.user.presentation.base.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}