package drama.github.user.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import drama.github.user.data.entity.BookmarkUserEntity
import io.reactivex.rxjava3.core.Single

/**
 * 북마크한 사용자를 Room DB에 저장, 조회, 삭제하기 위한 Dao
 */
@Dao
interface BookmarkUserDao {

    @Query("SELECT * FROM BookmarkUserEntity WHERE name LIKE '%' || :name || '%'")
    fun findLikeName(name:String): Single<List<BookmarkUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarkUserEntity: BookmarkUserEntity): Single<Long>

    @Query("DELETE FROM BookmarkUserEntity WHERE id=:id")
    fun deleteById(id:Long): Single<Int>
}