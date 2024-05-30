package das.mobile.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import das.mobile.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getBookmarkedUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun bookmarkUser(user: UserEntity)

    @Delete
    fun unbookmarkUser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE login = :login)")
    fun isUserBookmarked(login: String): LiveData<Boolean>
}