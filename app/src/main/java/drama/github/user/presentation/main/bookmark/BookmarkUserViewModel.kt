package drama.github.user.presentation.main.bookmark

import androidx.lifecycle.MutableLiveData
import drama.github.user.R
import drama.github.user.data.repository.SearchUserRepository
import drama.github.user.model.SearchUserModel
import drama.github.user.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * 북마크한 사용자 리스트를 보여주기, 해제하기 기능을 위한 ViewModel
 */
class BookmarkUserViewModel @Inject constructor(
    val searchUserRepository: SearchUserRepository
) : BaseViewModel() {

    val bookmarkUserStateLiveData = MutableLiveData<BookmarkUserState>(BookmarkUserState.Uninitialized)
    fun setBookmarkUser(bookmarkUserState: BookmarkUserState) {
        bookmarkUserStateLiveData.value = bookmarkUserState
    }

    fun getBookmarkUser() {

        val queryString = searchText.value
        if (queryString.isNullOrBlank()){
            setBookmarkUser(BookmarkUserState.Error(R.string.hint_search))
            return
        }
        setBookmarkUser(BookmarkUserState.Loading)
        compositeDisposable.add(
            searchUserRepository.getBookmarkUserList(queryString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ bookmarkUserList ->
                    bookmarkUserList?.let {
                        // 로컬에서 조회한 북마크한 사용자 응답 데이터를 모델로 데이터 변환
                        val searchUserModelList:ArrayList<SearchUserModel> = bookmarkUserList.map {
                            it.entityToModel()
                        }.toCollection(ArrayList())

                        setBookmarkUser(BookmarkUserState.Success.GetUser(searchUserModelList))
                    }?: kotlin.run {
                        setBookmarkUser(BookmarkUserState.Error(R.string.get_bookmark_error))
                    }

                },{
                    setBookmarkUser(BookmarkUserState.Error(R.string.get_bookmark_error))
                })
        )
    }

    fun deleteBookmarkUser(id: Long) {

        setBookmarkUser(BookmarkUserState.Loading)
        compositeDisposable.add(
            searchUserRepository.deleteBookmarkUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _id ->
                    _id?.let {
                        // 북마크한 사용자를 다시 조회한다.
                        getBookmarkUser()
                        setBookmarkUser(BookmarkUserState.Success.DeleteBookmark(R.string.delete_bookmark_success, id))
                    }?: kotlin.run {
                        setBookmarkUser(BookmarkUserState.Error(R.string.delete_bookmark_error))
                    }
                },{
                    setBookmarkUser(BookmarkUserState.Error(R.string.delete_bookmark_error))
                })
        )
    }


}