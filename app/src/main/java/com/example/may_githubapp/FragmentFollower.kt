package com.example.may_githubapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.may_githubapp.Adapter.LisUserAdapter
import com.example.may_githubapp.Api.ApiConfig
import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.ViewModel.DetailViewModel
import com.example.may_githubapp.ViewModel.FollowViewModel
import com.example.may_githubapp.databinding.ItemRowFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentFollower : Fragment(R.layout.item_row_fragment) {
    private var _binding: ItemRowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var followerViewModel: FollowViewModel
    private lateinit var followerAdapter: LisUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ItemRowFragmentBinding.bind(view)

        initializeComponents()
        observeFollowerData()
    }

    private fun initializeComponents() {
        val arg = arguments
        username = requireActivity().intent.getStringExtra(DetailActivity.DETAIL_USER).toString()

        followerAdapter = LisUserAdapter()
        followerAdapter.notifyDataSetChanged()

        binding.apply {
            rvGithub.setHasFixedSize(true)
            rvGithub.layoutManager = LinearLayoutManager(activity)
            rvGithub.adapter = followerAdapter
        }

        showLoading(true)

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowViewModel::class.java)
        followerViewModel.setDataListFollower(username)

    }

    private fun observeFollowerData() {
        followerViewModel.getDataListFollowers().observe(viewLifecycleOwner) { followers ->
            if (followers != null) {
                followerAdapter.setListUser(followers)
                Log.d("tag", "$followers")
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
