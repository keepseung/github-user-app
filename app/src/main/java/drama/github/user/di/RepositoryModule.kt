package drama.github.user.di

import dagger.Module
import dagger.Provides
import drama.github.user.data.db.dao.BookmarkUserDao
import drama.github.user.data.network.GithubApiService
import drama.github.user.data.repository.SearchUserRepository
import drama.github.user.data.repository.SearchUserRepositoryImpl
import javax.inject.Singleton

/**
 * Repository 객체 생성을 위한 모듈 정의
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchUserRepository(
        githubApiService: GithubApiService,
        bookmarkUserDao: BookmarkUserDao
    ): SearchUserRepository {
        return SearchUserRepositoryImpl(githubApiService, bookmarkUserDao)
    }

}

