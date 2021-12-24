package drama.github.user.data.repository

import drama.github.user.data.db.dao.BookmarkUserDao
import drama.github.user.data.entity.BookmarkUserEntity
import drama.github.user.data.network.GithubApiService
import drama.github.user.data.response.SearchUserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

/**
 * 로컬 데이터베이스 및 깃헙 API와 통신이 발생하는 Repository 구현체
 * GithubApiService와 BookmarkUserDao을 주입받는다.
 */
class SearchUserRepositoryImpl @Inject constructor (
    val githubApiService: GithubApiService,
    val bookmarkUserDao: BookmarkUserDao
):SearchUserRepository{
    override fun getSearchUserList(name:String): Single<Response<SearchUserResponse>> {
        return githubApiService.getSearchUsers(query = name)
    }

    override fun getBookmarkUserList(name: String): Single<List<BookmarkUserEntity>> {
        return bookmarkUserDao.findLikeName(name)
    }

    override fun addBookmarkUser(bookmarkUserEntity: BookmarkUserEntity):Single<Long> {
        return bookmarkUserDao.insert(bookmarkUserEntity)
    }

    override fun deleteBookmarkUser(id: Long): Single<Int> {
        return bookmarkUserDao.deleteById(id)
    }

    override fun getBookmarkUserAll(): Single<List<BookmarkUserEntity>>{
        return bookmarkUserDao.findAll()
    }

}