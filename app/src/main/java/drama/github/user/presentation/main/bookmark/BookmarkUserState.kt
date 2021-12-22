package drama.github.user.presentation.main.bookmark

import androidx.annotation.StringRes
import drama.github.user.data.response.SearchUser
import drama.github.user.data.response.SearchUserResponse
import drama.github.user.model.SearchUserModel
import drama.github.user.presentation.main.search.SearchUserState

/**
 * 북마크 유저 리스트 화면에서 사용되는 상태들
 */
sealed class BookmarkUserState {

    object Uninitialized : BookmarkUserState()

    object Loading : BookmarkUserState()

    sealed class Success : BookmarkUserState() {

        data class GetUser(
            val searchUserModelList: ArrayList<SearchUserModel> = ArrayList()
        ) : Success()

        data class DeleteBookmark(
            @StringRes val messageId: Int,
            val id: Long
        ) : Success()
    }
    data class Error(
        @StringRes val errorMessageId: Int
    ) : BookmarkUserState()

}