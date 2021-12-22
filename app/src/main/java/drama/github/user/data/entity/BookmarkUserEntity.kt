package drama.github.user.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import drama.github.user.model.SearchUserModel
import kotlinx.parcelize.Parcelize

/**
 * 북마크한 사용자 정보를 가지고 있을 테이블, 엔터티
 */
@Parcelize
@Entity
class BookmarkUserEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val profileImageUrl: String
) : Parcelable {

    fun entityToModel(): SearchUserModel {
        return SearchUserModel(
            id = id,
            name = name,
            profileImageUrl = profileImageUrl,
            bookmark = true
        )
    }
}