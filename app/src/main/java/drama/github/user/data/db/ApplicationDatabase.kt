package drama.github.user.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import drama.github.user.data.db.dao.BookmarkUserDao
import drama.github.user.data.entity.BookmarkUserEntity

/**
 * 북마크한 사용자를 저장할 Room 데이터베이스
 * 1개의 Entity와 Dao만 사용한다.
 */
@Database(
    entities = [BookmarkUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "GithubUserDatabase.db"
    }
    abstract fun bookmarkDao(): BookmarkUserDao
}