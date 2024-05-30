package das.mobile.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserDetailPagerAdapter(activity: AppCompatActivity, private val username: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = UserListFragment()
        fragment.arguments = Bundle().apply {
            if (position == 0) {
                putInt(UserListFragment.ARG_TYPE, UserListFragment.TYPE_FOLLOWER_LIST)
            } else {
                putInt(UserListFragment.ARG_TYPE, UserListFragment.TYPE_FOLLOWING_LIST)
            }
            putString(UserListFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}