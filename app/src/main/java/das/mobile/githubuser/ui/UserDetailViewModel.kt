package das.mobile.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import das.mobile.githubuser.data.Result
import das.mobile.githubuser.data.remote.response.User
import das.mobile.githubuser.data.remote.response.UserDetail
import das.mobile.githubuser.repository.UserRepository

class UserDetailViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    fun getUserDetail(login: String): LiveData<Result<UserDetail>> = userRepository.getUserDetail(login)

    fun bookmarkUser(user: User) = userRepository.bookmarkUser(user)

    fun unbookmarkUser(user: User) = userRepository.unbookmarkUser(user)

    fun isUserBookmarked(login: String):LiveData<Boolean> = userRepository.isUserBookmarked(login)
}