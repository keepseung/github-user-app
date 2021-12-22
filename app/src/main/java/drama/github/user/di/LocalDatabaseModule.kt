package drama.github.user.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import drama.github.user.data.db.ApplicationDatabase
import drama.github.user.data.db.dao.BookmarkUserDao
import javax.inject.Singleton

/**
 * Room Database, Dao에 대한 모듈 정의
 */
@Module
class LocalDatabaseModule {
    @Singleton
    @Provides
    fun provideDB(context: Context): ApplicationDatabase {
        return Room.databaseBuilder(context, ApplicationDatabase::class.java, ApplicationDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideBookmarkUserDao(database: ApplicationDatabase): BookmarkUserDao {
        return database.bookmarkDao()
    }

}