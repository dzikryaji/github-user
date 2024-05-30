package das.mobile.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import das.mobile.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class BookmarkedUserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: BookmarkedUserDatabase? = null
        fun getInstance(context: Context): BookmarkedUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkedUserDatabase::class.java, "BookmarkedUser.db"
                ).build()
            }
    }
}

