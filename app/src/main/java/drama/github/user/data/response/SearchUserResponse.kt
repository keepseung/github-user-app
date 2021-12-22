package drama.github.user.data.response

import com.google.gson.annotations.SerializedName

/**
 * 깃헙 사용자 정보 호출 API의 응답값
 */
data class SearchUserResponse (
    @SerializedName("items")
    val userList:List<SearchUser>
)