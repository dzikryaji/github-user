package das.mobile.githubuser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import das.mobile.githubuser.data.local.settings.SettingPreferences

class ViewModelFactory(
    private val pref: SettingPreferences? = null,
    private val application: Application? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            if (pref != null) {
                return SettingsViewModel(pref) as T
            }
            throw IllegalArgumentException("Setting Preferences parameter for ${modelClass.name} is null")
        } else if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            if (application != null) {
                return UserListViewModel(application) as T
            }
            throw IllegalArgumentException("Application parameter for ${modelClass.name} is null")
        } else if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            if (application != null) {
                return UserDetailViewModel(application) as T
            }
            throw IllegalArgumentException("Application parameter for ${modelClass.name} is null")
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}