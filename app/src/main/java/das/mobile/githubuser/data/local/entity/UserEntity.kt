package das.mobile.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import das.mobile.githubuser.data.remote.response.User

@Entity(tableName = "user")
data class UserEntity(
    @field:ColumnInfo(name = "login")
    @field:PrimaryKey
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
){
    fun toUser(): User{
        return User(login, avatarUrl)
    }
}