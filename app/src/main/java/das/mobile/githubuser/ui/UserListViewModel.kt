package das.mobile.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import das.mobile.githubuser.data.Result
import das.mobile.githubuser.data.remote.response.User
import das.mobile.githubuser.repository.UserRepository

class UserListViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    fun getUsers(): LiveData<Result<List<User>>> = userRepository.getUsers()

    fun getUserFollowers(username: String): LiveData<Result<List<User>>> = userRepository.getUserFollowers(username)

    fun getUserFollowing(username: String): LiveData<Result<List<User>>> = userRepository.getUserFollowing(username)

    fun findUser(query: String): LiveData<Result<List<User>>> = userRepository.findUser(query)

    fun getBookmarkedUser(): LiveData<Result<List<User>>> = userRepository.getBookmarkedUser()
}