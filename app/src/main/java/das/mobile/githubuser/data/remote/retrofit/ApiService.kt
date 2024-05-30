package das.mobile.githubuser.data.remote.retrofit

import das.mobile.githubuser.BuildConfig
import das.mobile.githubuser.data.remote.response.SearchResponse
import das.mobile.githubuser.data.remote.response.User
import das.mobile.githubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUsers(): Call<List<User>>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun findUser(
        @Query("q") query: String
    ): Call<SearchResponse>
}