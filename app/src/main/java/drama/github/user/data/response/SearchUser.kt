package drama.github.user.data.response

import com.google.gson.annotations.SerializedName
import drama.github.user.model.SearchUserModel

/**
 * 깃헙 사용자 정보 호출 API의 응답값
 */
data class SearchUser(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val profileImageUrl: String
){
    fun responseToModel(): SearchUserModel {
        return SearchUserModel(
            id = id,
            name = name,
            profileImageUrl = profileImageUrl
        )
    }
}