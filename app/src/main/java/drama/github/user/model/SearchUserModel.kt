package drama.github.user.model

import drama.github.user.data.entity.BookmarkUserEntity

/**
 * View에 직접적으로 사용하기 위한 모델 데이터
 * 유저 한 명에 대한 정보를 가지고 있음
 */
data class SearchUserModel(
    val id:Long,
    val name:String,
    val profileImageUrl:String,
    var bookmark: Boolean = false
){
    fun toEntity():BookmarkUserEntity = BookmarkUserEntity(
        id= id,
        name = name,
        profileImageUrl = profileImageUrl
    )
}
