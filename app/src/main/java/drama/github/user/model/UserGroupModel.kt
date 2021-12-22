package drama.github.user.model

/**
 * View에 직접적으로 사용하기 위한 모델 데이터
 * 초성과 초성에 해당하는 유저리스트를 가지고 있음
 */
data class UserGroupModel(
    val title: String,
    val userList: ArrayList<SearchUserModel> = ArrayList()
)