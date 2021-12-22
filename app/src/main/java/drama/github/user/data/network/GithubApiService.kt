package drama.github.user.data.network

import drama.github.user.data.Url
import drama.github.user.data.response.SearchUserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * 깃헙 유저 검색 API를 호출하기 위한 인터페이스
 */
interface GithubApiService {

    @GET(Url.SEARCH_USERS)
    fun getSearchUsers(
        @Header("Accept") accept: String = Url.ACCEPT_HEADER,
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 100
    ): Single<Response<SearchUserResponse>>

}