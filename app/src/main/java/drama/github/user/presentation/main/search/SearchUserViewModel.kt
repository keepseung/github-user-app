package drama.github.user.presentation.main.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import drama.github.user.R
import drama.github.user.data.entity.BookmarkUserEntity
import drama.github.user.data.repository.SearchUserRepository
import drama.github.user.data.response.SearchUserResponse
import drama.github.user.model.SearchUserModel
import drama.github.user.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * 사용자 이름 조회 북마크 등록 및 해제하기 기능을 위한 ViewModel
 */
class SearchUserViewModel @Inject constructor(
    val searchUserRepository: SearchUserRepository
) : BaseViewModel() {

    val searchStateLiveData = MutableLiveData<SearchUserState>(SearchUserState.Uninitialized)
    fun setSearchState(searchUserState: SearchUserState){
        searchStateLiveData.value = searchUserState
    }

    fun getBookmarkUser(searchUserResponse: SearchUserResponse) {
        // 북마크 유저 정보를 조회하고 반영함
        val queryString = searchText.value
        if (queryString.isNullOrBlank()){
            return
        }
        compositeDisposable.add(
            searchUserRepository.getBookmarkUserList(queryString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bookmarkUserList ->
                    Log.i("log","bookmarkUserList ${bookmarkUserList.size}")
                    bookmarkUserList?.let {

                        // API 응답 데이터를 모델로 데이터 변환
                        val searchUserModelList:ArrayList<SearchUserModel> = searchUserResponse.userList.map {
                            it.responseToModel()
                        }.toCollection(ArrayList())

                        // 북마크한 사용자가 없는 경우 API 결과를 반환함
                        if (bookmarkUserList.isEmpty()){
                            setSearchState(SearchUserState.Success.GetUser(searchUserModelList))
                        }

                        // 북마크한 사용자 아이디 리스트 조회
                        val bookmarkIdList = it.map { it.id }.toCollection(ArrayList())
                        // 북마크한 사용자가 있는 경우 북마크 데이터 반영
                        searchUserModelList.map { it.bookmark = it.id in bookmarkIdList }

                        setSearchState(SearchUserState.Success.GetUser(searchUserModelList))
                    }?: kotlin.run {
                        Log.e("error", "null")
                        setSearchState(SearchUserState.Error(R.string.get_bookmark_error))
                    }

                },{
                    Log.e("error", "${it.message}")
                    setSearchState(SearchUserState.Error(R.string.get_bookmark_error))
                })
        )
    }

    fun searchUser() {

        val queryString = searchText.value
        if (queryString.isNullOrBlank()){
            setSearchState(SearchUserState.Error(R.string.hint_search))
            return
        }
        setSearchState(SearchUserState.Loading)
        compositeDisposable.add(
                searchUserRepository.getSearchUserList(queryString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (!response.isSuccessful || response.body() == null){
                        setSearchState(SearchUserState.Error(R.string.sever_error))
                        return@subscribe
                    }

                    // API로 검색한 유저 리스트가 존재하는 경우 빈 유저 데이터를 넘김
                    if (response.body()!!.userList.isEmpty()){
                        setSearchState(SearchUserState.Success.GetUser())
                        return@subscribe
                    }

                    // 북마크 유저 정보를 조회하고 반영함
                    getBookmarkUser(response.body()!!)
                },{
                    setSearchState(SearchUserState.Error(R.string.sever_error))
                })
        )
    }

    fun addBookmarkUser(bookmarkUserEntity: BookmarkUserEntity) {

        setSearchState(SearchUserState.Loading)
        compositeDisposable.add(
            searchUserRepository.addBookmarkUser(bookmarkUserEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ id->
                    id?.let {
                        setSearchState(SearchUserState.Success.AddBookmark(R.string.add_bookmark_success, bookmarkUserEntity.id))

                    }?: kotlin.run {
                        setSearchState(SearchUserState.Error(R.string.add_bookmark_error))
                    }
                },{
                    setSearchState(SearchUserState.Error(R.string.add_bookmark_error))
                })
        )
    }

    fun deleteBookmarkUser(id: Long) {

        setSearchState(SearchUserState.Loading)
        compositeDisposable.add(
            searchUserRepository.deleteBookmarkUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _id ->
                    _id?.let {
                        setSearchState(SearchUserState.Success.DeleteBookmark(R.string.delete_bookmark_success, id))
                    }?: kotlin.run {
                        setSearchState(SearchUserState.Error(R.string.delete_bookmark_error))
                    }
                },{
                    setSearchState(SearchUserState.Error(R.string.delete_bookmark_error))
                })
        )
    }


}