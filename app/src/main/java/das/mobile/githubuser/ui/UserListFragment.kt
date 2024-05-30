package das.mobile.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import das.mobile.githubuser.data.Result
import das.mobile.githubuser.data.remote.response.User
import das.mobile.githubuser.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var viewModel: UserListViewModel

    private var username: String? = null
    private var type: Int = TYPE_DEFAULT

    companion object {
        const val ARG_TYPE = "arg_type"
        const val ARG_USERNAME = "arg_username"
        const val TYPE_DEFAULT = -1
        const val TYPE_FOLLOWER_LIST = 0
        const val TYPE_FOLLOWING_LIST = 1
        const val TYPE_BOOKMARK = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application = activity?.application)
        )[UserListViewModel::class.java]

        val layoutManager = LinearLayoutManager(activity)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        type = arguments?.getInt(ARG_TYPE, TYPE_DEFAULT) ?: TYPE_DEFAULT
        username = arguments?.getString(ARG_USERNAME, null)

        observeUserList()
    }

    private fun observeUserList() {
        when (type) {
            TYPE_DEFAULT -> {
                viewModel.getUsers().observe(viewLifecycleOwner){
                    handleUserListResult(it)
                }
            }

            TYPE_FOLLOWER_LIST -> {
                if (username != null) {
                    viewModel.getUserFollowers(username!!).observe(viewLifecycleOwner){
                        handleUserListResult(it)
                    }
                } else {
                    throw NullPointerException("username is null")
                }
            }

            TYPE_FOLLOWING_LIST -> {
                if (username != null) {
                    viewModel.getUserFollowing(username!!).observe(viewLifecycleOwner){
                        handleUserListResult(it)
                    }
                } else {
                    throw NullPointerException("username is null")
                }
            }

            TYPE_BOOKMARK -> {
                viewModel.getBookmarkedUser().observe(viewLifecycleOwner) {
                    viewModel.getBookmarkedUser().observe(viewLifecycleOwner){
                        handleUserListResult(it)
                    }
                }
            }
        }
    }

    private fun setUserData(users: List<User>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvReview.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(activity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_LOGIN, data.login)
                startActivity(intent)
            }
        })
    }

    fun searchUser(query: String) {
        if (query.isEmpty()) {
            observeUserList()
        } else {
            viewModel.findUser(query).observe(viewLifecycleOwner) {
                handleUserListResult(it)
            }
        }
    }

    private fun handleUserListResult(result: Result<List<User>>) {
        when (result) {
            is Result.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvReview.visibility = View.INVISIBLE
            }

            is Result.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.rvReview.visibility = View.VISIBLE
                val userList = result.data
                setUserData(userList)
            }

            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(
                    activity,
                    "Terjadi kesalahan" + result.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (type == TYPE_BOOKMARK){
            viewModel.getBookmarkedUser().observe(viewLifecycleOwner){
                handleUserListResult(it)
            }
        }
    }
}