package das.mobile.githubuser.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import das.mobile.githubuser.R
import das.mobile.githubuser.data.Result
import das.mobile.githubuser.data.remote.response.UserDetail
import das.mobile.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel
    private var isBookmarked: Boolean = false

    companion object {
        const val EXTRA_LOGIN = "extra_login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application = application)
        )[UserDetailViewModel::class.java]

        val login = intent.getStringExtra(EXTRA_LOGIN)

        login?.let {
            viewModel.getUserDetail(it).observe(this){ result ->
                handleUserDetailResult(result)
            }
        }
    }

    private fun handleUserDetailResult(result: Result<UserDetail>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                val userDetail = result.data
                setupUserDetail(userDetail)
            }

            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Terjadi kesalahan" + result.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupUserDetail(userDetail: UserDetail) {
        binding.tvUsername.text = userDetail.login
        if (userDetail.name == null) {
            binding.tvName.text = userDetail.login
        } else {
            binding.tvName.text = userDetail.name
        }
        binding.tvFollowers.text = getString(R.string.followers_count, userDetail.followers)
        binding.tvFollowing.text = getString(R.string.following_count, userDetail.following)
        Glide
            .with(this)
            .load(userDetail.avatarUrl)
            .into(binding.ivProfile)

        val userDetailPagerAdapter = UserDetailPagerAdapter(this, userDetail.login)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = userDetailPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.fabBookmark.visibility = View.VISIBLE
        binding.fabBookmark.setOnClickListener {
            if (isBookmarked) {
                viewModel.unbookmarkUser(userDetail.toUser())
                Toast.makeText(this, "User removed from bookmark", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.bookmarkUser(userDetail.toUser())
                Toast.makeText(this, "User added into bookmark", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isUserBookmarked(userDetail.login).observe(this) { isBookmarked: Boolean ->
            this.isBookmarked = isBookmarked
            if (isBookmarked){
                binding.fabBookmark.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.fabBookmark.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}