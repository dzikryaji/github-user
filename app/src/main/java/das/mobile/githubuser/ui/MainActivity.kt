package das.mobile.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import das.mobile.githubuser.R
import das.mobile.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_bookmark -> {
                    val intent = Intent(this, BookmarkActivity::class.java)
                    startActivity(intent)
                    false
                }

                R.id.action_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    false
                }

                else -> false
            }
        }

        setupLayout(UserListFragment())
    }

    private fun setupLayout(userListFragment: UserListFragment) {
        val fragmentManager = supportFragmentManager

        fragmentManager.commit {
            replace(R.id.container, userListFragment)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchView.hide()
                    userListFragment.searchUser(searchView.text.toString())
                    false
                }
        }
    }
}