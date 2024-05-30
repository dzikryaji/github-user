package das.mobile.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName
import das.mobile.githubuser.data.local.entity.UserEntity


data class User(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(login, avatarUrl)
    }
}

data class UserDetail(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int
) {
    fun toUser(): User {
        return User(login, avatarUrl)
    }
}

data class SearchResponse(
    @field:SerializedName("items")
    val items: List<User>
)

