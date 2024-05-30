package das.mobile.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import das.mobile.githubuser.R
import das.mobile.githubuser.databinding.ActivityBookmarkBinding

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentManager = supportFragmentManager
        fragmentManager.commit {
            val userListFragment = UserListFragment()
            userListFragment.arguments = Bundle().apply { putInt(UserListFragment.ARG_TYPE, UserListFragment.TYPE_BOOKMARK) }
            replace(R.id.container,userListFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}