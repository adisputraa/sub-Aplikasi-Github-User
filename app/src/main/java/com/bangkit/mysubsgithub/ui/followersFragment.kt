package com.bangkit.mysubsgithub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.mysubsgithub.R
import com.bangkit.mysubsgithub.UserAdapter
import com.bangkit.mysubsgithub.UserDiffUtil
import com.bangkit.mysubsgithub.data.response.user
import com.bangkit.mysubsgithub.databinding.FragmentFollowBinding

class followersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: followersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = UserAdapter()

        binding.apply {
            rvResult.setHasFixedSize(true)
            rvResult.layoutManager = LinearLayoutManager(activity)
            rvResult.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                Intent(requireContext(), DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[followersViewModel::class.java]
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) { newList ->
            newList?.let { _ ->
                val diffResult = DiffUtil.calculateDiff(UserDiffUtil(adapter.getUserList(), newList))
                adapter.setUserList(newList)
                diffResult.dispatchUpdatesTo(adapter)
                showLoading(false)
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}