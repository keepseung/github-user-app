package drama.github.user.data.repository

import drama.github.user.data.entity.BookmarkUserEntity
import drama.github.user.data.response.SearchUserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

/**
 * 로컬 데이터베이스 및 깃헙 API를 호출하기 위한 Repository 인터페이스
 */
interface SearchUserRepository {

    fun getSearchUserList(name:String): Single<Response<SearchUserResponse>>
    fun getBookmarkUserList(name:String): Single<List<BookmarkUserEntity>>
    fun addBookmarkUser(bookmarkUserEntity: BookmarkUserEntity): Single<Long>
    fun deleteBookmarkUser(id: Long):Single<Int>
}