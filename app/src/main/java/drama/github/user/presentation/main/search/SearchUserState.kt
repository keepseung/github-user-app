package drama.github.user.presentation.main.search

import androidx.annotation.StringRes
import drama.github.user.data.response.SearchUser
import drama.github.user.data.response.SearchUserResponse
import drama.github.user.model.SearchUserModel
import drama.github.user.model.UserGroupModel

/**
 * 사용자 이름 조회 리스트 화면에서 사용되는 상태들
 */
sealed class SearchUserState {

    object Uninitialized : SearchUserState()

    object Loading : SearchUserState()

    sealed class Success : SearchUserState() {

        data class GetUser(
            val searchUserModelList: ArrayList<SearchUserModel> = ArrayList()
        ) : Success()

        data class AddBookmark(
            @StringRes val messageId: Int,
            val id: Long
        ) : Success()

        data class DeleteBookmark(
            @StringRes val messageId: Int,
            val id: Long
        ) : Success()
    }

    data class Error(
        @StringRes val errorMessageId: Int
    ) : SearchUserState()

}