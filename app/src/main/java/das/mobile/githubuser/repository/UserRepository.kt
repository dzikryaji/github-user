package das.mobile.githubuser.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import das.mobile.githubuser.data.Result
import das.mobile.githubuser.data.local.room.BookmarkedUserDatabase
import das.mobile.githubuser.data.local.room.UserDao
import das.mobile.githubuser.data.remote.response.SearchResponse
import das.mobile.githubuser.data.remote.response.User
import das.mobile.githubuser.data.remote.response.UserDetail
import das.mobile.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = BookmarkedUserDatabase.getInstance(application)
        userDao = db.userDao()
    }

    fun getUsers(): LiveData<Result<List<User>>> {
        val result = MutableLiveData<Result<List<User>>>()
        result.value = Result.Loading
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { userDetail ->
                        result.value = Result.Success(userDetail)
                    }
                } else {
                    result.value = Result.Error(response.message())
                    Log.d("Users", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
                Log.d("Users", "onFailure: ${t.message}")
            }

        })
        return result
    }

    fun getUserFollowers(login: String): LiveData<Result<List<User>>> {
        val result = MutableLiveData<Result<List<User>>>()
        result.value = Result.Loading
        val client = ApiConfig.getApiService().getUserFollowers(login)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { userDetail ->
                        result.value = Result.Success(userDetail)
                    }
                } else {
                    result.value = Result.Error(response.message())
                    Log.d("User Followers", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
                Log.d("User Followers", "onFailure: ${t.message}")
            }

        })
        return result
    }

    fun getUserFollowing(login: String): LiveData<Result<List<User>>> {
        val result = MutableLiveData<Result<List<User>>>()
        result.value = Result.Loading
        val client = ApiConfig.getApiService().getUserFollowing(login)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { userDetail ->
                        result.value = Result.Success(userDetail)
                    }
                } else {
                    result.value = Result.Error(response.message())
                    Log.d("User Following", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
                Log.d("User Following", "onFailure: ${t.message}")
            }

        })
        return result
    }

    fun findUser(query: String): LiveData<Result<List<User>>> {
        val result = MutableLiveData<Result<List<User>>>()
        result.value = Result.Loading
        val client = ApiConfig.getApiService().findUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { searchResponse ->
                        result.value = Result.Success(searchResponse.items)
                    }
                } else {
                    result.value = Result.Error(response.message())
                    Log.d("Find User", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("Find User", "onFailure: ${t.message}")
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    fun getUserDetail(login: String): LiveData<Result<UserDetail>> {
        val result = MutableLiveData<Result<UserDetail>>()
        result.value = Result.Loading
        val client = ApiConfig.getApiService().getUserDetail(login)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { userDetail ->
                        result.value = Result.Success(userDetail)
                    }
                } else {
                    Log.d("User Detail", "onFailure: ${response.message()}")
                    result.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.d("User Detail", "onFailure: ${t.message}")
                result.value = Result.Error(t.message.toString())
            }
        })
        return result
    }

    fun getBookmarkedUser(): LiveData<Result<List<User>>>{
        val result = MutableLiveData<Result<List<User>>>()
        result.value = Result.Loading
        val userList = ArrayList<User>()
        executorService.execute{
            val bookmarkedUser = userDao.getBookmarkedUsers()
            bookmarkedUser.forEach{ userEntity ->
                userList.add(userEntity.toUser())
            }
            result.postValue(Result.Success(userList))
        }
        return result
    }

    fun bookmarkUser(user: User) {
        executorService.execute { userDao.bookmarkUser(user.toUserEntity()) }
    }

    fun unbookmarkUser(user: User) {
        executorService.execute { userDao.unbookmarkUser(user.toUserEntity()) }
    }

    fun isUserBookmarked(login: String): LiveData<Boolean> = userDao.isUserBookmarked(login)
}