package com.example.may_githubapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.may_githubapp.Adapter.LisUserAdapter
import com.example.may_githubapp.ViewModel.DetailViewModel
import com.example.may_githubapp.ViewModel.FollowViewModel
import com.example.may_githubapp.databinding.ItemRowFragmentBinding

class FragmentFollowing : Fragment(R.layout.item_row_fragment) {
    private var _binding: ItemRowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowViewModel
    private lateinit var followingAdapter: LisUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ItemRowFragmentBinding.bind(view)

        initializeComponents()
        observeFollowingData()
    }

    private fun initializeComponents() {
        val arg = arguments
        username = requireActivity().intent.getStringExtra(DetailActivity.DETAIL_USER).toString()

        followingAdapter = LisUserAdapter()
        followingAdapter.notifyDataSetChanged()

        binding.apply {
            rvGithub.setHasFixedSize(true)
            rvGithub.layoutManager = LinearLayoutManager(activity)
            rvGithub.adapter = followingAdapter
        }

        showLoading(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowViewModel::class.java)
        followingViewModel.setDataListFollowing(username)
    }

    private fun observeFollowingData() {
        followingViewModel.getDataListFollowing().observe(viewLifecycleOwner) { followingList ->
            if (followingList != null) {
                followingAdapter.setListUser(followingList)
                showLoading(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
